package Starner.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
//import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.*;

public class StarnerFx {
    public static Effect splashStar, healAura, fireAura, freezeAura, sporeAura, shootStarFlame;

    public static void load() {
        splashStar = new ParticleEffect() {
            {
                region = "starner-star-bullet";
                spin = 4f;
                sizeFrom = 10f;
                length = 60f;
            }
        };
        healAura = new MultiEffect(new WaveEffect() {
            {
                colorTo = Color.valueOf("00ff00aa");
                sizeTo = 90f;
            }
        }, new ParticleEffect() {
            {
                region = "starner-star-bullet";
                spin = 4f;
                sizeFrom = 12f;
                length = 120f;
                colorTo = Color.valueOf("00ff00aa");
            }
        });
        fireAura = new MultiEffect(new WaveEffect() {
            {
                colorTo = Color.valueOf("ff4444aa");
                sizeTo = 90f;
            }
        },
                new ParticleEffect() {
                    {
                        region = "starner-star-bullet";
                        spin = 4f;
                        sizeFrom = 12f;
                        length = 120f;
                        colorTo = Color.valueOf("ff4444aa");
                    }
                });
        freezeAura = new MultiEffect(new WaveEffect() {
            {
                colorTo = Color.valueOf("bbbbffaa");
                sizeTo = 90f;
            }
        }, new ParticleEffect() {
            {
                region = "starner-star-bullet";
                spin = 4f;
                sizeFrom = 12f;
                length = 120f;
                colorTo = Color.valueOf("bbbbffaa");
            }
        });
        sporeAura = new MultiEffect(new WaveEffect() {
            {
                colorTo = Color.valueOf("ff00ffaa");
                sizeTo = 90f;
            }
        }, new ParticleEffect() {
            {
                region = "starner-star-bullet";
                spin = 4f;
                sizeFrom = 12f;
                length = 120f;
                colorTo = Color.valueOf("ff00ffaa");
            }
        });
        shootStarFlame = new Effect(32f, 80f, e -> {
            color(Color.yellow, Color.white, e.fin());

            randLenVectors(e.id, 8, e.finpow() * 80f, e.rotation, 10f, (x, y) -> {
                // Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
                float size = 10f;

                Draw.rect("starner-star-bullet", e.x + x, e.y + y, size * e.fout(), size * e.fout(),
                        0.65f + e.fout() * 1.5f);
            });
        });
    }

    public static class Pulse extends WaveEffect {
        {
            sizeTo = 15f;
            lifetime = 20f;
            lightOpacity = 0.3f;
        }

        Pulse(Color from, Color to) {
            colorFrom = from;
            colorTo = to;
        }

        Pulse(Color to) {
            colorTo = to;
        }

        Pulse() {
        }
    }
}
