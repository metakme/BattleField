package BattleField;

import static BattleField.BattleField.r;
import static com.googlecode.lanterna.terminal.Terminal.Color.*;

public class Enemy extends Player {

    public Enemy(int x_pos, int y_pos) {
        super(x_pos, y_pos);
        colorBg = BLACK;
        colorFg = BLUE;
        look = new String[]{"8-_-8",
                            "8( )8",
                            "8 T 8"};
        shooting_angle = 90;
    }

    public void setTarget(Player player) {
        double adjacent = Math.abs((player.pos[0] - this.pos[0]));
        double oposite = Math.abs((player.pos[1] - this.pos[1]) * 3);
        shooting_angle = (int) ((Math.atan(oposite / adjacent)) * 180 / Math.PI);
        if (pos[0] > player.pos[0]) {
            shooting_angle = 90 + (90 - shooting_angle);
        }
    }

    @Override
    public void fire() throws InterruptedException {
        Projectile projectile = new Projectile(pos[0] + 2, pos[1] + 3, range, shooting_angle);
        projectile.hasBeenFired();
    }

    
    public void addMissChance() {
        shooting_angle = shooting_angle + (r.nextInt(20) - 10);
    }

}
