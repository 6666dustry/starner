package Starner.entities.bullet;

import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.StatusEffect;
import arc.graphics.g2d.*;
import arc.graphics.Color;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import arc.math.Interp;
import arc.util.Log;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import arc.func.Cons;
import arc.math.geom.Circle;
import arc.math.geom.Intersector;
import mindustry.entities.Damage;
import mindustry.entities.Fires;
import mindustry.gen.Unit;
import arc.math.Mathf;
import mindustry.entities.abilities.ForceFieldAbility;

import java.util.ArrayList;
import static mindustry.Vars.*;

public class FieldBulletType extends BulletType {
    public StatusEffect fieldStatus = StatusEffects.none;
    public float fieldStatusDuration = 180f;
    public float fieldRadius = 60f;
    public float fieldDamage = 0;
    public boolean scaledFieldDamage = false;
    public Color fieldColor = Pal.shield;
    public Effect anchorEffect = Fx.none;
    public float shrinkTime = 30f;
    public float spreadTime = 30f;
    public boolean applyTeam = false;
    public float shieldHealth = -1;
    public boolean isHexagon = false;
    protected static Bullet paramBullet;
    protected static FieldBulletType paramField;
    protected static float paramRadius;

    private class BulletMover implements Mover {

        public float shieldHealth = -1;
        public float downTime = -1;
        public boolean active = true;
        protected float alpha = 0;

        BulletMover(float shieldHealth) {
            this.shieldHealth = shieldHealth;
            if (shieldHealth <= 0) {
                active = false;
            }
        }

        public boolean isDown() {
            return shieldHealth < 0;
        }

        public void damage(float damage) {
            shieldHealth -= damage;
            Log.info(damage);
            if (shieldHealth <= 0) {
                down();
            }
        }

        public boolean isActive() {
            return active;
        }

        public void down() {
            shieldHealth = -1;
            downTime = Time.time;
            // ForceFieldAbility
        }

        @Override
        public void move(Bullet bullet) {
            if (shieldHealth <= 0 || !active) {
                return;
            }
            alpha = Math.max(alpha - Time.delta / 20f, 0f);
            paramBullet = bullet;
            paramField = (FieldBulletType) bullet.type;
            paramRadius = realRadius(bullet);
            Groups.bullet.intersect(bullet.x - paramRadius, bullet.y - paramRadius, paramRadius * 2, paramRadius * 2,
                    shieldConsumer);
        }
    }

    protected static final Cons<Bullet> shieldConsumer = trait -> {
        if (paramBullet.mover instanceof BulletMover m && !m.isDown() && paramBullet.team != trait.team
                && trait.type.absorbable

        ) {

            if ((!paramField.isHexagon
                    || new Circle(paramBullet.x, paramBullet.y, paramRadius).contains(trait.x, trait.y))
                    && (paramField.isHexagon || Intersector.isInsideHexagon(paramBullet.x,
                            paramBullet.y, paramRadius * 2f, trait.x, trait.y))) {
                trait.absorb();
                m.alpha = 1;

                Fx.absorb.at(trait);
                m.damage(trait.damage());
            }
        }
    };

    public FieldBulletType() {
        super();
    }

    {
        speed = 0f;
        collides = false;
        despawnEffect = Fx.none;
        hitEffect = Fx.none;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        createFieldEffect(b);
    }

    public void createFieldEffect(Bullet b) {
        float r = realRadius(b);
        if (r > 0 && !b.absorbed) {
            Damage.damage(b.team, b.x, b.y, fieldRadius, fieldDamage * b.damageMultiplier(), false, collidesAir,
                    collidesGround, scaledFieldDamage, b);

            if (fieldStatus != StatusEffects.none) {
                Damage.status(b.team, b.x, b.y, fieldRadius, fieldStatus, fieldStatusDuration, collidesAir,
                        collidesGround);
            }

            if (heals()) {
                indexer.eachBlock(b.team, b.x, b.y, fieldRadius, Building::damaged, other -> {
                    healEffect.at(other.x, other.y, 0f, healColor, other.block);
                    other.heal(healPercent / 100f * other.maxHealth() + healAmount);
                });
            }

            if (makeFire) {
                indexer.eachBlock(null, b.x, b.y, fieldRadius, other -> other.team != b.team,
                        other -> Fires.create(other.tile));
            }

            if (heals()) {

                ArrayList<Unit> heals = new ArrayList<Unit>();

                Units.nearby(b.team, b.x, b.y, r, u -> {
                    heals.add(u);
                });
                heals.forEach((U) -> {
                    if (U.health() < U.maxHealth()) {
                        U.heal(healPercent / 100 * U.maxHealth() + healAmount);
                    }
                });
                if (applyTeam) {
                    ArrayList<Unit> applys = new ArrayList<Unit>();
                    Units.nearby(b.team, b.x, b.y, r, u -> {
                        applys.add(u);
                    });
                    applys.forEach((U) -> {
                        if (status != null) {
                            U.apply(status, statusDuration);
                        }
                    });
                }
            }
        }
        if (shieldHealth > 0) {
            b.mover.move(b);
        }
    }

    public float realRadius(Bullet b) {
        float r = fieldRadius;
        float sin = Interp.sine.apply((b.time - spreadTime) / 75f) * 10;
        if (b.time < spreadTime) {
            r *= Interp.sineIn.apply(b.time / spreadTime);
            sin = 0f;
        }
        if (b.lifetime - b.time < shrinkTime) {
            r = fieldRadius;
            r *= Interp.sineIn.apply((b.lifetime - b.time) / shrinkTime);
            sin = 0f;
        }
        if (b.mover instanceof BulletMover m && m.isActive() && m.isDown()) {

            r *= (shrinkTime - (Time.time - m.downTime)) / shrinkTime;
            sin = 0f;
            if ((Time.time - m.downTime) > shrinkTime) {
                r = 0;
            }
        }
        return r + sin;
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        Draw.z(Layer.shields);
        float r = realRadius(b);

        Draw.color(fieldColor);
        if (b.mover instanceof BulletMover m) {
            Color[] mixes = { b.team.color, fieldColor };
            Color mix = Tmp.c1.lerp(mixes, 0.25f);
            Draw.color(
                    mix,
                    Color.white, Mathf.clamp(m.alpha));
        }
        Draw.color(fieldColor);
        if (Vars.renderer.animateShields) {
            if (isHexagon) {
                Fill.poly(b.x, b.y, 6, r);

            } else {
                Fill.circle(b.x, b.y, r);
            }
        } else {
            if (isHexagon) {
                Lines.stroke(1.5f);
                Draw.alpha(0.09f);
                Fill.poly(b.x, b.y, 6, r);
                Draw.alpha(1f);
                Lines.poly(b.x, b.y, 6, r);
            } else {
                Lines.stroke(1.5f);
                Draw.alpha(0.09f);
                Fill.circle(b.x, b.y, r);
                Draw.alpha(1f);
                Lines.circle(b.x, b.y, r);
            }

        }
        Draw.reset();
    }

    @Override
    public Bullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage,
            float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY) {
        Bullet b = super.create(owner, team, x, y, angle, damage,
                velocityScl, lifetimeScl, data, mover, aimX, aimY);
        b.mover(new BulletMover(shieldHealth));
        return b;
    }
}
