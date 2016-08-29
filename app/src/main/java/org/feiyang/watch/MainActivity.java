package org.feiyang.watch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLotteryButton(View view) {
        Intent intent = new Intent(this, LotteryActivity.class);
        startActivity(intent);
    }

    public void onClickGiftButton(View view) {
        Intent intent = new Intent(this, GiftActivity.class);
        startActivity(intent);
    }
}
