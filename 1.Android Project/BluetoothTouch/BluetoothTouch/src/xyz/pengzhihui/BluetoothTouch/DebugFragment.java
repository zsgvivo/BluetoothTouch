package xyz.pengzhihui.BluetoothTouch;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DebugFragment extends Fragment {
    private static TextView receive;
    protected static Button sendbutton, refreshbtn;
    private static TextInputEditText input;
    private Handler mHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.debug, container, false);
        //初始化对象
        receive = (TextView)v.findViewById(R.id.receive);
        sendbutton = (Button)v.findViewById(R.id.sendbtn);
        input = (TextInputEditText)v.findViewById(R.id.input);
        refreshbtn = (Button)v.findViewById(R.id.Refresh);
        receive.setMovementMethod(ScrollingMovementMethod.getInstance());
        //发送所输入的信息
        sendbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("android", "send" + input.getText().toString());
                if (MainActivity.mChatService != null && input.getText() != null) {
                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED){
                        mHandler.post(new Runnable()
                        {
                            public void run()
                            {
                                MainActivity.mChatService.write(input.getText().toString());
//                                MainActivity.mChatService.write("haha");
                            }
                        });
                    }
//                    MainActivity.mChatService.write("www");
                    //input.setText("");
                }
                else {
                    Log.e("debug", "mChatService == null");
                }
            }
        });
        refreshbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity.mChatService.read();
                Log.d("android", "refresh");
                updateView();
//                if (MainActivity.mChatService != null) {
//                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED){
//                        mHandler.post(new Runnable()
//                        {
//                            public void run()
//                            {
//
//                                MainActivity.mChatService.read();
//                                MainActivity.mChatService.write("haha");
//                            }
//                        });
//                    }
//                }
//                MainActivity.mChatService.read();
            }
        });
        updateView();
        return v;
    }

    public static void updateView() {
        MainActivity.mChatService.read();
        if(MainActivity.mChatService.ReceiveStr != null){
            receive.setText(MainActivity.mChatService.ReceiveStr);
        }
        Log.d("android", "uodate");

    }

    @Override
    public void onResume() {
        super.onResume();
        updateView(); // When the user resumes the view, then update the values
    }
}
