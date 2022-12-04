package Starner.entities.bullet;

import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.game.Team;
import mindustry.entities.Mover;

public class SlowlyBulletType extends BasicBulletType {
  public float slowPercent = 0.05f;
  public float velocityRnd = 0f;

  public SlowlyBulletType() {
    super();
  }

  @Override
  public @Nullable Bullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage,
      float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY) {
    Bullet b = super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY);
    float rndVel = Mathf.random(-velocityRnd, velocityRnd);
    b.vel().add(rndVel, rndVel);
    return b;
  }

  @Override
  public void update(Bullet b) {
    b.vel().scl(1 - slowPercent, 1 - slowPercent);
    super.update(b);
  }
}
