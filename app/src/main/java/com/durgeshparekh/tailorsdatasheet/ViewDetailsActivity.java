package com.durgeshparekh.tailorsdatasheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.durgeshparekh.tailorsdatasheet.model.Bean_Customer;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Pent;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Shirt;

public class ViewDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ViewDetailsActivity";
    String customerName, customerGender, customerContact, customerAddress;
    int customerId;
    Bean_Pent beanPent;
    Bean_Shirt beanShirt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        Intent intent = getIntent();
        customerName = intent.getStringExtra("customer_name");
        customerId = intent.getIntExtra("customer_id", 0);
        customerContact = intent.getStringExtra("customer_contact");
        customerAddress = intent.getStringExtra("customer_address");
        customerGender = intent.getStringExtra("customer_gender");

        beanPent = databaseHandler.getPentDetailById(customerId);
        beanShirt = databaseHandler.getShirtDetailById(customerId);

        fetchID();

    }

    private void fetchID() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(customerName);

        //customer Details
        EditText name = findViewById(R.id.et_name);
        EditText address = findViewById(R.id.et_address);
        EditText contact = findViewById(R.id.et_contact);
        EditText gender = findViewById(R.id.et_gender);

        name.setText(customerName);
        address.setText(customerAddress);
        contact.setText(customerContact);
        gender.setText(customerGender);

        //pent Details
        EditText pentLength = findViewById(R.id.et_pent_length);
        EditText bottom = findViewById(R.id.et_bottom);
        EditText west = findViewById(R.id.et_west);
        EditText hip = findViewById(R.id.et_hip);
        EditText fly = findViewById(R.id.et_fly);
        EditText thigh = findViewById(R.id.et_thigh);
        EditText thighReady = findViewById(R.id.et_thigh_ready);

        pentLength.setText(beanPent.getPent_length());
        bottom.setText(beanPent.getBottom());
        west.setText(beanPent.getWest());
        hip.setText(beanPent.getHip());
        fly.setText(beanPent.getFly());
        thigh.setText(beanPent.getThigh());
        thighReady.setText(beanPent.getThighReady());

        //shirt details
        EditText shirtLength = findViewById(R.id.et_shirt_length);
        EditText chest = findViewById(R.id.et_chest);
        EditText stomach = findViewById(R.id.et_stomach);
        EditText loosing = findViewById(R.id.et_loosing);
        EditText neck = findViewById(R.id.et_neck);
        EditText shoulder = findViewById(R.id.et_shoulder);
        EditText sleeves = findViewById(R.id.et_sleeves);

        shirtLength.setText(beanShirt.getShirt_length());
        chest.setText(beanShirt.getChest());
        stomach.setText(beanShirt.getStomach());
        loosing.setText(beanShirt.getLoosing());
        neck.setText(beanShirt.getNeck());
        shoulder.setText(beanShirt.getShoulder());
        sleeves.setText(beanShirt.getSleves());

    }
}
