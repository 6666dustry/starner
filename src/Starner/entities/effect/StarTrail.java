package Starner.entities.effect;

import mindustry.entities.effect.ParticleEffect;

public class StarTrail extends ParticleEffect {
  public StarTrail() {
    particles = 1;
    region = "starner-star-bullet";
    spin = 4f;
    sizeFrom = 5f;
  };
}
