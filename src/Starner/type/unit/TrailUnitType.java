package Starner.type.unit;

import Starner.entities.unit.TrailUnit;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class TrailUnitType extends UnitType {
  public Seq<TrailEffect> trailEffects = new Seq<>();

  public static class TrailEffect {
    public TrailEffect(Effect effect) {
      this.effect = effect;
    }

    public TrailEffect(Effect effect, float x, float y, float chance, float interval) {
      this.effect = effect;
      this.x = x;
      this.y = y;
      this.chance = chance;
      this.interval = interval;
    }

    public Effect effect;
    public float chance = 0.25f;
    public float interval = 0.25f;
    public float x = 0, y = -10;
  }

  public TrailUnitType(String name) {
    super(name);
    constructor = TrailUnit::create;
  }

  @Override
  public void update(Unit unit) {
    super.update(unit);
    for (TrailEffect trail : trailEffects) {
      if (!unit.vel.isZero(0.2f) && unit instanceof TrailUnit u
          && u.trailTime + trail.interval < Time.time) {
        if (Mathf.chance(trail.chance)) {
          float rotation = unit.rotation() - 90;
          trail.effect.at(u.x + Angles.trnsx(rotation, trail.x, trail.y),
              u.y + Angles.trnsy(rotation, trail.x, trail.y), u.rotation);
        }
        u.trailTime = Time.time;
      }
    }
  }
}
