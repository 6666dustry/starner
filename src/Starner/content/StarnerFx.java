package Starner.content;

import arc.graphics.Color;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;

public class StarnerFx {
    public static Effect splashStar, healAura, fireAura, freezeAura, sporeAura;

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
    }
}
