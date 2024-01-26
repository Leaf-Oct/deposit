package cn.leafoct.deposit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    private List<RecordItem> list;
    private Context context;

    public RecordAdapter(List<RecordItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.record_item, parent, false);
        }
        var item=list.get(position);

        var date_textview= (TextView) convertView.findViewById(R.id.date);
        date_textview.setText(item.date);
        var desc_textview=(TextView)convertView.findViewById(R.id.explanation);
        desc_textview.setText(item.description);
        var number_textview=(TextView)convertView.findViewById(R.id.number);
        number_textview.setText((item.is_expense?"-":"+")+Math.abs(item.value));
        var balance_textview=(TextView)convertView.findViewById(R.id.balance);
        balance_textview.setText(""+item.balance);
        return convertView;
    }
}
