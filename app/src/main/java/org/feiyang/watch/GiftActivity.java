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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
//        Bundle bundle;
//        bundle = this.getIntent().getExtras();
//        System.out.print(bundle.getString("Score"));

        viewPager = (ViewPager) findViewById(R.id.gift_pager);

        updateGiftInfoList();

    }

    private static int NUM_ITEMS = 0;
    private ArrayList<GiftInfo> giftInfoList = new ArrayList<>();

    public void onClickExchangeButton(View view) {
        customDialog.Builder builder = new customDialog.Builder(this);
        // TODO: 修改对话框样式 & 内容
        // builder.setTitle("提示");
        final GiftInfo currentGift = giftInfoList.get(viewPager.getCurrentItem());
        builder.setMessage("是否兑换'" + currentGift.mName + "'?");
        builder.setPositiveButton( "确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        confirmExchange(currentGift.mId);
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

    private void confirmExchange(String gId) {
        RequestParams params = new RequestParams();
        params.put("id", gId);
        HttpUtils.Post("exchangeGift", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String status = response.getString("status");
                    AlertDialog alertDialog = new AlertDialog.Builder(GiftActivity.this).create();
                    if ("0".equals(status)){
                        alertDialog.setMessage("申请成功");
                    }else{
                        alertDialog.setMessage("积分不够, 快去努力赚积分!");
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                    NUM_ITEMS = 0;
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
            Log.d("TEST", "position: " + position);
            System.out.println("NumGI:"+NUM_ITEMS);
            System.out.println("szGI:"+giftInfoList.size());
            System.out.println("positionGI:"+position);
            return GiftFragment.newInstance(giftInfoList.get(position));
        }

        @Override
        public int getCount() {
            System.out.println("NumGC:"+NUM_ITEMS);
            System.out.println("szGC:"+giftInfoList.size());
            return NUM_ITEMS;
        }
    }
}
