package Starner.world.blocks.defence;

import mindustry.entities.bullet.ContinuousLaserBulletType;
import Starner.entities.bullet.ContinuousLaserTrailBulletType;
import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.ui.Bar;
import arc.Core;
import mindustry.graphics.Pal;

public class ContinuousNonDelayTurret extends ContinuousTurret {
  public ContinuousNonDelayTurret(String name) {
    super(name);
  }

  @Override
  public void setBars() {
    super.setBars();
    addBar("damageIncrease",
        (ContinuousLaserTurretBuild entity) -> new Bar(
            () -> Core.bundle.format("bar.damageincrease"),
            () -> Pal.lightOrange,
            () -> entity.increaseDamage));
  }

  public class ContinuousLaserTurretBuild extends ContinuousTurretBuild {
    public float increaseDamage = 0;

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
        increaseDamage = 0;
      }
      for (BulletEntry bulletEntry : bullets) {
        if (shootType instanceof ContinuousLaserTrailBulletType l) {
          increaseDamage = (bulletEntry.bullet.damage() - l.damage) / l.maxDamage;
          Log.info(bulletEntry.bullet.damage());
        }
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
