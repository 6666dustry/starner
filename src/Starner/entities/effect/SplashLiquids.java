package Starner.entities.effect;

import arc.math.Interp;
import mindustry.content.Liquids;
import mindustry.entities.effect.ParticleEffect;
import mindustry.type.Liquid;

public class SplashLiquids extends ParticleEffect {
  public SplashLiquids() {
    this(Liquids.water);
  }

  public SplashLiquids(Liquid liquid) {
    colorFrom = colorTo = liquid.color;
    lightColor = liquid.lightColor;
    lightOpacity = liquid.lightColor.a;
    cone = 90f;
    interp = Interp.sineOut;
    sizeFrom = 6f;
    length = 100f;
    lifetime = 20f;
    particles = 16;
  }
}
