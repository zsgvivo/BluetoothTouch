package xyz.pengzhihui.BluetoothTouch;
import android.os.Handler;

import java.lang.Math;
import java.util.Objects;

public class Car {
    public Point Position;
    public int Angle;
//    private Handler mHandler = new Handler();
//    private Runnable mRunnable;

    Car(Point pt, int angle)
    {
        Position = pt;
        Angle = angle;
    }
    public void turnleft() {
        Angle = (Angle + 90) % 360;
        MapFragment.Print("Turn left...\n");

        if (MainActivity.mChatService != null) {
            if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
//                mHandler.post(new Runnable() {
//                    public void run() {
                MainActivity.mChatService.write(MapFragment.leftcommand);
                MainActivity.mChatService.clear();
                String str = "";
                while(true){
                    try
                    {
                        Thread.sleep(500);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    MainActivity.mChatService.read();
                    str = MainActivity.mChatService.ReceiveStr;
//                    if(str != null){
//                        MapFragment.Print(str + "\n");
//                    }
//                    if(Objects.equals(str, MapFragment.commanddone)){
//                        break;
//                    }
                    if(str.length() >= MapFragment.commanddone.length() && Objects.equals(str.substring(str.length() - MapFragment.commanddone.length(), str.length()), MapFragment.commanddone)){
                        MapFragment.Print("Done\n");
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        break;
                    }
//                    MainActivity.mChatService.clear();
                }
            }
        }
//                });
//            }
//        }
    }
    public void turnright() {
        Angle = (Angle + 270) % 360;
        MapFragment.Print("Turn right...\n");

        if (MainActivity.mChatService != null) {
            if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                MainActivity.mChatService.write(MapFragment.rightcommand);
                MainActivity.mChatService.clear();
                String str = "";
                while(true){
                    try
                    {
                        Thread.sleep(500);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    MainActivity.mChatService.read();
                    str = MainActivity.mChatService.ReceiveStr;
//                    if(str != null){
//                        MapFragment.Print(str + "\n");
//                    }
//                    if(Objects.equals(str, MapFragment.commanddone)){
//                        break;
//                    }
                    if(str.length() >= MapFragment.commanddone.length() && Objects.equals(str.substring(str.length() - MapFragment.commanddone.length(), str.length()), MapFragment.commanddone)){
                        MapFragment.Print("Done\n");
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        break;
                    }
//                    MainActivity.mChatService.clear();
                }
            }
        }
    }
    public void turnreset(){
        if(Angle == 0){
            return;
        }
        else if(Angle == 90){
            turnright();
        }
        else if(Angle == 180){
            turnright();
            turnright();
        }
        else if(Angle == 270){
            turnleft();
        }
    }
    public void turnto(int angle){
        if((Angle - angle) % 360 == 0){
            return;
        }
        if((Angle - angle - 90) % 360 == 0){
            turnright();
            return;
        }
        if((Angle - angle - 270) % 360 == 0){
            turnleft();
            return;
        }
        if((Angle - angle - 180) % 360 == 0){
            turnleft();
            turnleft();
            return;
        }
    }
    public void move(int dist){
        MapFragment.Print("Move...\n");

        Position.x += (int)(dist * Math.cos(Angle * Math.PI / 180));
        Position.y += (int)(dist * Math.sin(Angle * Math.PI / 180));
        if (MainActivity.mChatService != null) {
            if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
//                mHandler.post(new Runnable() {
//                    public void run() {
                MainActivity.mChatService.write(MapFragment.movecommand);
////                                MainActivity.mChatService.write("haha");
                MainActivity.mChatService.clear();
                String str = "";
                while(true){
                    try
                    {
                        Thread.sleep(500);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    MainActivity.mChatService.read();
                    str = MainActivity.mChatService.ReceiveStr;
//                    if(str != null){
//                        MapFragment.Print(str + "\n");
//                    }
//                    if(Objects.equals(str, MapFragment.commanddone)){
//                        break;
//                    }
                    if(str.length() >= MapFragment.commanddone.length() && Objects.equals(str.substring(str.length() - MapFragment.commanddone.length(), str.length()), MapFragment.commanddone)){
                        MapFragment.Print("Done\n");
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        break;
                    }
//                    MainActivity.mChatService.clear();
                }
            }
        }
        MapFragment.Print("Current Position:" + '(' + Position.x + ", " + Position.y + ')' + "\n");
    }

}
