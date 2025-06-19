package io.github.jadedchara.ashfall.common.block.utility;

public class posTest {
    int x = 25;
    int y = 25;
    int rot = 60;
    int rad = 3;
    int distx;
    int disty;
    int a;
    int b;
    public void Main() {
        //int tempRot;
        if(rot >= 360){
            rot = rot-360;
        }
        if (rot > 270) {
            //distx must be negative;
            //360-rot
        }else if (rot == 270) {
            a = x - rad;
            b = y;
        }else if (rot > 180){
            //both dists must be negative;
            //270-rot
        }else if (rot == 180){
            a = x;
            b = y - rad;
        }else if (rot > 90){
            //disty must be negative;
            //180-rot
        }else if(rot == 90){
            a = x + rad;
            b = y;
        }else if(rot > 0){
            //both dists are positive;
        }else if(rot == 0){
            a = x;
            b = y + rad;
        }
    }
}
