package BattleField;

import static BattleField.BattleField.destroyed;
import static BattleField.BattleField.key;
import static BattleField.BattleField.screen;
import static com.googlecode.lanterna.terminal.Terminal.Color.*;

public class Player extends VisualObject implements Collidable {

    int shooting_angle = -90;
    int range = 25;
    int width = 4;
    int height = 2;
    Hitbox hitbox;

    public Player(int x_pos, int y_pos) {
        super(x_pos, y_pos);
        colorBg = BLACK;
        colorFg = GREEN;
        look = new String[]{"8 I 8",
                            "8(_)8",
                            "8-=-8"};
        generateHitbox();
    }

    public void generateHitbox() {
        this.hitbox = new Hitbox();
        this.hitbox.pos = this.pos;
        this.hitbox.width = this.width;
        this.hitbox.height = this.height;
    }

    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public boolean checkCollision(Hitbox other) {
        return this.hitbox.checkCollision(other);
    }

    @Override
    public void moveLeft() {
        this.pos[0] -= 2;
    }

    @Override
    public void moveRight() {
        this.pos[0] += 2;

    }

    public void setAngle(int angle) {
        this.shooting_angle = -angle;
    }

    public void fire() throws InterruptedException {
        Projectile projectile = new Projectile(pos[0] + 2, pos[1] - 1, range, shooting_angle);
        projectile.hasBeenFired();
    }

    public void aim() throws InterruptedException {
        lock:
        while (true) {

            Projectile projectile = new Projectile(pos[0] + 2, pos[1] - 1, 3, shooting_angle);

            projectile.project();
            key = null;
            while (key == null) {
                key = screen.readInput();

            }
            switch (key.getKind()) {
                case ArrowLeft:
                    if (shooting_angle != -180) {
                        shooting_angle -= 5;
                    }
                    projectile.setShootingAngle(shooting_angle);
                    projectile.project();
                    break;
                case ArrowRight:
                    if (shooting_angle != 0) {
                        shooting_angle += 5;
                    }
                    projectile.setShootingAngle(shooting_angle);
                    projectile.project();
                    break;
                case Enter:
                    break lock;
            }

        }

        fire();
    }

    public boolean checkIfDead() {
        for (Player tocheck : destroyed) {
            if (this == tocheck) {
                destroyed.remove(tocheck);
                return true;
            }
        }
        return false;
    }
}
