package org.feiyang.watch;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LotteryActivity extends AppCompatActivity {

    private LuckyPanView luckyPanView;
    private Button panControlBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        luckyPanView = (LuckyPanView) findViewById(R.id.luck_pan_view);
        panControlBtn = (Button) findViewById(R.id.pan_control_btn);

        luckyPanView.setOnSpinFinshedCallback(new LuckyPanView.OnSpinFinshedCallback() {
            @Override
            public void onSpinFinished(final int resultIndex) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog alertDialog = new AlertDialog.Builder(LotteryActivity.this).create();
                        // TODO: 修改对话框样式 & 内容
                        alertDialog.setMessage("haha: " + resultIndex);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }, 200);
            }
        });

        panControlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("TEST", "on click");
                if (luckyPanView.isStart()) {
                    panControlBtn.setBackgroundColor(Color.GREEN);
                    luckyPanView.luckyEnd();
                } else {
                    panControlBtn.setBackgroundColor(Color.RED);
                    luckyPanView.luckyStart();
                }
            }
        });
    }

}
