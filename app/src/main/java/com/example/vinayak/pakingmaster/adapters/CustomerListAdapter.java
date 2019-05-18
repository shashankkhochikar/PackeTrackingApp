package com.example.vinayak.pakingmaster.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayak.pakingmaster.R;

import java.util.ArrayList;

public class CustomerListAdapter extends BaseAdapter {
    Activity activity;
    private static LayoutInflater inflater = null;
    ArrayList<String> customers;
    ArrayList<String> dates;

    public CustomerListAdapter(Activity activity, ArrayList<String> customers, ArrayList<String> dates) {
        this.activity = activity;
        this.customers = customers;
        this.dates = dates;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int position) {
        return customers.get(position);
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
            view = inflater.inflate(R.layout.activity_customer_list, null);
            holder = new ViewHolder();
            holder.customerName = (TextView) view.findViewById(R.id.cutomerName);
            holder.date = (TextView) view.findViewById(R.id.date);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.customerName.setText(customers.get(position).toString());
        holder.date.setText(dates.get(position).toString());
        return view;
    }

    static class ViewHolder {
        TextView customerName;
        TextView date;
    }
}
