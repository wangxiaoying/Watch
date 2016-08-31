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

import java.util.Date;
import java.util.Random;

public class LotteryActivity extends AppCompatActivity {

    private LuckyPanView luckyPanView;
    private Button panControlBtn;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        luckyPanView = (LuckyPanView) findViewById(R.id.luck_pan_view);
        panControlBtn = (Button) findViewById(R.id.pan_control_btn);
        random.setSeed(new Date().getTime());

        luckyPanView.setOnSpinFinshedCallback(new LuckyPanView.OnSpinFinshedCallback() {
            @Override
            public void onSpinFinished(final int resultIndex) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customDialog.Builder builder = new customDialog.Builder(LotteryActivity.this);
                        // TODO: 修改对话框样式 & 内容
                        builder.setMessage("金币不足,无法获取!!!!!!");
                        builder.setokButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    }
                }, 200);
            }
        });

        panControlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!luckyPanView.isStart()) {
                    luckyPanView.luckyStart();
                    int delay_misec = random.nextInt(3000)+1000;
//                    Log.d("DELAY", "misec: "+delay_misec);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            luckyPanView.luckyEnd();
                        }
                    }, delay_misec);

                }
            }
        });
    }

}
