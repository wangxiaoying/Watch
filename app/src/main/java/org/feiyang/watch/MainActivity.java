package org.feiyang.watch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
        HttpUtils.Get("getScore", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    score = response.getString("msg");
                    score_text.setText(score);
                } catch (JSONException e) {
                    e.printStackTrace();
                    score_text.setText("网络异常");
                }
            }
        });
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
