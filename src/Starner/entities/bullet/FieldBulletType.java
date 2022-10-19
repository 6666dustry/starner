package Starner.entities.bullet;

import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.StatusEffect;
import arc.graphics.g2d.*;
import arc.graphics.Color;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import arc.math.Interp;
import arc.util.Nullable;
import arc.util.Time;
import arc.func.Cons;
import arc.math.geom.*;
import arc.math.Mathf;
import arc.*;
import mindustry.game.EventType.*;

import static mindustry.Vars.*;

public class FieldBulletType extends SlowlyBulletType {
    public StatusEffect fieldStatus = StatusEffects.none;

    public float fieldStatusDuration = 180f;
    public float fieldRadius = 60f;
    public float fieldDamage = 0.5f;
    public boolean scaledFieldDamage = false;
    public Color fieldColor = Pal.shield.cpy().a(1);
    public Effect anchorEffect = Fx.none;
    public float shrinkTime = 30f;
    public float spreadTime = 30f;
    public float fieldHealth = -1;
    protected static Bullet paramBullet;
    protected static FieldBulletType paramField;
    protected static float paramRadius;
    private static final Vec2 vec = new Vec2();
    private static final Rect rect = new Rect();

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
        }

        @Override
        public void move(Bullet bullet) {
            if (shieldHealth <= 0 || !active) {
                return;
            }
            alpha = Math.max(alpha - Time.delta / 10f, 0f);
            paramBullet = bullet;
            paramField = (FieldBulletType) bullet.type;
            paramRadius = realRadius(bullet);
            Groups.bullet.intersect(bullet.x - paramRadius, bullet.y - paramRadius, paramRadius * 2, paramRadius * 2,
                    shieldConsumer);
        }
    }

    protected static final Cons<Bullet> shieldConsumer = trait -> {
        if (paramBullet.mover instanceof BulletMover m && !m.isDown() && paramBullet.team != trait.team
                && trait.type.absorbable) {

            if (new Circle(paramBullet.x, paramBullet.y, paramRadius).contains(trait.x, trait.y)) {
                float disX = paramBullet.x - trait.x;
                float disY = paramBullet.y - trait.y;
                if (disX * disX + disY * disY < paramRadius * paramRadius) {
                    trait.absorb();
                    m.alpha = 1;

                    Fx.absorb.at(trait);
                    m.damage(trait.damage());
                }
            }
        }
    };

    public FieldBulletType() {
        super();
    }

    {
        speed = 0f;
        lifetime = 300f;
        collides = false;
        despawnEffect = Fx.none;
        hitEffect = Fx.none;
        slowPercent = 0;
        width = height = 0;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        createFieldEffect(b);
    }

    public void createFieldEffect(Bullet b) {
        float r = realRadius(b);
        if (r > 0 && !b.absorbed) {

            Cons<Unit> cons = entity -> {
                if (entity.team == b.team || !entity.checkTarget(collidesAir, collidesGround) || !entity.hittable()
                        || !entity.within(b.x, b.y, fieldRadius + (scaledFieldDamage ? entity.hitSize / 2f : 0f))) {
                    return;
                }
                float falloff = 0.4f;
                float scaled = Mathf
                        .lerp(1f - (scaledFieldDamage ? Math.max(0, entity.dst(b.x, b.y) - entity.type.hitSize / 2)
                                : entity.dst(b.x, b.y)) / fieldRadius, 1f, falloff);
                float amount = fieldDamage * scaled;

                entity.damage(amount);
                float dst = vec.set(entity.x - b.x, entity.y - b.y).len();
                entity.vel.add(vec.setLength((1f - dst / fieldRadius) * 2f / entity.mass()));

                if (fieldDamage
                        * b.damageMultiplier() >= 9999999f && entity.isPlayer()) {
                    Events.fire(Trigger.exclusionDeath);
                }
            };

            rect.setSize(fieldRadius * 2).setCenter(b.x, b.y);
            if (b.team != null) {
                Units.nearbyEnemies(b.team, rect, cons);
            } else {
                Units.nearby(rect, cons);
            }

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
        }
        if (fieldHealth > 0) {
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
        if (b.mover() instanceof BulletMover m) {

            Draw.color(
                    fieldColor,
                    Color.white, Mathf.clamp(m.alpha));
        }
        if (Vars.renderer.animateShields) {
            Fill.circle(b.x, b.y, r);
        } else {
            Lines.stroke(1.5f);
            Draw.alpha(0.09f);
            Fill.circle(b.x, b.y, r);
            Draw.alpha(1f);
            Lines.circle(b.x, b.y, r);
        }
        anchorEffect.at(b.x, b.y, b.rotation());
        Draw.reset();
    }

    @Override
    public Bullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage,
            float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY) {
        Bullet b = super.create(owner, team, x, y, angle, damage,
                velocityScl, lifetimeScl, data, mover, aimX, aimY);
        b.mover(new BulletMover(fieldHealth));
        return b;
    }
}
