package com.durgeshparekh.tailorsdatasheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.durgeshparekh.tailorsdatasheet.model.Bean_Customer;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Pent;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Shirt;

public class SizeDetailsActivity extends AppCompatActivity {
    String name, contact, address, gender;
    DatabaseHandler databaseHandler;
    private static final String TAG = "SizeDetailsActivity";
    // pent data
    String pentLength, bottom, west, hip, fly, thigh, thighReady;
    EditText etPentLength, etBottom, etWest, etHip, etFly, etThigh, etThighReady;

    //shirt data
    String shirtLength, chest, stomach, loosing, neck, shoulder, sleeves;
    EditText etShirtLength, etChest, etStomach, etLoosing, etNeck, etShoulder, etSleeves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_details);

        databaseHandler = new DatabaseHandler(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        contact = intent.getStringExtra("contact");
        address = intent.getStringExtra("address");
        gender = intent.getStringExtra("gender");

        fetchID();

    }

    private void fetchID() {
        //pent details
        etPentLength = findViewById(R.id.et_pent_length);
        etBottom = findViewById(R.id.et_bottom);
        etWest = findViewById(R.id.et_west);
        etHip = findViewById(R.id.et_hip);
        etFly = findViewById(R.id.et_fly);
        etThigh = findViewById(R.id.et_thigh);
        etThighReady = findViewById(R.id.et_thigh_ready);


        //shirt Details
        etShirtLength = findViewById(R.id.et_shirt_length);
        etChest = findViewById(R.id.et_chest);
        etStomach = findViewById(R.id.et_stomach);
        etLoosing = findViewById(R.id.et_loosing);
        etNeck = findViewById(R.id.et_neck);
        etShoulder = findViewById(R.id.et_shoulder);
        etSleeves = findViewById(R.id.et_sleeves);

    }

    public void onSaveClicked(View view) {
        Bean_Customer beanCustomer = new Bean_Customer();
        beanCustomer.setName(name);
        beanCustomer.setAddress(address);
        beanCustomer.setContact(contact);
        beanCustomer.setGender(gender);

        databaseHandler.addCustomer(beanCustomer);
        int customerID = databaseHandler.getCustomerID(contact);

        pentLength = etPentLength.getText().toString();
        bottom = etBottom.getText().toString();
        west = etWest.getText().toString();
        hip = etHip.getText().toString();
        fly = etFly.getText().toString();
        thigh = etThigh.getText().toString();
        thighReady = etThighReady.getText().toString();

        shirtLength = etShirtLength.getText().toString();
        chest = etChest.getText().toString();
        stomach = etStomach.getText().toString();
        loosing = etLoosing.getText().toString();
        neck = etNeck.getText().toString();
        shoulder = etShoulder.getText().toString();
        sleeves = etSleeves.getText().toString();

        Bean_Pent beanPent = new Bean_Pent();
        beanPent.setCustomerID(customerID);
        beanPent.setPent_length(pentLength);
        beanPent.setBottom(bottom);
        beanPent.setWest(west);
        beanPent.setHip(hip);
        beanPent.setFly(fly);
        beanPent.setThigh(thigh);
        beanPent.setThighReady(thighReady);

        Bean_Shirt beanShirt = new Bean_Shirt();
        beanShirt.setCustomerID(customerID);
        beanShirt.setShirt_length(shirtLength);
        beanShirt.setChest(chest);
        beanShirt.setStomach(stomach);
        beanShirt.setLoosing(loosing);
        beanShirt.setNeck(neck);
        beanShirt.setShoulder(shoulder);
        beanShirt.setSleves(sleeves);

        databaseHandler.addSizeData(beanPent, beanShirt);
        Toast.makeText(this, "Data Saved Successfully !", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void onPreviousClicked(View view) {
        onBackPressed();
    }
}
