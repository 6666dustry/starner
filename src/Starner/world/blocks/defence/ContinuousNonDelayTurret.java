package Starner.world.blocks.defence;

import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import arc.util.Tmp;

public class ContinuousNonDelayTurret extends ContinuousTurret {
    public ContinuousNonDelayTurret(String name) {
        super(name);
    }

    public class ContinuousLaserTurretBuild extends ContinuousTurretBuild {
        @Override
        public void updateTile() {
            super.updateTile();
            if (!isShooting()) {
                for (BulletEntry bulletEntry : bullets) {
                    float delay = 10f;
                    if (shootType instanceof ContinuousLaserBulletType laser) {
                        delay = laser.fadeTime;
                    }
                    bulletEntry.bullet.lifetime(delay);
                }
                bullets.clear();
            }
        }

        @Override
        protected void handleBullet(Bullet bullet, float offsetX, float offsetY, float angleOffset) {
            if (bullet != null) {
                bullets.add(new BulletEntry(bullet, offsetX, offsetY, angleOffset, Float.POSITIVE_INFINITY));

                // make sure the length updates to the last set value
                Tmp.v1.trns(rotation, shootY + lastLength).add(x, y);
                bullet.aimX = Tmp.v1.x;
                bullet.aimY = Tmp.v1.y;
            }
        }
    }
}
