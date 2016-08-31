package org.feiyang.watch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.feiyang.watch.Utils.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private TextView score_text;
    private String score = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score_text = (TextView) findViewById(R.id.score_text);
        score_text.setText("...");
        refreshGold();
    }

    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 1:
                    score_text.setText(msg.obj.toString());
                    //button.setText(R.string.text2);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public void refreshGold(){

        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true) {
                    HttpUtils.SyncGet("getScore", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Message message = new Message();
                                message.what = 1;
                                message.obj = response.getString("msg");
                                mHandler.sendMessage(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //score_text.setText("网络异常");
                            }
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void onClickLotteryButton(View view) {
        Intent intent = new Intent(this, LotteryActivity.class);
        intent.putExtra("Score",score);
        startActivity(intent);
    }

    public void onClickGiftButton(View view) {
        Intent intent = new Intent(this, GiftActivity.class);
        intent.putExtra("Score",score);
        startActivity(intent);
    }
}
