package com.example.vinayak.pakingmaster.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.example.vinayak.pakingmaster.Application;
import com.example.vinayak.pakingmaster.CustomerDetailsActivity;
import com.example.vinayak.pakingmaster.R;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.DeleteItemResponseData;
import com.example.vinayak.pakingmaster.pojo.GetCustomerListResponse;
import com.example.vinayak.pakingmaster.pojo.Item;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.vinayak.pakingmaster.utils.Constant.slipNumberFromList;

public class ItemListAdapter extends BaseAdapter {

    private static final String TAG = ItemListAdapter.class.getName();
    Activity activity;
    private static LayoutInflater inflater = null;
    List<Item> items;
    public String strBarcode;
    Item item = new Item();
    String modeOfOpration;

    public ItemListAdapter(Activity activity, List<Item> items, String modeOfOpration) {
        this.activity = activity;
        this.items = items;
        this.modeOfOpration = modeOfOpration;
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
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_item, null);
            holder = new ViewHolder();
            holder.itemName = (TextView) view.findViewById(R.id.itemName);
            /*holder.imgModify = (ImageView) view.findViewById(R.id.modifyItem);*/
            holder.itemQty = (EditText) view.findViewById(R.id.edTxtListItemQty);
            holder.itemBoxNo = (EditText) view.findViewById(R.id.edTxtListItemBoxNo);
            holder.itemUmo = (EditText) view.findViewById(R.id.edTxtListItemUmo);
            holder.imgDelete = (ImageView) view.findViewById(R.id.deleteItem);
            holder.scanItem = (ImageView) view.findViewById(R.id.scanItem);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (modeOfOpration.equals("2") || modeOfOpration.equals("3")) {
            holder.itemBoxNo.setEnabled(false);
            holder.itemQty.setEnabled(false);
            // holder.scanItem.setEnabled(false);
            holder.imgDelete.setEnabled(false);

        }

        holder.itemName.setText(items.get(position).getItemName().toString());

        holder.itemQty.setText(items.get(position).getItemQty().toString());
        holder.itemQty.setId(position);
        holder.itemBoxNo.setText(items.get(position).getItemBoxNo().toString());
        holder.itemBoxNo.setId(position);

        holder.itemUmo.setText(items.get(position).getUom().toString());

        holder.itemBoxNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (!holder.itemBoxNo.getText().toString().equals("")) {
                        Constant.isAllItemBoxNoFilled = true;
                        if (Integer.parseInt(holder.itemBoxNo.getText().toString()) <= 0) {
                            Toast.makeText(activity, "BoxNo Should be Greater then zero", Toast.LENGTH_LONG).show();
                            Constant.isAllItemBoxNoFilled = false;
                        } else {
                            final int pos = v.getId();
                            final EditText Caption = (EditText) v;
                            /*items.get(position).setItemBoxNo(holder.itemBoxNo.getText().toString());*/
                            items.get(pos).setItemBoxNo(Caption.getText().toString());
                            Log.e(TAG, items.get(pos).getItemBoxNo().toString());
                            Constant.isAllItemBoxNoFilled = true;
                        }
                    } else {
                        Constant.isAllItemBoxNoFilled = false;
                    }
                }
            }
        });

        holder.itemQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!holder.itemQty.getText().toString().equals("")) {
                        Constant.isAllItemQtyFilled = true;
                        if (Float.parseFloat(holder.itemQty.getText().toString()) <= 0) {
                        //if (Integer.parseInt(holder.itemQty.getText().toString()) < 1) {
                            Toast.makeText(activity, "Qty Should be Greater then zero", Toast.LENGTH_LONG).show();
                            Constant.isAllItemQtyFilled = false;
                        } else {
                            final int pos = v.getId();
                            final EditText Caption = (EditText) v;
                            /*items.get(position).setItemQty(holder.itemQty.getText().toString());*/
                            items.get(pos).setItemQty(Caption.getText().toString());
                            Log.e(TAG, items.get(pos).getItemQty().toString());
                            Constant.isAllItemQtyFilled = true;
                        }
                    } else {
                        Constant.isAllItemQtyFilled = false;
                    }
                }
            }
        });

        holder.scanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
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
                                
                                if (items.get(position).getId() != null) {
                                    Log.e("Ewwww","Ok");
                                    try {
                                        JSONObject jo = new JSONObject();
                                        jo.put("id", items.get(position).getId());
                                        jo.put("slipno", slipNumberFromList);

                                        GsonRequest<DeleteItemResponseData> deleteItemResquest = new GsonRequest<>(Request.Method.POST, Constant.DELETE_ITEM, jo.toString(), DeleteItemResponseData.class,
                                                new Response.Listener<DeleteItemResponseData>() {
                                                    @Override
                                                    public void onResponse(@NonNull DeleteItemResponseData response) {

                                                        if (response.getError() != null) {
                                                            Log.e(ItemListAdapter.class.getName(), response.getError().getErrorMessage());
                                                        } else {
                                                            if (response.getSuccess() == 1) {
                                                                items.remove(position);
                                                                notifyDataSetChanged();
                                                            }
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.e(ItemListAdapter.class.getName(), error.getMessage());
                                            }
                                        });
                                        deleteItemResquest.setRetryPolicy(Application.getDefaultRetryPolice());
                                        deleteItemResquest.setShouldCache(false);
                                        Application.getInstance().addToRequestQueue(deleteItemResquest, "deleteItemResquest");

                                    } catch (Exception e) {
                                        Log.e(ItemListAdapter.class.getName(), e.getMessage());
                                    }
                                } else {

                                    Log.e("Ewwww","Not Ok");
                                    items.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        EditText itemUmo;
        ImageView imgDelete;
        ImageView scanItem;
    }

    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */

    }

    private void showDialog(final int position) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity).create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_barcode_alert_dialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        final ImageView imageScanBarcode = (ImageView) dialogView.findViewById(R.id.scanBarcode);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button btnCancel = (Button) dialogView.findViewById(R.id.buttonCancel);

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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        imageScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startScan();
               /* final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
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
                materialBarcodeScanner.startScan();*/

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }
}
