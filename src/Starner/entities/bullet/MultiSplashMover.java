package Starner.entities.bullet;

import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Mover;
import mindustry.gen.Bullet;
import mindustry.entities.Effect;

public class MultiSplashMover implements Mover {
  public int splashCount = 3;
  public float splashInterval = 20f;
  public @Nullable Effect splashEffect = Fx.none;

  private int splashedCount = 0;

  public static MultiSplashMover create(int count, float interval, @Nullable Effect splashEffect) {
    MultiSplashMover mover = new MultiSplashMover();
    mover.splashCount = count;
    mover.splashInterval = interval;
    mover.splashEffect = splashEffect;
    return mover;
  }

  @Override
  public void move(Bullet b) {

    if ((splashedCount <= splashCount && (b.time > (1 + splashedCount) * splashInterval)) || splashCount < 0) {
      b.type.createFrags(b, b.x, b.y);
      b.type.createIncend(b, b.x, b.y);
      b.type.createPuddles(b, b.x, b.y);
      b.type.createSplashDamage(b, b.x, b.y);
      if (splashEffect != null) {
        splashEffect.at(b.x, b.y, b.rotation());
      }
      splashedCount++;
    }
  }
}
