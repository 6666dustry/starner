package Starner.entities.bullet;

import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import arc.math.*;
import arc.math.geom.Geometry;
import mindustry.entities.Effect;
import mindustry.entities.Damage;

public class ContinuousLaserTrailBulletType extends ContinuousLaserBulletType {
    public float trailSpacing = 3f;
    public float increaseDamage = 0.1f;
    public float maxDamage = 15f;

    // TODO this name is too long.
    public ContinuousLaserTrailBulletType(float damage) {
        this.damage = damage;
    }

    public ContinuousLaserTrailBulletType() {
    }

    @Override
    public void applyDamage(Bullet b) {
        Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), currentLength(b), largeHit, laserAbsorb,
                pierceCap);
    }

    @Override
    public void update(Bullet b) {
        updateTrailEffects(b);
        if (b.damage < damage + maxDamage) {
            b.damage(b.damage() + increaseDamage);
        }
        if (!continuous)
            return;

        // damage every 5 ticks
        if (b.timer(1, damageInterval)) {
            applyDamage(b);
        }

        if (shake > 0) {
            Effect.shake(shake, shake, b);
        }
    }

    @Override
    public void updateTrailEffects(Bullet b) {
        float px = Angles.trnsx(b.rotation(), length), py = Angles.trnsy(b.rotation(), length);
        if (trailChance > 0) {

            Geometry.iterateLine(0f, b.x, b.y, b.x + px, b.y + py, trailSpacing, (x, y) -> {
                if (Mathf.chanceDelta(trailChance)) {
                    trailEffect.at(x, y, trailRotation ? b.rotation() : trailParam, trailColor);
                }
            });

        }

        if (trailInterval > 0f) {
            if (b.timer(0, trailInterval)) {
                Geometry.iterateLine(0f, b.x, b.y, b.x + px, b.y + py, trailSpacing, (x, y) -> {
                    trailEffect.at(x, y, trailRotation ? b.rotation() : trailParam, trailColor);
                });
            }
        }
    }
}