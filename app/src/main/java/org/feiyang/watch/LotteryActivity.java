package org.feiyang.watch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.feiyang.watch.Utils.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

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
                //TODO: 记录奖励积分
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final String result = luckyPanView.mStrs[resultIndex];
                        RequestParams params = new RequestParams();
                        params.put("detail", "抽奖获得金币");
                        params.put("gold", result);
                        HttpUtils.Post("addScore", params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                customDialog.Builder builder = new customDialog.Builder(LotteryActivity.this);
                                builder.setMessage("抽奖结果: "+ ("未中奖".equals(result)?result:result+"金币"));
                                builder.setokButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.create().show();
                            }
                        });
                    }
                }, 200);
            }
        });

        HttpUtils.Get("getLotteryInfo", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject json = response.getJSONObject("msg");
                    for (int i = 0; i < json.length() - 1; i++) {
                        String item = json.getString("item" + (i + 1));
                        luckyPanView.mStrs[i] = "0".equals(item) ? "未中奖" : item;
//                        System.out.print(luckyPanView.mStrs[i]);
                    }
                    panControlBtn.setOnClickListener(panControlBtnClick);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private View.OnClickListener panControlBtnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!luckyPanView.isStart()) {
                // 抽奖扣除积分
                RequestParams params = new RequestParams();
                params.put("detail", "抽奖消耗金币");
                params.put("gold", "-5");
                HttpUtils.Post("addScore", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if ("0".equals(status)){
                                luckyPanView.luckyStart();
                                int delay_misec = random.nextInt(6666) + 2333;
                                Log.d("DELAY", "misec: " + delay_misec);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        luckyPanView.luckyEnd();
                                    }
                                }, delay_misec);
                            }else {
                                customDialog.Builder builder = new customDialog.Builder(LotteryActivity.this);
                                builder.setMessage("金币不足,无法抽奖!");
                                builder.setokButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                builder.create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    };
}
