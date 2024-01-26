package cn.leafoct.deposit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.leafoct.deposit.databinding.ActivityRecordBinding;

public class RecordActivity extends AppCompatActivity {
    ActivityRecordBinding binding;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle(getIntent().getStringExtra("year")+"年"+getIntent().getStringExtra("month")+"月");
        list=binding.list;
        list.setCacheColorHint(Color.TRANSPARENT);
        list.setDrawingCacheEnabled(true);
        list.setScrollingCacheEnabled(true);
        var record_list=initList(getIntent().getStringExtra("json"));
        var adapter=new RecordAdapter(record_list, this);
        list.setAdapter(adapter);
    }
    public List<RecordItem> initList(String json){
        List<RecordItem> list=new ArrayList<>();
        try {
            var json_array=new JSONArray(json);
            for (int i=0;i<json_array.length();i++){
                var obj=json_array.getJSONObject(i);
                var date=obj.getString("date");
                var description=obj.getString("description");
                var number=obj.getDouble("number");
                var balance=obj.getDouble("balance");
                var is_expense=obj.getBoolean("isexpand");
                var item=new RecordItem(date, description, number, balance, is_expense);
                list.add(item);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}