package cn.leafoct.deposit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import cn.leafoct.deposit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    NumberPicker year_picker, month_picker;
    Button inquiry_button;

    NetworkUtil net=new NetworkUtil();
    Calendar date=Calendar.getInstance();

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        year_picker=binding.year;
        month_picker=binding.month;
        inquiry_button=binding.button;

        FileUtil.context=getApplicationContext();

        initView();
        inquiry_button.setOnClickListener(this);
    }
    private void initView(){
        year_picker.setMaxValue(date.get(Calendar.YEAR));
        year_picker.setValue(year_picker.getMaxValue());
        year_picker.setMinValue(2020);
        month_picker.setMaxValue(12);
        month_picker.setMinValue(1);
        month_picker.setValue(date.get(Calendar.MONTH)+1);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==inquiry_button.getId()){
            var year=year_picker.getValue();
            var month=month_picker.getValue();
            new Thread(()->net.inquery(year+"", month+"")).start();
            inquiry_button.setEnabled(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void error(ErrorEvent e){
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show();
        if(!inquiry_button.isEnabled()){
            inquiry_button.setEnabled(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void next(String array){
        if(!inquiry_button.isEnabled()){
            inquiry_button.setEnabled(true);
        }
        var intent=new Intent(this, RecordActivity.class);
        intent.putExtra("json", array);
        intent.putExtra("year", year_picker.getValue()+"");
        intent.putExtra("month", month_picker.getValue()+"");
        startActivity(intent);
    }
}