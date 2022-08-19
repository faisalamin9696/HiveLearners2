package com.example.hivelearners2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<MyList_POJO> {

    private int resourceLayout;
    private Context mContext;
    ArrayList<MyList_POJO> arrayList;
    onListItemClickListener mListener;

    public interface onListItemClickListener {
        void onDeleteClick(int position);
    }

    public MyAdapter(Context context, int resource, ArrayList<MyList_POJO> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.arrayList = items;
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
            TextView title_tv = (TextView) v.findViewById(R.id.title_lv_item);
            TextView subTitle_tv = (TextView) v.findViewById(R.id.subTitle_lv_item);
            TextView delete_btn = (TextView) v.findViewById(R.id.delete_lv_btn);


            if (title_tv != null) {
                title_tv.setText(pojo.getTitle());
            }

            if (subTitle_tv != null) {
                subTitle_tv.setText(pojo.getSubTitle());
            }

            if (delete_btn != null)

                delete_btn.setOnClickListener(v1 -> {

                    String delete_child = pojo.getPushKey();
                    FirebaseDatabase.getInstance().getReference("sendings").child(delete_child).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            this.notifyDataSetChanged();

                        } else
                            Toast.makeText(mContext, "Failed to delete", Toast.LENGTH_SHORT).show();

                    });


                });

        }

        return v;
    }


}