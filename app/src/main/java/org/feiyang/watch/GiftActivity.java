package org.feiyang.watch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class GiftActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private GiftPageAdaper giftPageAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        viewPager = (ViewPager) findViewById(R.id.gift_pager);
        giftPageAdaper = new GiftPageAdaper(getSupportFragmentManager());
        viewPager.setAdapter(giftPageAdaper);
        viewPager.getCurrentItem();
    }

    public void onClickExchangeButton(View view) {
        customDialog.Builder builder = new customDialog.Builder(this);
        // TODO: 修改对话框样式 & 内容
        // builder.setTitle("提示");
        builder.setMessage("是否兑换? " + viewPager.getCurrentItem());
        builder.setPositiveButton( "确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public static class GiftPageAdaper extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 0;
        private ArrayList<GiftInfo> giftInfoList = new ArrayList<>();

        public GiftPageAdaper(FragmentManager fragmentManager) {
            super(fragmentManager);
            updateGiftInfoList();
        }

        // TODO: 填充礼物列表
        private void updateGiftInfoList() {
            for (int i = 0; i < 5; ++i) {
                giftInfoList.add(new GiftInfo("" + 10 + i, "Test" + i));
                ++NUM_ITEMS;
            }
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("TEST", "position: " + position);
            return GiftFragment.newInstance(giftInfoList.get(position));
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
