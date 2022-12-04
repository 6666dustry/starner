package Starner.entities.unit;

import Starner.content.StarnerUnitTypes;
import arc.util.Time;
import mindustry.gen.UnitEntity;

public class TrailUnit extends UnitEntity {
  public static int id = 0;

  public TrailUnit() {
    super();
  }

  public static TrailUnit create() {
    return new TrailUnit();

  }

  public float trailTime = Time.time;

  @Override
  public void update() {
    super.update();
    type.update(self());
  }

  @Override
  public int classId() {
    return StarnerUnitTypes.classID(TrailUnit.class);
  }
}
