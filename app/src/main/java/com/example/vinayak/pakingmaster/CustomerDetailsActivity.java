package com.example.vinayak.pakingmaster;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.example.vinayak.pakingmaster.adapters.ItemListAdapter;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.BarcodeScanResponse;
import com.example.vinayak.pakingmaster.pojo.CustomerDetails;
import com.example.vinayak.pakingmaster.pojo.GetCustomerListResponse;
import com.example.vinayak.pakingmaster.pojo.Item;
import com.example.vinayak.pakingmaster.pojo.ItemListData;
import com.example.vinayak.pakingmaster.pojo.SlipDetailsResponseData;
import com.example.vinayak.pakingmaster.pojo.SubmitOrderResponse;
import com.example.vinayak.pakingmaster.pojo.UserLoginResponseData;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.example.vinayak.pakingmaster.utils.Constant.slipNumberFromList;
import static com.example.vinayak.pakingmaster.utils.Constant.userName;

public class CustomerDetailsActivity extends BaseActivity {

    TextView slipNumber;
    EditText edTxtOrderDate;
    EditText edTxtOrderNumber;
    ListView listView;
    List<Item> items;

    ItemListAdapter adapter;
    Spinner customerNameSipnner;
    GetCustomerListResponse getCustomerListResponse;
    BarcodeScanResponse barcodeScanResponse;
    SlipDetailsResponseData slipDetailsResponseData;
    ArrayList<String> customer = new ArrayList<>();
    ArrayList<String> customerIds = new ArrayList<>();
    private DatePicker datePicker;
    private Calendar calendar = Calendar.getInstance();
    private int year, month, day;
    Date c = Calendar.getInstance().getTime();

    CustomerDetails customerDetails;
    String finalSlipNumber;
    String str_Barcode = "";
    String entryBy;
    /*String slipNumberFromList = "";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        slipNumberFromList = intent.getStringExtra("slipNumber");

        assignview();

        prepareCustomerList();

        if(!slipNumberFromList.equals(""))
        {
            slipNumber.setText(slipNumberFromList);
            getSupportActionBar().setTitle(slipNumberFromList);
            prepareSlipDetails(slipNumberFromList);

        } else {
            generateSlipNumber();
        }

        items = new ArrayList<>();

        setAdapter(items);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edTxtOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CustomerDetailsActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        entryBy = userName;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edTxtOrderDate.setText(sdf.format(calendar.getTime()));
    }

    private void generateSlipNumber() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        String tempDate = formattedDate.replace("-", "");
        int tempNumber = generateRandomIntIntRange(0001, 9999);
        finalSlipNumber = tempDate + "-" + tempNumber;
        slipNumber.setText("" + finalSlipNumber);
        getSupportActionBar().setTitle("" + finalSlipNumber);

    }


    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void setAdapter(List<Item> items) {
        adapter = new ItemListAdapter(CustomerDetailsActivity.this, items);
        listView.setAdapter(adapter);
    }

    private void assignview() {
        listView = (ListView) findViewById(R.id.itemList);
        customerNameSipnner = (Spinner) findViewById(R.id.customerSpinner);
        slipNumber = (TextView) findViewById(R.id.textViewSlipNumber);
        edTxtOrderDate = (EditText) findViewById(R.id.edTxtOrderDate);
        edTxtOrderNumber = (EditText) findViewById(R.id.edTxtOrderNumber);
        edTxtOrderDate.setFocusable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_add_new, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                if(slipNumberFromList.equals(""))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CustomerDetailsActivity.this);
                    alertDialogBuilder.setMessage("Are you sure,Do you wanted to cancel this slip ?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finish();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else{
                    finish();
                }
                return true;

            case R.id.menuAddItem:
                showDialog();
                return true;

            case R.id.menuSubmit:
                if (checkValidationForFields() == true) {
                    submitSlipDetails();
                    return true;
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void submitSlipDetails() {

        String str_slipNumber = slipNumber.getText().toString().trim();

        String str_customerName = customerNameSipnner.getSelectedItem().toString();
        String str_orderDate = edTxtOrderDate.getText().toString().trim();
        String str_orderNumber = edTxtOrderNumber.getText().toString().trim();


        int indexOfCustomer = customerNameSipnner.getSelectedItemPosition();
        String cutomerId = customerIds.get(indexOfCustomer).toString();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String submitedDate = df.format(c);
        String str_slipDate = df.format(c);
        String downloadedDate = df.format(c);
        String noOfBoxes = "" + items.size();

        customerDetails = new CustomerDetails(str_slipNumber, str_slipDate, str_orderNumber, str_orderDate, cutomerId, submitedDate, downloadedDate,
                noOfBoxes, entryBy, items);

        Gson gson2 = new GsonBuilder().create();
        String jsonString = gson2.toJson(customerDetails);

        try {
            GsonRequest<SubmitOrderResponse>
                    submitSlipDetailsRequest = new GsonRequest<>(Request.Method.POST, Constant.ADD_ORDER, jsonString, SubmitOrderResponse.class,
                    new Response.Listener<SubmitOrderResponse>() {
                        @Override
                        public void onResponse(@NonNull SubmitOrderResponse response) {
                            hideBusyProgress();
                            Log.e(CustomerDetailsActivity.class.getName(), "" + response.getSuccess());
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    showToast("Order submitted successfully.");
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(error.getMessage());
                    Log.e(CustomerDetailsActivity.class.getName(), error.getMessage());
                }
            });

            submitSlipDetailsRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            submitSlipDetailsRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(submitSlipDetailsRequest, "submitSlipDetailsRequest");
        } catch (Exception e) {
            Log.e(CustomerDetailsActivity.class.getName(), e.getMessage());
        }


    }

    private void showDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(CustomerDetailsActivity.this).create();
        LayoutInflater inflater = CustomerDetailsActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_new_item_alert_dialog, null);

        final EditText itemName = (EditText) dialogView.findViewById(R.id.edTxtItemName);
        final ImageView imgBarcodeScanner = (ImageView) dialogView.findViewById(R.id.imgBarcodeScanner);
        final EditText itemBarcodeValue = (EditText) dialogView.findViewById(R.id.edTxtItemBarcode);
        final EditText itemQuantity = (EditText) dialogView.findViewById(R.id.edTxtItemQuantity);
        final EditText itemBoxNo = (EditText) dialogView.findViewById(R.id.edTxtBoxNo);
        final EditText itemUmo = (EditText) dialogView.findViewById(R.id.edTxtUmo);
        final Button btnSubmit = (Button) dialogView.findViewById(R.id.buttonSubmitNewItem);
        final Button btnCancel = (Button) dialogView.findViewById(R.id.buttonCancelNewItem);


        imgBarcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                        .withActivity(CustomerDetailsActivity.this)
                        .withEnableAutoFocus(true)
                        .withBleepEnabled(true)
                        .withBackfacingCamera()
                        .withText("Scanning...")
                        .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                            @Override
                            public void onResult(Barcode barcode) {
                                Toast.makeText(CustomerDetailsActivity.this, barcode.rawValue, Toast.LENGTH_LONG).show();
                                str_Barcode = barcode.rawValue;
                                itemBarcodeValue.setText(str_Barcode);

                                //get item details by scanning barcode
                                try {
                                    showBusyProgress();
                                    JSONObject jo = new JSONObject();
                                    jo.put("barcode", str_Barcode);

                                    GsonRequest<BarcodeScanResponse> barcodeScanResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_ITEM_BY_BARCODE, jo.toString(), BarcodeScanResponse.class,
                                            new Response.Listener<BarcodeScanResponse>() {
                                                @Override
                                                public void onResponse(@NonNull BarcodeScanResponse response) {
                                                    hideBusyProgress();
                                                    //showToast(""+response.getSuccess().toString());
                                                    if (response.getError() != null) {
                                                        showToast(response.getError().getErrorMessage());
                                                    } else {
                                                        if (response.getSuccess() == 1) {
                                                            barcodeScanResponse = response;
                                                            if (barcodeScanResponse.getItemList() != null && barcodeScanResponse.getItemList().size() > 0) {

                                                                itemName.setText(barcodeScanResponse.getItemList().get(0).getName());
                                                                itemUmo.setText(barcodeScanResponse.getItemList().get(0).getUom());

                                                            } else {
                                                                showToast("No Item Found");
                                                            }
                                                        }
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            hideBusyProgress();
                                            showToast(error.getMessage());
                                            Log.e(CustomerDetailsActivity.class.getName(), error.getMessage());
                                        }
                                    });
                                    barcodeScanResquest.setRetryPolicy(Application.getDefaultRetryPolice());
                                    barcodeScanResquest.setShouldCache(false);
                                    Application.getInstance().addToRequestQueue(barcodeScanResquest, "barcodeScanResquest");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .build();
                materialBarcodeScanner.startScan();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_itemName = itemName.getText().toString().trim();
                String str_itemBarcode = itemBarcodeValue.getText().toString().trim();
                String str_itemQty = itemQuantity.getText().toString().trim();
                String str_itemBoxNo = itemBoxNo.getText().toString().trim();
                String str_slipNo = slipNumber.getText().toString().trim();
                String str_itemUmo = itemUmo.getText().toString().trim();

                Item item = new Item(str_itemName, str_itemBarcode, str_itemQty, str_itemBoxNo, str_slipNo, str_itemUmo);
                items.add(item);
                setAdapter(items);
                dialogBuilder.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });


        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }


    private void prepareCustomerList() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("", "");

            GsonRequest<GetCustomerListResponse> getCustomerListResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_CUSTOMER_LIST, "", GetCustomerListResponse.class,
                    new Response.Listener<GetCustomerListResponse>() {
                        @Override
                        public void onResponse(@NonNull GetCustomerListResponse response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getSuccess() == 1) {
                                getCustomerListResponse = response;
                                if (getCustomerListResponse.getCustomerList() != null && getCustomerListResponse.getCustomerList().size() > 0) {

                                    customer = new ArrayList<>(getCustomerListResponse.getCustomerList().size());
                                    customerIds = new ArrayList<>(getCustomerListResponse.getCustomerList().size());
                                    for (int i = 0; i < getCustomerListResponse.getCustomerList().size(); i++) {
                                        customer.add(getCustomerListResponse.getCustomerList().get(i).getCustname());
                                        customerIds.add(getCustomerListResponse.getCustomerList().get(i).getId());
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                            (CustomerDetailsActivity.this, android.R.layout.simple_spinner_item,
                                                    customer);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    customerNameSipnner.setAdapter(adapter);
                                } else {
                                    showToast("No Customer Found");
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(error.getMessage());
                }
            });
            getCustomerListResquest.setRetryPolicy(Application.getDefaultRetryPolice());
            getCustomerListResquest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getCustomerListResquest, "getCustomerListResquest");
        } catch (JSONException e) {
            hideBusyProgress();
            Log.e(PakingListActivity.class.getName(), e.getMessage());
        }
    }

    private void prepareSlipDetails(final String slipNumberFromList) {
        try{
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("Slipno", slipNumberFromList);

            GsonRequest<SlipDetailsResponseData> barcodeScanResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_SLIP_DETAILS, jo.toString(), SlipDetailsResponseData.class,
                    new Response.Listener<SlipDetailsResponseData>() {
                        @Override
                        public void onResponse(@NonNull SlipDetailsResponseData response) {
                            hideBusyProgress();

                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                                Log.e(CustomerDetailsActivity.class.getName(),response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    slipDetailsResponseData = response;
                                    if (slipDetailsResponseData.getItemList() != null && slipDetailsResponseData.getItemList().size() > 0) {
                                            slipNumber.setText(slipDetailsResponseData.getSlipno().toString());
                                            String tempCustId = slipDetailsResponseData.getCustid().toString();
                                            customerNameSipnner.setSelection(customerIds.indexOf(tempCustId));
                                            edTxtOrderDate.setText(slipDetailsResponseData.getOrderdate().toString());
                                            edTxtOrderNumber.setText(slipDetailsResponseData.getOrderno().toString());

                                            items = slipDetailsResponseData.getItemList();
                                            setAdapter(items);

                                    } else {
                                        showToast("No Item Found");
                                    }
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(error.getMessage());
                    Log.e(CustomerDetailsActivity.class.getName(), error.getMessage());
                }
            });
            barcodeScanResquest.setRetryPolicy(Application.getDefaultRetryPolice());
            barcodeScanResquest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(barcodeScanResquest, "barcodeScanResquest");

        }catch (Exception e){
            hideBusyProgress();
            Log.e(CustomerDetailsActivity.class.getName(),e.getMessage());
        }


    }


    private boolean checkValidationForFields() {

        String customerName = customerNameSipnner.getSelectedItem().toString();
        String orderDate = edTxtOrderDate.getText().toString().trim();
        String orderNumber = edTxtOrderNumber.getText().toString().trim();

        if (customerName.isEmpty() || customerName == null) {
            showToast("Please select cutomer name");
            return false;
        } else if (orderDate.isEmpty() || orderDate == null) {
            showToast("Please select order date");
            return false;
        } else if (orderNumber.isEmpty() || orderNumber == null) {
            showToast("Please enter order number");
            return false;
        } else if (items.size() < 0 || items.isEmpty()) {
            showToast("Please add at least one item");
            return false;
        } else
            return true;
    }
}
