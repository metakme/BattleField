package BattleField;

import static BattleField.BattleField.objects_to_draw;
import static BattleField.BattleField.screen;
import com.googlecode.lanterna.terminal.Terminal;

public class Explosion extends VisualObject{
        
    
    String[][] Frames = new String[4][];
    int[][] newposmod = new int[4][];
    public Explosion(int x_pos,int y_pos){
        super(x_pos,y_pos);
        this.colorFg = Terminal.Color.YELLOW;
        this.look = new String[]{"@"};
        
        Frames[0] = new String[]{" #",
                                 "#@#",
                                 " #"};
        newposmod[0]= new int[]{1,1};
        Frames[1] = new String[]{" #@#",
                                 "#@ @#",
                                 " #@#"};
        newposmod[1]= new int[]{1,0};
        Frames[2] = new String[]{"   #",
                                 " ## ##",
                                 "##   ##",
                                 " ## ## ",
                                 "   #"};        
        newposmod[2]= new int[]{1,1};
        Frames[3] = new String[]{"   #   #",
                                 "",                                 
                                 "#  #   #  #",
                                 "",
                                 "   #   #"};
        newposmod[3]= new int[]{2,0};
    }
    public void explode() throws InterruptedException{
            objects_to_draw.add(this);
        for (int i =0;i<Frames.length;i++) {
            this.look = Frames[i];
            this.pos[0] = this.pos[0]-newposmod[i][0];
            this.pos[1] = this.pos[1]-newposmod[i][1];
            screen.clear();
            BattleField.globalDraw();
            screen.refresh();
            Thread.sleep(100);                    
        }
        objects_to_draw.remove(this);
    }
//    @Override
//    public void draw(Screen toDrawOn) {
//        toDrawOn.putString(pos[0], pos[1], look[0], colorFg, colorBg);
//    }
}

