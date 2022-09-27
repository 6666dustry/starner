package Starner.type.unit;

import Starner.entites.unit.TrailUnit;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class TrailUnitType extends UnitType {
    @Nullable
    public Effect trailEffect;
    public float trailChance = 0.25f;
    public float trailInterval = 0.25f;
    public Color trailColor = Color.white;

    public TrailUnitType(String name) {
        super(name);
        constructor = TrailUnit::create;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if (!unit.vel.isZero(0.2f) && unit instanceof TrailUnit u && trailEffect != null
                && u.trailTime + trailInterval < Time.time) {
            if (Mathf.chance(trailChance)) {
                trailEffect.at(u.x, u.y, u.rotation, trailColor);
            }
            u.trailTime = Time.time;
        }
    }
}
