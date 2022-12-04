package Starner.entities.bullet;

import arc.util.Nullable;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.entities.Effect;

public class MultiSplashBulletType extends BasicBulletType {
  public int splashCount = 3;
  public float splashInterval = lifetime / splashCount;
  public @Nullable Effect splashEffect;

  public MultiSplashBulletType() {
    super();
  }

  @Override

  public Bullet create(Entityc owner, Team team, float x, float y, float angle,
      float damage,
      float velocityScl, float lifetimeScl, Object data, Mover mover, float aimX,
      float aimY) {

    Bullet bullet = super.create(owner, team, x, y, angle, damage, velocityScl,
        lifetimeScl, data, mover, aimX, aimY);
    bullet.mover = MultiSplashMover.create(splashCount, splashInterval, splashEffect);
    return bullet;
  }

  @Override
  public void update(Bullet b) {
    super.update(b);
  }
}
