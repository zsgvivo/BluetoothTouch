package xyz.pengzhihui.BluetoothTouch;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
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
    protected static Button sendbutton;
    private static TextInputEditText input;
    private Handler mHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.debug, container, false);
        //初始化对象
        receive = (TextView)v.findViewById(R.id.receive);
        sendbutton = (Button)v.findViewById(R.id.sendbtn);
        input = (TextInputEditText)v.findViewById(R.id.input);
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
                            }
                        });
                    }
                    input.setText("");
                }
                else {
                    Log.e("debug", "mChatService == null");
                }

            }
        });
        updateView();
        return v;
    }

    public static void updateView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        updateView(); // When the user resumes the view, then update the values
    }
}
