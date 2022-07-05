package xyz.pengzhihui.BluetoothTouch;

import android.Manifest;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;
import java.lang.Math;
// import java.util.Collection;
// import java.util.Vector;
import java.util.*;
import java.util.concurrent.TimeUnit;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class MapFragment extends Fragment {

    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private static Button startbtn;
    private static TextView debug;
    private static EditText initx;
    private static EditText inity;
    private static EditText initangle;
    private static EditText mapsize;

    public static Point pt;
    public static Car mycar;
    public static Environment myenv;
    public static Point[][] mymap = new Point[Constant.MapSize][Constant.MapSize];
    public static String leftcommand = MainActivity.sendCommand + "l" + ";";
    public static String rightcommand = MainActivity.sendCommand +"r" + ";";
    public static String movecommand = MainActivity.sendCommand + "m" + ";";
    public static String detectcommand = MainActivity.sendCommand + "d" + ";";
    public static String commanddone = "done";


    static final Handler myHandler = new Handler()
    {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
//            if(msg.what == 0x123)
//            {
////                imgchange.setImageResource(imgids[imgstart++ % 8]);
//
//            }
            debug.setText(debugstr);
            refreshTextView(debug);
        }
    };


    public static String debugstr;
    public static void Print(String str){
//        debugstr = debug.getText().toString();
        debugstr = debugstr + str;
        Log.d("maptext", "set");
        myHandler.sendEmptyMessage(0x123);
//        try
//        {
//            Thread.sleep(20);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//        myHandler.sendEmptyMessage(0x123);


    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, container, false);
        startbtn = (Button)v.findViewById(R.id.startbtn);
        debug = (TextView)v.findViewById(R.id.debug);
        initx = (EditText)v.findViewById(R.id.initx);
        inity = (EditText)v.findViewById(R.id.inity);
        initangle = (EditText)v.findViewById(R.id.initangle);
        mapsize = (EditText)v.findViewById(R.id.mapsize);

        debug.setMovementMethod(ScrollingMovementMethod.getInstance());
        debugstr = "";
        startbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Print("Start!\n");
                        Constant.reset();
                        test();
                        Log.d("onclick", "done");
                    }
                }).start();

            }
        });

        updateView();
        return v;
    }

    private static void refreshTextView(TextView textView){
        int offset=textView.getLineCount()*textView.getLineHeight();
        if(offset>(textView.getHeight()-textView.getLineHeight()-20)){
            textView.scrollTo(0,offset-textView.getHeight()+textView.getLineHeight()+20);
        }
    }

    public static void updateView() {

//        int offset=receive.getLineCount()*(receive.getLineHeight());
//        if(offset>receive.getHeight()){
//            receive.scrollTo(0,offset-receive.getHeight());
//        }
        Log.d("android", "update map");

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public static void detect(){
        Constant.dectection_cnt++;
        MapFragment.Print("Detect...\n");
        String str = "";
        String diststr = "";
        int dist = 1;
        if (MainActivity.mChatService != null) {
            if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                MainActivity.mChatService.write(MapFragment.detectcommand);
                MainActivity.mChatService.clear();

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
                    if(str.length() >= 5 && Objects.equals(str.substring(str.length() - 4, str.length()), MapFragment.commanddone)){
                        MapFragment.Print("Done\n");
                        diststr = str.substring(0, str.length() - 4);
                        dist = Integer.parseInt(diststr);
                        break;
                    }
                }
            }
        }
        if(mycar.Angle == 0){
            int x = mycar.Position.x;
            int y = mycar.Position.y;
            while(x < mycar.Position.x + dist && x < Constant.MapSize){
                mymap[x][y].state = Constant.AVAILABLE;
                x++;
            }
            if(x < Constant.MapSize) {
                mymap[x][y].state = Constant.OCCUPIED;
            }
        }
        else if(mycar.Angle == 90){
            int x = mycar.Position.x ;
            int y = mycar.Position.y ;
            while(y < mycar.Position.y + dist && y < Constant.MapSize){
                mymap[x][y].state = Constant.AVAILABLE;
                y++;
            }
            if(y < Constant.MapSize) {
                mymap[x][y].state = Constant.OCCUPIED;
            }
        }
        else if(mycar.Angle == 180){
            int x = mycar.Position.x;
            int y = mycar.Position.y;
            while(x > mycar.Position.x - dist && x >= 0){
                mymap[x][y].state = Constant.AVAILABLE;
                x--;
            }
            if(x >= 0) {
                mymap[x][y].state = Constant.OCCUPIED;
            }
        }
        else if(mycar.Angle == 270){
            int x = mycar.Position.x ;
            int y = mycar.Position.y ;
            while(y > mycar.Position.y - dist && y >= 0){
                mymap[x][y].state = Constant.AVAILABLE;
                y--;
            }
            if(y >= 0) {
                mymap[x][y].state = Constant.OCCUPIED;
            }
        }


//        if(mycar.Angle == 0){
//            int x = mycar.Position.x + 1;
//            int y = mycar.Position.y;
//            while(x < Constant.MapSize){
//                mymap[x][y].state = myenv.getstate(x, y);
//                if (mymap[x][y].state == Constant.OCCUPIED){
//                    break;
//                }
//                x++;
//            }
//        }
//        else if(mycar.Angle == 90){
//            int x = mycar.Position.x;
//            int y = mycar.Position.y + 1;
//            while(y < Constant.MapSize){
//                mymap[x][y].state = myenv.getstate(x, y);
//                if (mymap[x][y].state == Constant.OCCUPIED){
//                    break;
//                }
//                y++;
//            }
//        }
//        else if(mycar.Angle == 180){
//            int x = mycar.Position.x - 1;
//            int y = mycar.Position.y;
//            while(x >= 0){
//                mymap[x][y].state = myenv.getstate(x, y);
//                if (mymap[x][y].state == Constant.OCCUPIED){
//                    break;
//                }
//                x--;
//            }
//        }
//        else if(mycar.Angle == 270){
//            int x = mycar.Position.x;
//            int y = mycar.Position.y - 1;
//            while(y >= 0){
//                mymap[x][y].state = myenv.getstate(x, y);
//                if (mymap[x][y].state == Constant.OCCUPIED){
//                    break;
//                }
//                y--;
//            }
//        }
    }
    public static void printstate(Point[][] map){
//        System.out.println("state map after " + Constant.dectection_cnt + " detections");
        Print("state map after " + Constant.dectection_cnt + " detections\n");
        for (int i = 0; i < Constant.MapSize; i++) {
            for (int j = 0; j < Constant.MapSize; j++) {
//                System.out.print(map[i][j].state + " ");
                Print(map[i][j].state + " ");
            }
//            System.out.println();
            Print("\n");
        }

    }
    public static Vector getneibghbor(Point p){
        Vector neighbor = new Vector<Point>();
        if(p.x > 0){
            neighbor.add(mymap[p.x - 1][p.y]);
        }
        if(p.x < Constant.MapSize - 1){
            neighbor.add(mymap[p.x + 1][p.y]);
        }
        if(p.y > 0){
            neighbor.add(mymap[p.x][p.y - 1]);
        }
        if(p.y < Constant.MapSize - 1){
            neighbor.add(mymap[p.x][p.y + 1]);
        }
        return neighbor;
    }
    public static boolean isborder(Point p){
        if(mymap[p.x][p.y].state != Constant.AVAILABLE){
            return false;
        }
        else{
            Vector neighbor = getneibghbor(p);
            for(int i = 0; i < neighbor.size(); i++){
                if(mymap[((Point)neighbor.get(i)).x][((Point)neighbor.get(i)).y].state == Constant.UNEXPORED){
                    return true;
                }
            }
        }
        return false;
    }
    public static Vector getborder(){
        Vector border = new Vector<Point>();
        for(int i = 0; i < Constant.MapSize; i++){
            for(int j = 0; j < Constant.MapSize; j++){
                if(isborder(mymap[i][j])){
                    border.add(mymap[i][j]);
                }
            }
        }
        return border;
    }
    public static void turnto(Car mycar, int angle){
        if((mycar.Angle - angle) % 360 == 0){
            return;
        }
        if((mycar.Angle - angle) % 360 == 90){
            mycar.turnright();
            return;
        }
        if((mycar.Angle - angle) % 360 == 270){
            mycar.turnleft();
            return;
        }
        if((mycar.Angle - angle) % 360 == 180){
            mycar.turnleft();
            mycar.turnleft();
            return;
        }

    }
    public static void gotoneighbor(Point p){
        if(mycar.Position.x == p.x && mycar.Position.y == p.y){
            return;
        }
        if(p.state!=Constant.AVAILABLE){
            return;
        }
        if(p.x == mycar.Position.x){
            if(p.y > mycar.Position.y){
                // mycar.turnreset();
                // mycar.turnleft();
                turnto(mycar, 90);
                mycar.move(1);
            }
            else{
                // mycar.turnreset();
                // mycar.turnright();
                turnto(mycar, 270);
                mycar.move(1);
            }
        }
        else if(p.y == mycar.Position.y){
            if(p.x > mycar.Position.x){
                // mycar.turnreset();
                turnto(mycar, 0);
                mycar.move(1);
            }
            else{
                // mycar.turnreset();
                // mycar.turnright();
                // mycar.turnright();
                turnto(mycar, 180);
                mycar.move(1);
            }
        }
    }
    public static Vector findpath(Point Start, Point end){
        if (end.state!=Constant.AVAILABLE || (Start.x == end.x && Start.y == end.y)){
            return null;
        }
        Vector path = new Vector<Point>();
        Vector queue = new Vector<Point>();
        queue.add(Start);
        boolean[][] visited = new boolean[Constant.MapSize][Constant.MapSize];
        Point[][] pathfrom = new Point[Constant.MapSize][Constant.MapSize];
        for(int i = 0; i < Constant.MapSize; i++){
            for(int j = 0; j < Constant.MapSize; j++){
                pathfrom[i][j] = new Point(-1, -1);
            }
        }
        visited[Start.x][Start.y] = true;
        while(!queue.isEmpty()){
            Point point = (Point)queue.remove(0);
            Vector neighbor = getneibghbor(point);
            for(int i = 0; i < neighbor.size(); i++){
                Point p = (Point)neighbor.get(i);
                if(p.state == Constant.AVAILABLE && !visited[p.x][p.y]){
                    visited[p.x][p.y] = true;
                    pathfrom[p.x][p.y] = point;
                    queue.add(p);
                    if(p.x == end.x && p.y == end.y){
                        path.add(p);
                        Point tem = pathfrom[p.x][p.y];
                        while(tem.x != Start.x || tem.y != Start.y){
                            path.add(tem);
                            tem = pathfrom[tem.x][tem.y];
                        }
                        // return path;
                    }
                }
            }
        }
        return path;
    }
    public static void GoTo(Point target){
//        System.out.println("go to " + target.x + " " + target.y + " ");
        Print("Go To (" + target.x + ", " + target.y + ") \n");
        Vector path = findpath(mycar.Position, target);

        if(path != null){
            for(int i = path.size() - 1; i >= 0; i--){
                gotoneighbor((Point)path.get(i));
            }
        }
        else{
//            System.out.println("no path");
            Print("no path\n");
        }
    }
    public static void detectaround(){
        detect();
        mycar.turnleft();
        detect();
        mycar.turnleft();
        detect();
        mycar.turnleft();
        detect();
        // mycar.turnleft();
    }
    public static boolean allexplored(){
        for(int i = 0; i < Constant.MapSize; i++){
            for(int j = 0; j < Constant.MapSize; j++){
                if(mymap[i][j].state == Constant.UNEXPORED){
                    return false;
                }
            }
        }
        return true;
    }
    public static void test(){
//        mycar = new Car(new Point(0, 0), 0);
        //init
        Constant.MapSize = Integer.parseInt(mapsize.getText().toString());
        mycar = new Car(new Point(Integer.parseInt(initx.getText().toString()), Integer.parseInt(inity.getText().toString())), Integer.parseInt(initangle.getText().toString()));
        mymap = new Point[Constant.MapSize][Constant.MapSize];
        myenv = new Environment(Constant.MapSize);
        for (int i = 0; i < Constant.MapSize; i++) {
            for (int j = 0; j < Constant.MapSize; j++) {
                mymap[i][j] = new Point(i,j, Constant.UNEXPORED);
            }
        }
        mymap[mycar.Position.x][mycar.Position.y].state = Constant.AVAILABLE;
        Vector havevisited = new Vector<Point>();

        printstate(mymap);
//        detect();
//        printstate(mymap);
//        mycar.turnleft();
//        detect();
//        printstate(mymap);
        detectaround();
        havevisited.add(mymap[mycar.Position.x][mycar.Position.y]);
        printstate(mymap);

        for(int i = 0; i < (Constant.MapSize * Constant.MapSize); i++){
            Vector border = getborder();
            for(int j = 0; j < border.size(); j++){
                Point p = (Point)border.get(j);
                if(!havevisited.contains(p)){
                    GoTo(p);
                    havevisited.add(p);
                    detectaround();
                    printstate(mymap);
                }
            }
            if(allexplored()){
                break;
            }
        }
        Print("all done!\n");
    }
}
