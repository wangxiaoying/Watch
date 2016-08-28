package org.feiyang.watch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LotteryActivity extends AppCompatActivity {

    private LuckyPanView luckyPanView;
    private Button panControlBtn;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        luckyPanView = (LuckyPanView)findViewById(R.id.luck_pan_view);
        panControlBtn = (Button)findViewById(R.id.pan_control_btn);
        resultText = (TextView) findViewById(R.id.result_text);

        luckyPanView.setOnSpinFinshedCallback(new LuckyPanView.OnSpinFinshedCallback() {
            @Override
            public void onSpinFinished(final int resultIndex) {
                resultText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TEST", "begin set " + resultIndex);
                        resultText.setText("" + resultIndex);
                    }
                }, 200);
            }
        });

        panControlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Log.d("TEST", "on click");
                if(luckyPanView.isStart()) {
                    panControlBtn.setBackgroundColor(Color.GREEN);
                    luckyPanView.luckyEnd();
                } else
                {
                    panControlBtn.setBackgroundColor(Color.RED);
                    luckyPanView.luckyStart();
                }
            }
        });
    }

}
