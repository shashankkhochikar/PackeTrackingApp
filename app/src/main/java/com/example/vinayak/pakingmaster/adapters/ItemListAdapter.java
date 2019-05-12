package com.example.vinayak.pakingmaster.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayak.pakingmaster.CustomerDetailsActivity;
import com.example.vinayak.pakingmaster.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {
    Activity activity;
    private static LayoutInflater inflater = null;
    ArrayList<String>items;

    public ItemListAdapter(Activity activity, ArrayList<String> items) {
        this.activity = activity;
        this.items = items;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_item, null);
            holder = new ViewHolder();
            holder.itemName = (TextView) view.findViewById(R.id.itemName);
            holder.imgModify = (ImageView) view.findViewById(R.id.modifyItem);
            holder.imgDelete = (ImageView) view.findViewById(R.id.deleteItem);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemName.setText(items.get(position).toString());


        holder.imgModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setMessage("Are you sure,You wanted to delete this item");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });
        return view;
    }

    static class ViewHolder {
        TextView itemName;
        ImageView imgModify;
        ImageView imgDelete;
    }
}
