package org.feiyang.watch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.feiyang.watch.Utils.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class GiftActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private GiftPageAdaper giftPageAdaper;
//    private ProgressBar prgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

//        prgBar = (ProgressBar)findViewById(R.id.prgBar);
        viewPager = (ViewPager) findViewById(R.id.gift_pager);
//        setProgressBarIndeterminateVisibility(true);

        updateGiftInfoList();

    }

    private static int NUM_ITEMS = 0;
    private ArrayList<GiftInfo> giftInfoList = new ArrayList<>();

    public void onClickExchangeButton(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(GiftActivity.this).create();
        // TODO: 修改对话框样式 & 内容
        final GiftInfo currentGift = giftInfoList.get(viewPager.getCurrentItem());
        alertDialog.setMessage("礼物: " + currentGift.mName + " 价格:" + currentGift.mScore);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                        confirmExchange(currentGift.mId);
                    }
                });
        alertDialog.show();
    }

    // TODO: 兑换礼物 缺少确认兑换操作
    private void confirmExchange(String gId) {
        RequestParams params = new RequestParams();
        params.put("id", gId);
        HttpUtils.Post("exchangeGift", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AlertDialog alertDialog = new AlertDialog.Builder(GiftActivity.this).create();
                // TODO: 修改对话框样式 & 内容
                alertDialog.setMessage("兑换成功");//TODO: 用词不当
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }


    // TODO: 填充礼物列表
    private void updateGiftInfoList() {
        HttpUtils.Get("getNormalGiftList", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject json = response.getJSONObject("msg").getJSONObject("info");
                    Iterator<?> kv = json.keys();
                    while (kv.hasNext()) {//遍历日期
                        String key = kv.next().toString();
                        JSONArray arr = json.getJSONArray(key);
                        for (int i = 0; i < arr.length(); i++) {//遍历日期下的礼物
                            JSONObject list = arr.getJSONObject(i);
                            String id = list.getString("id");
                            String name = list.getString("name");
                            String score = list.getString("score");
                            giftInfoList.add(new GiftInfo(id, name, score));
                            ++NUM_ITEMS;
                        }
                    }
                    giftPageAdaper = new GiftPageAdaper(getSupportFragmentManager(), giftInfoList, NUM_ITEMS);
                    viewPager.setAdapter(giftPageAdaper);
                    viewPager.getCurrentItem();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static class GiftPageAdaper extends FragmentPagerAdapter {
        private int NUM_ITEMS = 0;
        private ArrayList<GiftInfo> giftInfoList = new ArrayList<>();


        public GiftPageAdaper(FragmentManager fragmentManager, ArrayList<GiftInfo> _giftInfoList, int numOfItems) {
            super(fragmentManager);
            NUM_ITEMS = numOfItems;
            giftInfoList = _giftInfoList;
        }

        @Override
        public Fragment getItem(int position) {
            return GiftFragment.newInstance(giftInfoList.get(position));
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
