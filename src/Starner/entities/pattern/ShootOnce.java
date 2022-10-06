package Starner.entities.pattern;

import mindustry.entities.pattern.ShootSpread;

public class ShootOnce extends ShootSpread {
    public int shotOnce = 3;

    public ShootOnce(int shots, float spread) {
        this.shots = shots;
        this.spread = spread;
    }

    public ShootOnce() {

    }

    @Override
    public void shoot(int totalShots, BulletHandler handler) {
        for (int i = 0; i < shots / shotOnce; i++) {
            for (int j = 0; j < shotOnce; j++) {
                float angleOffset = j * spread - (shotOnce - 1) * spread / 2f;
                handler.shoot(0, 0, angleOffset, firstShotDelay + shotDelay * i);
            }
        }
    }
}
