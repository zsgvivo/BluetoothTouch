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

public class ClickerFragment extends Fragment {
    protected static Button upbtn, leftbtn, rightbtn, stopbtn, switchbtn;
    private Handler mHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clicker, container, false);
        upbtn = (Button) v.findViewById(R.id.clicker_up);
        leftbtn = (Button) v.findViewById(R.id.clicker_left);
        rightbtn = (Button) v.findViewById(R.id.clicker_right);
        stopbtn = (Button) v.findViewById(R.id.clicker_stop);
        switchbtn = (Button)v.findViewById(R.id.switchbtn);
        upbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mChatService != null) {
                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                        mHandler.post(new Runnable() {
                            public void run() {
                                MainActivity.mChatService.write(MainActivity.sendClickerButton + MainActivity.clickerUp + ";");
//                                MainActivity.mChatService.write("haha");
                            }
                        });
                    }
                }
            }
        });
        leftbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mChatService != null) {
                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                        mHandler.post(new Runnable() {
                            public void run() {
                                MainActivity.mChatService.write(MainActivity.sendClickerButton + MainActivity.clickerLeft + ";");
//                                MainActivity.mChatService.write("haha");
                            }
                        });
                    }
                }
            }
        });
        rightbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mChatService != null) {
                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                        mHandler.post(new Runnable() {
                            public void run() {
                                MainActivity.mChatService.write(MainActivity.sendClickerButton + MainActivity.clickerRight + ";");
//                                MainActivity.mChatService.write("haha");
                            }
                        });
                    }
                }
            }
        });
        stopbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mChatService != null) {
                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                        mHandler.post(new Runnable() {
                            public void run() {
                                MainActivity.mChatService.write(MainActivity.sendClickerButton + MainActivity.clickerStop + ";");
//                                MainActivity.mChatService.write("haha");
                            }
                        });
                    }
                }
            }
        });
        switchbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mChatService != null) {
                    if (MainActivity.mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                        mHandler.post(new Runnable() {
                            public void run() {
                                MainActivity.mChatService.write(MainActivity.sendClickerButton + MainActivity.clickerSwitch + ";");
//                                MainActivity.mChatService.write("haha");
                            }
                        });
                    }
                }
            }
        });
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
