package Starner.entites.bullet;

import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import arc.math.*;

public class FragLaserBulletType extends LaserBulletType {
    public FragLaserBulletType(float damage) {
        super(damage);
    }

    public FragLaserBulletType() {
        super(1f);
    }

    @Override
    public void createFrags(Bullet b, float x, float y) {
        super.createFrags(b, x + Angles.trnsx(b.rotation(), length), y + Angles.trnsy(b.rotation(), length));
    }

    @Override
    public void createSplashDamage(Bullet b, float x, float y) {
        super.createSplashDamage(b, x + Angles.trnsx(b.rotation(), length), y + Angles.trnsy(b.rotation(), length));
    }

    @Override
    public void createIncend(Bullet b, float x, float y) {
        super.createSplashDamage(b, x + Angles.trnsx(b.rotation(), length), y + Angles.trnsy(b.rotation(), length));
    }

    @Override
    public void createPuddles(Bullet b, float x, float y) {
        super.createSplashDamage(b, x + Angles.trnsx(b.rotation(), length), y + Angles.trnsy(b.rotation(), length));
    }
}