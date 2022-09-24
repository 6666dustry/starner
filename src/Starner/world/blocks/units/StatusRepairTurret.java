package Starner.world.blocks.units;

import mindustry.type.StatusEffect;
import mindustry.world.blocks.units.RepairTurret;

import java.util.ArrayList;

import arc.func.Cons;
import arc.math.*;
import arc.math.geom.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import arc.util.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;

public class StatusRepairTurret extends RepairTurret {
    static final Rect rect = new Rect();
    public StatusEffect statusEffect;
    public float healRadius = -1;
    public float healSpeed = 0.1f;
    public Color healCircleLineColor = Pal.heal.cpy().mul(1f, 1f, 1f, 0.75f);
    public Color healCircleColor = Pal.heal.cpy().mul(1f, 1f, 1f, 0.25f);
    public float StatusDuration = 60f;
    public Effect laserEffect = Fx.none;
    public float laserEffectChance = 0.05f;
    public float laserEffectSpacing = 5f;
    public float laserEffectInterval = 10f;

    public StatusRepairTurret(String name) {
        super(name);
    }

    public class StatusRepairPointBuild extends RepairPointBuild {
        private float elapsedTime = Time.time;

        private void drawRadius() {

            float f = Interp.sine.apply(Time.time / 75f) * 10;
            Draw.color(healCircleColor);
            Fill.circle(lastEnd.x, lastEnd.y, healRadius + f);
            Draw.color(healCircleLineColor);
            Lines.stroke(strength * pulseStroke);
            Lines.circle(lastEnd.x, lastEnd.y, healRadius + f);
            Draw.color();
        }

        @Override
        public void draw() {
            // TODO Auto-generated method stub
            super.draw();
            if (target != null && efficiency > 0) {
                drawRadius();
            }
        }

        @Override
        public void updateTile() {
            float multiplier = 1f;
            if (acceptCoolant) {
                multiplier = 1f + liquids.current().heatCapacity * coolantMultiplier * optionalEfficiency;
            }

            if (target != null && (target.dead() || target.dst(this) - target.hitSize / 2f > repairRadius
            // || target.health() >= target.maxHealth()
            )) {
                target = null;
            }

            if (target == null) {
                offset.setZero();
            }

            boolean healed = false;

            if (target != null && efficiency > 0) {
                float angle = Angles.angle(x, y, target.x + offset.x, target.y + offset.y);
                if (Angles.angleDist(angle, rotation) < 30f) {
                    if (elapsedTime + laserEffectInterval < Time.time) {
                        Geometry.iterateLine(0f, x, y, target.x, target.y, laserEffectSpacing, (x, y) -> {
                            if (Mathf.chance(laserEffectChance)) {
                                laserEffect.at(x, y, rotation);
                            }
                            ;
                        });
                        elapsedTime = Time.time;
                    }
                    healed = true;
                    if (target.health() < target.maxHealth()) {

                        target.heal(repairSpeed * strength * edelta() * multiplier);
                    }

                    if (statusEffect != null) {
                        target.apply(statusEffect, StatusDuration);
                    }

                    if (healRadius > 0) {
                        ArrayList<Unit> heals = new ArrayList<Unit>();
                        Units.nearby(team, target.x, target.y, healRadius, new Cons<Unit>() {
                            public void get(Unit U) {
                                heals.add(U);
                            }
                        });
                        heals.forEach((U) -> {
                            if (U.health() < U.maxHealth()) {
                                U.heal(healSpeed * strength * edelta());
                            }
                            if (statusEffect != null) {
                                U.apply(statusEffect, StatusDuration);
                            }
                        });
                    }
                }
                rotation = Mathf.slerpDelta(rotation, angle, 0.5f * efficiency * timeScale);
            }

            strength = Mathf.lerpDelta(strength, healed ? 1f : 0f, 0.08f * Time.delta);

            if (timer(timerTarget, 20)) {
                rect.setSize(repairRadius * 2).setCenter(x, y);
                target = Units.closest(team, x, y, repairRadius, Unit::damaged);
                if (target == null) {
                    target = Units.closest(team, x, y, repairRadius, (u) -> {
                        return true;
                    });
                }
            }
        }
    };
}
