package com.durgesh.tailorsdatasheet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.durgesh.tailorsdatasheet.model.CustomerModel;

public class CustomerDetailsActivity extends AppCompatActivity {

    TextView male_csName, male_csAddress, male_csContact,male_customer_gender;
    TextView pent_length, bottom, west, hip, fly, thigh, thighReady;
    TextView shirt_length, chest, stomach, shoulder, loosing, neck, sleves;
    CustomerModel model = new CustomerModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getAllWidgets();
        getDataFromPreviousActivity();
        setDataInWidgets();
    }

    private void getDataFromPreviousActivity() {
        int customerID = getIntent().getIntExtra("customer_id", 1);
        model = MainActivity.getInstance().searchPerson(customerID);
    }

    private void getAllWidgets() {
        //define all customer data
        male_customer_gender =  findViewById(R.id.tvPersonDetailGender);
        male_csName =  findViewById(R.id.tvPersonDetailName);
        male_csAddress =  findViewById(R.id.tvPersonDetailAddress);
        male_csContact = findViewById(R.id.tvPersonDetailContact);

        //define pent data
        pent_length = findViewById(R.id.pent_length);
        bottom = findViewById(R.id.bottom);
        west = findViewById(R.id.west);
        hip = findViewById(R.id.hip);
        fly = findViewById(R.id.fly);
        thigh = findViewById(R.id.thigh);
        thighReady = findViewById(R.id.thigh_ready);

        //define shirt data
        shirt_length = findViewById(R.id.shirt_length);
        chest = findViewById(R.id.chest);
        stomach = findViewById(R.id.stomach);
        loosing = findViewById(R.id.loosing);
        neck = findViewById(R.id.neck);
        shoulder = findViewById(R.id.shoulder);
        sleves = findViewById(R.id.sleves);

    }

    private void setDataInWidgets() {
        male_csName.setText(getString(R.string.person_name,model.getName()));
        male_csAddress.setText(getString(R.string.person_address,model.getAddress()));
        male_csContact.setText(getString(R.string.person_contact,model.getContact()));
        male_customer_gender.setText(getString(R.string.person_gender, model.getGender()));

        pent_length.setText(getString(R.string.pent_length,String.valueOf(model.getPent_length())));
        bottom.setText(getString(R.string.pent_bottom,String.valueOf(model.getBottom())));
        west.setText(getString(R.string.pent_west,String.valueOf(model.getWest())));
        hip.setText(getString(R.string.pent_hip,String.valueOf(model.getHip())));
        fly.setText(getString(R.string.pent_fly,String.valueOf(model.getFly())));
        thigh.setText(getString(R.string.pent_thigh,String.valueOf(model.getThigh())));
        thighReady.setText(getString(R.string.pent_thigh_ready,String.valueOf(model.getThighReady())));

        shirt_length.setText(getString(R.string.shirt_length,String.valueOf(model.getShirt_length())));
        chest.setText(getString(R.string.shirt_chest,String.valueOf(model.getChest())));
        stomach.setText(getString(R.string.shirt_stomach,String.valueOf(model.getStomach())));
        loosing.setText(getString(R.string.shirt_loosing,String.valueOf(model.getLoosing())));
        neck.setText(getString(R.string.shirt_neck,String.valueOf(model.getNeck())));
        shoulder.setText(getString(R.string.shirt_shoulder,String.valueOf(model.getShoulder())));
        sleves.setText(getString(R.string.shirt_sleves,String.valueOf(model.getSleves())));
    }

}
