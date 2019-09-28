package com.durgesh.tailorsdatasheet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.durgesh.tailorsdatasheet.model.CustomerModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

//    DatabaseHandler databaseHandler;

    private static int id = 1;
    private Realm myRealm;
    private ListView lvPersonNameList;
    EditText male_csName, male_csAddress, male_csContact,male_customer_gender;
    EditText pent_length, bottom, west, hip, fly, thigh, thighReady;
    EditText shirt_length, chest, stomach, shoulder, loosing, neck, sleves;
    FloatingActionButton fab;
    private static ArrayList<CustomerModel> customerModelArrayList = new ArrayList<>();
    private AlertDialog.Builder subDialog;
    private CustomerDetailsAdapter customerDetailsAdapter;
    private static MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*//setup realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("tailorDataSheet.realm").build();
        myRealm = Realm.getInstance(configuration);*/

        myRealm = Realm.getDefaultInstance();

        /*SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String ans = sp.getString("shop_name", null);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tailor's DataSheet");

        instance = this;

        fetchID();
        setPersonDetailsAdapter();
    }


    public static MainActivity getInstance() {
        return instance;
    }


    private void fetchID() {

        lvPersonNameList = (ListView) findViewById(R.id.lvPersonNameList);
        lvPersonNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,CustomerDetailsActivity.class);
                intent.putExtra("customer_id", customerModelArrayList.get(position).getCustomer_id());
                startActivity(intent);
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fab:
                        addOrUpdatePersonDetailsDialog(null,-1);
                        break;
                }

            }
        });
    }

    private void setPersonDetailsAdapter() {
        customerDetailsAdapter = new CustomerDetailsAdapter(MainActivity.this, customerModelArrayList);
        lvPersonNameList.setAdapter(customerDetailsAdapter);
        customerDetailsAdapter.notifyDataSetChanged();
    }

    public void addOrUpdatePersonDetailsDialog(final CustomerModel model, final int position) {
        //subdialog
        subDialog =  new AlertDialog.Builder(MainActivity.this)
                .setMessage("Please enter all the details!!!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dlg2, int which) {
                        dlg2.cancel();
                    }
                });

        //maindialog
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.prompt_dialog, null);
        AlertDialog.Builder mainDialog = new AlertDialog.Builder(MainActivity.this);
        mainDialog.setView(promptsView);

        //define all customer data
        male_customer_gender =  promptsView.findViewById(R.id.male_customer_gender);
        male_csName =  promptsView.findViewById(R.id.male_customer_name);
        male_csAddress =  promptsView.findViewById(R.id.male_customer_address);
        male_csContact = promptsView.findViewById(R.id.male_customer_contact);

        //define pent data
        pent_length = promptsView.findViewById(R.id.pent_length);
        bottom = promptsView.findViewById(R.id.pent_bottom);
        west = promptsView.findViewById(R.id.pent_west);
        hip = promptsView.findViewById(R.id.pent_hip);
        fly = promptsView.findViewById(R.id.pent_fly);
        thigh = promptsView.findViewById(R.id.pent_thigh);
        thighReady = promptsView.findViewById(R.id.pent_thigh_ready);

        //define shirt data
        shirt_length = promptsView.findViewById(R.id.shirt_length);
        chest = promptsView.findViewById(R.id.shirt_chest);
        stomach = promptsView.findViewById(R.id.shirt_stomach);
        loosing = promptsView.findViewById(R.id.shirt_loosing);
        neck = promptsView.findViewById(R.id.shirt_neck);
        shoulder = promptsView.findViewById(R.id.shirt_shoulder);
        sleves = promptsView.findViewById(R.id.shirt_sleves);

        if (model != null) {
            male_csName.setText(model.getName());
            male_csAddress.setText(model.getAddress());
            male_csContact.setText(model.getContact());
            male_customer_gender.setText(model.getGender());

            pent_length.setText(String.valueOf(model.getPent_length()));
            bottom.setText(String.valueOf(model.getBottom()));
            west.setText(String.valueOf(model.getWest()));
            hip.setText(String.valueOf(model.getHip()));
            fly.setText(String.valueOf(model.getFly()));
            thigh.setText(String.valueOf(model.getThigh()));
            thighReady.setText(String.valueOf(model.getThighReady()));

            shirt_length.setText(String.valueOf(model.getShirt_length()));
            chest.setText(String.valueOf(model.getChest()));
            stomach.setText(String.valueOf(model.getStomach()));
            loosing.setText(String.valueOf(model.getLoosing()));
            neck.setText(String.valueOf(model.getNeck()));
            shoulder.setText(String.valueOf(model.getShoulder()));
            sleves.setText(String.valueOf(model.getSleves()));
        }

        mainDialog.setCancelable(false)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog dialog = mainDialog.create();
        dialog.show();

        Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utility.isBlankField(male_csName) && !Utility.isBlankField(male_csAddress) &&
                        !Utility.isBlankField(male_csContact) && !Utility.isBlankField(male_customer_gender)) {

                    //fetch customer data
                    String male_cs_name = male_csName.getText().toString().trim();
                    String customer_gender = male_customer_gender.getText().toString().trim();
                    String male_cs_address = male_csAddress.getText().toString().trim();
                    String male_cs_contact = male_csContact.getText().toString().trim();

                    //fetch pent data
                    int pentLength = Integer.parseInt(pent_length.getText().toString());
                    int pentBottom = Integer.parseInt(bottom.getText().toString());
                    int pentWest = Integer.parseInt(west.getText().toString());
                    int pentHip = Integer.parseInt(hip.getText().toString());
                    int pentFly = Integer.parseInt(fly.getText().toString());
                    int pentThigh = Integer.parseInt(thigh.getText().toString());
                    int pentThighReady = Integer.parseInt(thighReady.getText().toString());

                    //fetch shirt data
                    int shirtLength = Integer.parseInt(shirt_length.getText().toString());
                    int shirtChest = Integer.parseInt(chest.getText().toString());
                    int shirtStomach = Integer.parseInt(stomach.getText().toString());
                    int shirtLoosing = Integer.parseInt(loosing.getText().toString());
                    int shirtNeck = Integer.parseInt(neck.getText().toString());
                    int shirtShoulder = Integer.parseInt(shoulder.getText().toString());
                    int shirtSleves = Integer.parseInt(sleves.getText().toString());

                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setName(male_cs_name);
                    customerModel.setAddress(male_cs_address);
                    customerModel.setContact(male_cs_contact);
                    customerModel.setGender(customer_gender);

                    customerModel.setPent_length(pentLength);
                    customerModel.setBottom(pentBottom);
                    customerModel.setWest(pentWest);
                    customerModel.setHip(pentHip);
                    customerModel.setFly(pentFly);
                    customerModel.setThigh(pentThigh);
                    customerModel.setThighReady(pentThighReady);

                    customerModel.setShirt_length(shirtLength);
                    customerModel.setChest(shirtChest);
                    customerModel.setStomach(shirtStomach);
                    customerModel.setLoosing(shirtLoosing);
                    customerModel.setNeck(shirtNeck);
                    customerModel.setShoulder(shirtShoulder);
                    customerModel.setSleves(shirtSleves);

                    if (model == null)
                        addDataToRealm(customerModel);
                    else
                        updatePersonDetails(customerModel, position, model.getCustomer_id());

                    dialog.cancel();
                } else {
                    subDialog.show();
                }
            }
        });
    }

    private void addDataToRealm(final CustomerModel model) {
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomerModel customerModel = realm.copyToRealm(model);
                customerModel.setCustomer_id(id);
                customerModel.setName(model.getName());
                customerModel.setAddress(model.getAddress());
                customerModel.setContact(model.getContact());
                customerModel.setGender(model.getGender());

                //pent details
                customerModel.setPent_length(model.getPent_length());
                customerModel.setBottom(model.getBottom());
                customerModel.setWest(model.getWest());
                customerModel.setHip(model.getHip());
                customerModel.setFly(model.getFly());
                customerModel.setThigh(model.getThigh());
                customerModel.setThighReady(model.getThighReady());

                //shirt details
                customerModel.setShirt_length(model.getShirt_length());
                customerModel.setChest(model.getChest());
                customerModel.setStomach(model.getStomach());
                customerModel.setLoosing(model.getLoosing());
                customerModel.setNeck(model.getNeck());
                customerModel.setShoulder(model.getShoulder());
                customerModel.setSleves(model.getSleves());

                customerModelArrayList.add(customerModel);
                customerDetailsAdapter.notifyDataSetChanged();
                id++;
                Log.e("id","----->>"+id);
            }
        });
    }

    public void deletePerson(int personId, int position) {
        RealmResults<CustomerModel> results = myRealm.where(CustomerModel.class)
                .equalTo("customer_id", personId).findAll();

        myRealm.beginTransaction();
//        results.remove(0);
        results.deleteFromRealm(0);
        myRealm.commitTransaction();

        customerModelArrayList.remove(position);
        customerDetailsAdapter.notifyDataSetChanged();
    }

    public CustomerModel searchPerson(int customer_id) {
        RealmResults<CustomerModel> results = myRealm.where(CustomerModel.class)
                .equalTo("customer_id", customer_id).findAll();

        myRealm.beginTransaction();
        myRealm.commitTransaction();

        return results.get(0);
    }

    public void updatePersonDetails(CustomerModel model,int position,int personID) {
        CustomerModel customerModel = myRealm.where(CustomerModel.class)
                .equalTo("customer_id", personID).findFirst();
        myRealm.beginTransaction();
        customerModel.setName(model.getName());
        customerModel.setAddress(model.getAddress());
        customerModel.setContact(model.getContact());
        customerModel.setGender(model.getGender());

        //pent details
        customerModel.setPent_length(model.getPent_length());
        customerModel.setBottom(model.getBottom());
        customerModel.setWest(model.getWest());
        customerModel.setHip(model.getHip());
        customerModel.setFly(model.getFly());
        customerModel.setThigh(model.getThigh());
        customerModel.setThighReady(model.getThighReady());

        //shirt details
        customerModel.setShirt_length(model.getShirt_length());
        customerModel.setChest(model.getChest());
        customerModel.setStomach(model.getStomach());
        customerModel.setLoosing(model.getLoosing());
        customerModel.setNeck(model.getNeck());
        customerModel.setShoulder(model.getShoulder());
        customerModel.setSleves(model.getSleves());

        myRealm.commitTransaction();

        customerModelArrayList.set(position, customerModel);
        customerDetailsAdapter.notifyDataSetChanged();
    }

    private void getAllUsers() {
        RealmResults<CustomerModel> results = myRealm.where(CustomerModel.class).findAll();

        myRealm.beginTransaction();

        for (int i = 0; i < results.size(); i++) {
            customerModelArrayList.add(results.get(i));
        }

        if(results.size()>0)
            id = myRealm.where(CustomerModel.class).max("customer_id").intValue() + 1;
        myRealm.commitTransaction();
        customerDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        customerModelArrayList.clear();
        myRealm.close();
    }
}
