package Starner.world.draw;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.world.Block;
import mindustry.world.draw.DrawFlame;
import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.util.Time;

public class DrawFusion extends DrawFlame {
    public float rotateSpeed = 1f;
    /** Any number <=0 disables layer changes. */
    public float layer = -1;

    public DrawFusion() {
    }

    public DrawFusion(Color flameColor) {
        this.flameColor = flameColor;
    }

    public void draw(Building build) {
        if (build.warmup() > 0f && flameColor.a > 0.001f) {
            float g = 0.3f;
            float r = 0.06f;
            float cr = Mathf.random(0.1f);

            Draw.z(Layer.block + 0.01f);

            Draw.alpha(build.warmup());
            Draw.rect(top, build.x, build.y, build.totalProgress() * rotateSpeed);

            Draw.alpha(((1f - g) + Mathf.absin(Time.time, 8f, g) + Mathf.random(r) - r) * build.warmup());

            Draw.tint(flameColor);
            Fill.circle(build.x, build.y, flameRadius + Mathf.absin(Time.time, flameRadiusScl, flameRadiusMag) + cr);
            Draw.color(1f, 1f, 1f, build.warmup());
            Fill.circle(build.x, build.y,
                    flameRadiusIn + Mathf.absin(Time.time, flameRadiusScl, flameRadiusInMag) + cr);

            Draw.color();
        }
    }
}
