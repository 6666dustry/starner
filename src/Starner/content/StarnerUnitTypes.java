package Starner.content;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.gen.*;

public class StarnerUnitTypes {

    public static UnitType DebriStar, CometSlicer;

    public static void load() {
        DebriStar = new UnitType("debri-star") {
            {
                constructor = UnitEntity::create;
                description = "very cheep unit.";
                details = "flying crawler.";
                flying = true;
                speed = 3.5f;
                health = 105;
                fallSpeed = Float.POSITIVE_INFINITY;
                range = 6f;
                maxRange = 6f;
                weapons.add(new Weapon() {
                    {
                        mirror = false;
                        reload = 60f;
                        rotate = true;
                        shootOnDeath = true;
                        rotateSpeed = Float.POSITIVE_INFINITY;
                        bullet = new BasicBulletType() {
                            {
                                range = 6f;
                                maxRange = 6f;
                                instantDisappear = true;
                                speed = 3f;
                                killShooter = true;
                                splashDamage = 10;
                                splashDamageRadius = 60f;
                                fragBullets = 14;
                                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                fragRandomSpread = 0f;
                                fragSpread = 360f / fragBullets;
                                fragBullet = new BasicBulletType(3f, 8, "starner-star-bullet") {
                                    {
                                        range = 6f;
                                        maxRange = 6f;
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
        CometSlicer = new UnitType("comet-slicer") {
            {
                constructor = UnitEntity::create;
                description = "we are star!";
                details = "star slice!";
                flying = true;
                speed = 2.5f;
                health = 225;
                armor = 1;
                range = 50f;
                deathExplosionEffect = StarnerFx.splashStar;
                fallSpeed = Float.POSITIVE_INFINITY;
                weapons.add(new Weapon("starner-star-laser") {
                    {
                        y = 6f;
                        layerOffset = -1;
                        range = 50f;
                        reload = 45f;
                        rotate = false;
                        shootSound = Sounds.laser;
                        bullet = new LaserBulletType(15) {
                            {
                                range = 50f;
                                length = 55f;
                                instantDisappear = true;
                                splashDamage = 5;
                                splashDamageRadius = 50f;
                                fragBullets = 16;
                                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                fragRandomSpread = 0f;
                                fragSpread = 360f / fragBullets;
                                pierceCap = 2;
                                fragBullet = new BasicBulletType(3.2f, 6, "starner-star-bullet") {
                                    {
                                        frontColor = backColor = Color.valueOf("ffffffff");
                                        weaveRandom = false;
                                        lifetime = 40f;
                                        weaveMag = 4.1f;
                                        weaveScale = 90f;
                                    }
                                };
                            }
                        };
                    }
                });

            }
        };
        override();
    }

    private static void override() {
        Reconstructor ride = (Reconstructor) Blocks.additiveReconstructor;
        ride.upgrades.add(new UnitType[] { DebriStar, CometSlicer });
    }
}
