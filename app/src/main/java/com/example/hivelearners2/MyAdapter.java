package com.example.hivelearners2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<MyList_POJO> {

    private int resourceLayout;
    private Context mContext;
    public MyAdapter(Context context, int resource, ArrayList<MyList_POJO> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        MyList_POJO pojo = getItem(position);

        if (pojo != null) {
            TextView title_tv = (TextView) v.findViewById(R.id.title);
            TextView subTitle_tv = (TextView) v.findViewById(R.id.subTitle_lv_item);
            TextView delete_btn = (TextView) v.findViewById(R.id.delete_lv_btn);

            if (title_tv != null) {
                title_tv.setText(pojo.getTitle());
            }

            if (subTitle_tv != null) {
                subTitle_tv.setText(pojo.getSubTitle());
            }

        }

        return v;
    }

}