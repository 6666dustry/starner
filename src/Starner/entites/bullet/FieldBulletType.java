package Starner.entites.bullet;

import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.type.StatusEffect;
import arc.graphics.g2d.*;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Gl;
import mindustry.graphics.Pal;
import arc.math.Interp;
import arc.util.Time;
import java.util.ArrayList;

public class FieldBulletType extends MultiSplashBulletType {
    public StatusEffect fieldStatus = StatusEffects.none;
    public Color fieldColor = Pal.heal.cpy().mul(1f, 1f, 1f, 0.1f);
    public Color fieldEdgeColor = Pal.heal.cpy().mul(1f, 1f, 1f, 0.35f);
    public float fieldEdgeThick = 2.5f;
    public float shrinkTime = 30f;
    public float spreadTime = 30f;
    public boolean applyTeam = false;

    public FieldBulletType() {
        super();
        splashInterval = -1f;
        splashCount = -1;
        speed = 0f;
        width = height = 0;
        collides = false;
        splashDamageRadius = 60f;
        splashDamage = 0f;
        despawnEffect = Fx.none;
        hitEffect = Fx.none;
    }

    @Override
    public void createSplashDamage(Bullet b, float x, float y) {

        super.createSplashDamage(b, x, y);
        if (splashDamageRadius > 0 && !b.absorbed && heals()) {

            ArrayList<Unit> heals = new ArrayList<Unit>();
            Units.nearby(b.team, b.x, b.y, splashDamageRadius, u -> {
                heals.add(u);
            });
            heals.forEach((U) -> {
                if (U.health() < U.maxHealth()) {
                    U.heal(healPercent / 100 * U.maxHealth() + healAmount);
                }
            });
        }
        if (applyTeam) {
            ArrayList<Unit> applys = new ArrayList<Unit>();
            Units.nearby(b.team, b.x, b.y, splashDamageRadius, u -> {
                applys.add(u);
            });
            applys.forEach((U) -> {
                if (status != null) {
                    U.apply(status, statusDuration);
                }
            });
        }
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        float r = splashDamageRadius;
        float sin = Interp.sine.apply((b.time - spreadTime) / 75f) * 10;
        if (b.lifetime - b.time < shrinkTime) {
            r *= Interp.sineIn.apply((b.lifetime - b.time) / shrinkTime);
            sin = 0f;
        }
        if (b.time < spreadTime) {
            r *= Interp.sineIn.apply(b.time / spreadTime);
            sin = 0f;
        }
        // TODO I can't...
        // Draw.blend(new Blending(Gl.srcColor, Gl.oneMinusSrcColor, Gl.one, Gl.one));
        // Gl.blendEquationSeparate(Gl.funcAdd, Gl.min);

        Draw.color(fieldColor);
        Fill.circle(b.x, b.y, r + sin);
        // Draw.blend();
        // Gl.blendEquation(Gl.funcAdd);
        Draw.color(fieldEdgeColor);
        Lines.stroke(fieldEdgeThick);
        Lines.circle(b.x, b.y, r + sin);
        Draw.reset();
    }
}
