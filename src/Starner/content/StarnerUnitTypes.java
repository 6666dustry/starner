package Starner.content;

import Starner.entites.effect.StarTrail;
import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.gen.*;

public class StarnerUnitTypes {
    public static UnitType DebriStar;

    public static void load() {

        DebriStar = new UnitType("debri-star") {
            {

                constructor = UnitEntity::create;
                flying = true;
                speed = 3.5f;
                health = 125;
                fallSpeed = Float.POSITIVE_INFINITY;
                weapons.add(new Weapon() {
                    {

                        range = 20f;
                        reload = 1000f;
                        rotate = true;
                        rotateSpeed = Float.POSITIVE_INFINITY;
                        bullet = new BasicBulletType() {
                            {
                                instantDisappear = true;
                                speed = 0;
                                killShooter = true;
                                splashDamage = 5;
                                splashDamageRadius = 50f;
                                fragBullets = 12;
                                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                fragRandomSpread = 0f;
                                fragSpread = 360f / fragBullets;
                                fragBullet = new BasicBulletType(3f, 6, "starner-star-bullet") {
                                    {
                                        frontColor = backColor = Color.valueOf("ffffffff");
                                        weaveRandom = false;
                                        lifetime = 35f;
                                        weaveMag = 4f;
                                        weaveScale = 90f;
                                    }
                                };
                            }
                        };
                    }
                });

            }
        };
    }
}
