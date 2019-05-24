package com.example.vinayak.pakingmaster.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayak.pakingmaster.R;
import com.example.vinayak.pakingmaster.pojo.CustomerOrderListData;

import java.util.List;


public class SlipListAdapter extends BaseAdapter {

    Activity activity;
    private static LayoutInflater inflater = null;
    List<CustomerOrderListData> customerOrderListData;

    public SlipListAdapter(){

    }

    public SlipListAdapter(Activity activity, List<CustomerOrderListData> customerOrderListData){

        this.activity = activity;
        this.customerOrderListData = customerOrderListData;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return customerOrderListData.size();
    }

    @Override
    public Object getItem(int position) {
        return customerOrderListData.get(position);
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
            view = inflater.inflate(R.layout.activity_slip_list, null);
            holder = new ViewHolder();
            holder.slipNumber = (TextView)view.findViewById(R.id.textViewSlipNumberVal);
            holder.slipDate = (TextView)view.findViewById(R.id.textViewSlipDateVal);
            holder.orderNo = (TextView)view.findViewById(R.id.textViewOrderNoVal);
            holder.orderDate = (TextView)view.findViewById(R.id.textViewOrderDateVal);
            holder.submitDate = (TextView)view.findViewById(R.id.textViewSubmitDateVal);
            holder.customerName = (TextView)view.findViewById(R.id.textViewCustomerNameVal);
            holder.noOfBoxes = (TextView)view.findViewById(R.id.textViewNoOfBoxesVal);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.slipNumber.setText(customerOrderListData.get(position).getSlipno());
        holder.slipDate.setText(customerOrderListData.get(position).getSlipdate());
        holder.orderNo.setText(customerOrderListData.get(position).getOrderno());
        holder.orderDate.setText(customerOrderListData.get(position).getOrderdate());
        holder.submitDate.setText(customerOrderListData.get(position).getSubmitdate());
        holder.customerName.setText(customerOrderListData.get(position).getCustname());
        holder.noOfBoxes.setText(customerOrderListData.get(position).getNoofboxes());

        return view;
    }


    static class ViewHolder {
        TextView slipNumber;
        TextView slipDate;
        TextView orderNo;
        TextView orderDate;
        TextView submitDate;
        TextView customerName;
        TextView noOfBoxes;
    }
}
