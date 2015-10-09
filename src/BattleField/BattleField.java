package BattleField;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import static com.googlecode.lanterna.input.Key.Kind.*;
import com.googlecode.lanterna.screen.Screen;
import static com.googlecode.lanterna.terminal.Terminal.Color.*;
import java.util.ArrayList;
import java.util.Random;

public class BattleField {

    public static ArrayList<VisualObject> objects_to_draw = new ArrayList<>();
    public static ArrayList<Collidable> collidables = new ArrayList<>();
    public static ArrayList<Player> destroyed = new ArrayList<>();
    public static boolean projectiles_in_air = false;
    public static Screen screen = TerminalFacade.createScreen();
    public static Key key = null;
    public static Random r = new Random();
    public static Enemy CurrentEnemy;
    public static Player CurrentPlayer;

    public static void globalDraw() {
        for (Object to_draw : objects_to_draw) {
            VisualObject vo = (VisualObject) to_draw;
            vo.draw(screen);
        }
    }

    public static void spawnEnemy() {

        Enemy enemy = new Enemy((r.nextInt(90)), (r.nextInt(5) + 1));
        objects_to_draw.add(enemy);
        collidables.add(enemy);
        CurrentEnemy = enemy;
    }

    public static void spawnPlayer() {

        Player player = new Player((r.nextInt(90)), (r.nextInt(5) + 22));
        objects_to_draw.add(player);
        collidables.add(player);
        CurrentPlayer = player;
    }

    public static enum Turn {

        PLAYER, ENEMY
    };

    public static void main(String[] args) throws InterruptedException {
        spawnEnemy();
        spawnPlayer();
        Turn turn = Turn.PLAYER;
        VisualObject header = new VisualObject(1, 0);
        VisualObject controls = new VisualObject(1, 29);
        controls.look = new String[]{"Controls: ENTER: shoot / ARROW Keys: move / ESCAPE: exit"};
        objects_to_draw.add(header);
        objects_to_draw.add(controls);
        
        boolean running = true;
        int round = 1;
        int score_p = 0;
        int score_e = 0;

        screen.startScreen();

        round:
        while (running) {
            screen.clear();
            header.look = new String[]{"ROUND: " + round + "      SCORE:[ You: " + score_p + " Enemy: " + score_e + " ]" + "   TURN: " + turn };
            globalDraw();
            screen.refresh();

            switch (turn) {
                case PLAYER:
                    key = null;
                    while (key == null) {
                        key = screen.readInput();
                    }

                    switch (key.getKind()) {
                        case Escape:
                            screen.stopScreen();
                            running = false;
                            break;
                        case ArrowLeft:
                            CurrentPlayer.moveLeft();
                            if (CurrentPlayer.checkGlobalCollision()) {
                                CurrentPlayer.moveRight();
                            }
                            break;
                        case ArrowRight:
                            CurrentPlayer.moveRight();
                            if (CurrentPlayer.checkGlobalCollision()) {
                                CurrentPlayer.moveLeft();
                            }
                            break;
                        case ArrowUp:
                            CurrentPlayer.moveUp();
                            if (CurrentPlayer.checkGlobalCollision()) {
                                CurrentPlayer.moveDown();
                            }
                            break;
                        case ArrowDown:
                            CurrentPlayer.moveDown();
                            if (CurrentPlayer.checkGlobalCollision()) {
                                CurrentPlayer.moveUp();
                            }
                            break;
                        case Enter:

                            CurrentPlayer.aim();
                            turn = Turn.ENEMY;
                            if (CurrentEnemy.checkIfDead()) {
                                screen.clear();
                                globalDraw();
                                Thread.sleep(200);
                                screen.putString(47, 15, "YOU WIN!", WHITE, BLACK);
                                screen.refresh();
                                Thread.sleep(2000);
                                spawnEnemy();
                                score_p++;
                                round++;
                            }
                            break;
                        case Backspace:
                            spawnEnemy();
                            break;
                        case Delete:
                            CurrentEnemy.setTarget(CurrentPlayer);
                            CurrentEnemy.addMissChance();
                            CurrentEnemy.fire();
                            break;
                    }
                    break;
                case ENEMY:
                    for (int i = 0; i < 3; i++) {
                        switch (r.nextInt(2)) {
                            case 0:
                                CurrentEnemy.moveLeft();
                                break;
                            case 1:
                                CurrentEnemy.moveRight();
                                break;
                        }
                        Thread.sleep(400);
                        screen.clear();
                        globalDraw();
                        screen.refresh();
                    }

                    Thread.sleep(200);
                    CurrentEnemy.setTarget(CurrentPlayer);
                    CurrentEnemy.addMissChance();
                    CurrentEnemy.fire();
                    turn = Turn.PLAYER;
                    if (CurrentPlayer.checkIfDead()) {
                        screen.clear();
                        globalDraw();
                        Thread.sleep(100);
                        screen.putString(47, 15, "YOU LOOSE!", WHITE, BLACK);
                        screen.refresh();
                        Thread.sleep(2000);
                        score_e++;
                        spawnPlayer();
                        round++;

                    }
                    break;

            }
        }
    }
}
