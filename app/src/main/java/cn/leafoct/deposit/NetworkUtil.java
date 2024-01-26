package cn.leafoct.deposit;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkUtil {
    private OkHttpClient client=new OkHttpClient();
    public void inquery(String year, String month){
        //编译时这里必报错，需要手动调整请求URL
        var request=new Request.Builder().get().url("https://"+Config.host+":"+Config.port+"/?year="+yer+"&month="+month).addHeader("Cookie", Config.key).build();
        Log.i("Network Request", request.url().toString());
        String result_string=null;
        try {
            var response=client.newCall(request).execute();
            result_string=response.body().string();
            if(result_string.equals("null")){
                EventBus.getDefault().post(new ErrorEvent("该年月无记录"));
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ErrorEvent("网络异常，具体请看日志"));
            var sw = new StringWriter();
            var pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            FileUtil.write(sw.toString());
            return;
        }
        JSONArray json_array=null;
        try {
            json_array=new JSONArray(result_string);
        } catch (JSONException e) {
            EventBus.getDefault().post(new ErrorEvent("解析记录数据出现问题，具体请看日志"));
            var sw = new StringWriter();
            var pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            FileUtil.write(sw.toString());
            FileUtil.write(result_string);
            return;
        }
        EventBus.getDefault().post(result_string);
    }
}
