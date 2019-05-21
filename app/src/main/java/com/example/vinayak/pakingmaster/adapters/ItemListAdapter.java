package com.example.vinayak.pakingmaster.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.example.vinayak.pakingmaster.CustomerDetailsActivity;
import com.example.vinayak.pakingmaster.R;
import com.example.vinayak.pakingmaster.pojo.Item;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONObject;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {
    Activity activity;
    private static LayoutInflater inflater = null;
    ArrayList<Item>items;
    public String strBarcode;

    public ItemListAdapter(){

    }

    public ItemListAdapter(Activity activity, ArrayList<Item> items) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_item, null);
            holder = new ViewHolder();
            holder.itemName = (TextView) view.findViewById(R.id.itemName);
            /*holder.imgModify = (ImageView) view.findViewById(R.id.modifyItem);*/
            holder.itemQty = (EditText)view.findViewById(R.id.edTxtListItemQty);
            holder.itemBoxNo = (EditText)view.findViewById(R.id.edTxtListItemBoxNo);
            holder.imgDelete = (ImageView) view.findViewById(R.id.deleteItem);
            holder.scanItem = (ImageView) view.findViewById(R.id.scanItem);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemName.setText(items.get(position).getItemName().toString());
        holder.itemQty.setText(items.get(position).getItemQty().toString());
        holder.itemBoxNo.setText(items.get(position).getItemBoxNo().toString());


        holder.scanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });

        /*holder.imgModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setMessage("Are you sure,You wanted to delete this item");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                items.remove(position);
                                notifyDataSetChanged();
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
        /*ImageView imgModify;*/
        EditText itemQty;
        EditText itemBoxNo;
        ImageView imgDelete;
        ImageView scanItem;
    }

    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */

    }

    private void showDialog(int position){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity).create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_barcode_alert_dialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        final ImageView imageScanBarcode = (ImageView) dialogView.findViewById(R.id.scanBarcode);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);

        editText.setText(items.get(position).getItemBarcode().toString());

        /*button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });*/
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startScan();
                final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                        .withActivity(activity)
                        .withEnableAutoFocus(true)
                        .withBleepEnabled(true)
                        .withBackfacingCamera()
                        .withText("Scanning...")
                        .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                            @Override
                            public void onResult(Barcode barcode) {
                                Toast.makeText(activity,barcode.rawValue,Toast.LENGTH_LONG).show();
                                strBarcode = barcode.rawValue;
                                editText.setText(strBarcode);
                            }
                        })
                        .build();
                materialBarcodeScanner.startScan();

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
