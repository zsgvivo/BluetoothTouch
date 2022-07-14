package xyz.pengzhihui.BluetoothTouch;

public class Constant {
    //initialize the constant
    public static final int UNEXPORED = 0;
    public static final int AVAILABLE = 1;
    public static final int OCCUPIED = 2;
    public static int MapSize = 6;
    public static int dectection_cnt = 0;
    public static int valid_dist = 5;
    public static void reset(){
        dectection_cnt = 0;
    }
}
