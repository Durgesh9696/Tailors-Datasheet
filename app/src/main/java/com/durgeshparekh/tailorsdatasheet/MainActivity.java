package com.durgeshparekh.tailorsdatasheet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.durgeshparekh.tailorsdatasheet.adapter.CustomerListAdapter;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Customer;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Pent;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Shirt;
import com.durgeshparekh.tailorsdatasheet.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomerListAdapter.CustomerListEditCallback {

    private static final String TAG = "MainActivity";
    CustomerListAdapter customerListAdapter;
    DatabaseHandler databaseHandler;
    List<Bean_Customer> customerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new DatabaseHandler(this);
        fetchID();
    }

    private void fetchID() {
        TextView shopName = findViewById(R.id.shop_name);
        String name = SharedPrefManager.getInstance(this).getShopName();
        shopName.setText(name);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        customerList = databaseHandler.getAllCustomerList();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        customerListAdapter = new CustomerListAdapter(customerList, this);
        customerListAdapter.setCallBack(this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(customerListAdapter);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

    }

    public void onFabClicked(View view) {
        Intent i = new Intent(this, CustomerDataActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void editCustomerData(final int position, View view) {
        //Show Popup to Rename or Delete or Edit
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_group_edit,popupMenu.getMenu());
        popupMenu.setGravity(Gravity.CENTER);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.edit_customer_data:
                        MainActivity.this.updateCustomerData(position);
                        break;

                    case R.id.edit_pent_data:
                        MainActivity.this.updatePentData(position);
                        break;

                    case R.id.edit_shirt_data:
                        MainActivity.this.updateShirtData(position);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void updateShirtData(int position) {
        final int customerId = customerList.get(position).getCustomer_id();

        Bean_Shirt beanShirt = databaseHandler.getShirtDetailById(customerId);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View dialogView = getLayoutInflater().inflate(R.layout.change_shirt_data_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText shirtLength = dialogView.findViewById(R.id.et_shirt_length);
        final EditText chest = dialogView.findViewById(R.id.et_chest);
        final EditText stomach = dialogView.findViewById(R.id.et_stomach);
        final EditText loosing = dialogView.findViewById(R.id.et_loosing);
        final EditText neck = dialogView.findViewById(R.id.et_neck);
        final EditText shoulder = dialogView.findViewById(R.id.et_shoulder);
        final EditText sleeves = dialogView.findViewById(R.id.et_sleeves);

        shirtLength.setText(beanShirt.getShirt_length());
        chest.setText(beanShirt.getChest());
        stomach.setText(beanShirt.getStomach());
        loosing.setText(beanShirt.getLoosing());
        neck.setText(beanShirt.getNeck());
        shoulder.setText(beanShirt.getShoulder());
        sleeves.setText(beanShirt.getSleves());

        dialogBuilder.setTitle("Change Shirt Details");
        dialogBuilder.setPositiveButton("Save", null);
        dialogBuilder.setNegativeButton("Cancel", null);

        final AlertDialog ab = dialogBuilder.create();

        ab.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn_positive = ab.getButton(AlertDialog.BUTTON_POSITIVE);

                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bean_Shirt bean_shirt = new Bean_Shirt();
                        bean_shirt.setShirt_length(shirtLength.getText().toString());
                        bean_shirt.setChest(chest.getText().toString());
                        bean_shirt.setStomach(stomach.getText().toString());
                        bean_shirt.setLoosing(loosing.getText().toString());
                        bean_shirt.setNeck(neck.getText().toString());
                        bean_shirt.setShoulder(shoulder.getText().toString());
                        bean_shirt.setSleves(sleeves.getText().toString());
                        bean_shirt.setCustomerID(customerId);

                        databaseHandler.updateShirtDetails(bean_shirt);
                        ab.dismiss();
                    }
                });
            }
        });
        ab.show();
    }

    private void updatePentData(final int position) {
        final int customerId = customerList.get(position).getCustomer_id();

        Bean_Pent beanPent = databaseHandler.getPentDetailById(customerId);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View dialogView = getLayoutInflater().inflate(R.layout.change_pent_data_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText pentLength = dialogView.findViewById(R.id.et_pent_length);
        final EditText bottom = dialogView.findViewById(R.id.et_bottom);
        final EditText west = dialogView.findViewById(R.id.et_west);
        final EditText hip = dialogView.findViewById(R.id.et_hip);
        final EditText fly = dialogView.findViewById(R.id.et_fly);
        final EditText thigh = dialogView.findViewById(R.id.et_thigh);
        final EditText thighReady = dialogView.findViewById(R.id.et_thigh_ready);

        pentLength.setText(beanPent.getPent_length());
        bottom.setText(beanPent.getBottom());
        west.setText(beanPent.getWest());
        hip.setText(beanPent.getHip());
        fly.setText(beanPent.getFly());
        thigh.setText(beanPent.getThigh());
        thighReady.setText(beanPent.getThighReady());

        dialogBuilder.setTitle("Change Pent Details");
        dialogBuilder.setPositiveButton("Save", null);
        dialogBuilder.setNegativeButton("Cancel", null);

        final AlertDialog ab = dialogBuilder.create();

        ab.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn_positive = ab.getButton(AlertDialog.BUTTON_POSITIVE);

                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bean_Pent bean_pent = new Bean_Pent();
                        bean_pent.setPent_length(pentLength.getText().toString());
                        bean_pent.setBottom(bottom.getText().toString());
                        bean_pent.setWest(west.getText().toString());
                        bean_pent.setHip(hip.getText().toString());
                        bean_pent.setFly(fly.getText().toString());
                        bean_pent.setThigh(thigh.getText().toString());
                        bean_pent.setThighReady(thighReady.getText().toString());
                        bean_pent.setCustomerID(customerId);

                        databaseHandler.updatePentDetails(bean_pent);
                        ab.dismiss();
                    }
                });
            }
        });
        ab.show();

    }

    private void updateCustomerData(final int position) {
        final Bean_Customer beanCustomer = customerList.get(position);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View dialogView = getLayoutInflater().inflate(R.layout.change_customer_data_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText name = dialogView.findViewById(R.id.et_name);
        final Spinner gender = dialogView.findViewById(R.id.spn_gender);
        final EditText contact = dialogView.findViewById(R.id.et_contact);
        final EditText address = dialogView.findViewById(R.id.et_address);

        ArrayList<String> genderType = new ArrayList<>();
        genderType.add("Male");
        genderType.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.custom_pattern_spinner_size, genderType);
        gender.setAdapter(adapter);

        name.setText(beanCustomer.getName());
        if (beanCustomer.getGender().equals("Male")){
            gender.setSelection(0);

        }else {
            gender.setSelection(1);
        }
        contact.setText(beanCustomer.getContact());
        address.setText(beanCustomer.getAddress());

        dialogBuilder.setTitle("Change Customer Details");
        dialogBuilder.setPositiveButton("Save", null);
        dialogBuilder.setNegativeButton("Cancel", null);

        final AlertDialog ab = dialogBuilder.create();

        ab.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn_positive = ab.getButton(AlertDialog.BUTTON_POSITIVE);

                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bean_Customer bean_customer = new Bean_Customer();
                        bean_customer.setName(name.getText().toString());
                        bean_customer.setContact(contact.getText().toString());
                        bean_customer.setGender(gender.getSelectedItem().toString());
                        bean_customer.setAddress(address.getText().toString());
                        bean_customer.setCustomer_id(customerList.get(position).getCustomer_id());

                        databaseHandler.updateCustomerData(bean_customer);
                        customerList.set(position, bean_customer);
                        customerListAdapter.notifyDataSetChanged();
                        ab.dismiss();
                    }
                });
            }
        });
        ab.show();

    }

    @Override
    public void deleteData(int position) {

        final Bean_Customer beanCustomer = customerList.get(position);
        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(MainActivity.this);
        confirmDelete.setTitle("Confirm to delete");
        confirmDelete.setMessage("Are you sure to delete ?");
        confirmDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                databaseHandler.deleteData(String.valueOf(beanCustomer.getCustomer_id()));
                customerList.remove(beanCustomer);
                customerListAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        confirmDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        confirmDelete.create().show();

    }
}
