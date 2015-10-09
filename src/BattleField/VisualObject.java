package BattleField;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal.Color;

public class VisualObject {

    int[] pos = new int[2];
    String[] look;
    Color colorBg;
    Color colorFg;

    public VisualObject(int x_pos, int y_pos) {
        this.pos[0] = x_pos;
        this.pos[1] = y_pos;
    }
    
    public void draw(Screen toDrawOn) {
        for(int i = 0;i<look.length;i++){
        toDrawOn.putString(pos[0], pos[1]+i, look[i], colorFg, colorBg);
        }
    }
    public void moveLeft(){
        this.pos[0]--;
    }
    public void moveRight(){
        this.pos[0]++;
    }
    public void moveUp(){
        this.pos[1]--;
    }
    public void moveDown(){
        this.pos[1]++;
    }

}
