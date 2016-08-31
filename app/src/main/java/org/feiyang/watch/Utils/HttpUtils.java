package org.feiyang.watch.Utils;

/**
 * Created by wabzsy on 16/8/31.
 */

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtils {
    private static final String Server = "http://96.126.97.242/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String uri = "index.php";

    public static void Get(String action, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(action), responseHandler);
    }

    public static void Post(String action, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(action), params, responseHandler);
    }

//    public static void Get(RequestParams params, JsonHttpResponseHandler responseHandler) {
//        client.get(getAbsoluteUrl(url), params, responseHandler);
//    }
//
//    public static void Post(RequestParams params, JsonHttpResponseHandler responseHandler) {
//        client.post(getAbsoluteUrl(url), params, responseHandler);
//    }

    private static String getAbsoluteUrl(String action) {
        return Server + uri + "?method=" + action;
    }
}