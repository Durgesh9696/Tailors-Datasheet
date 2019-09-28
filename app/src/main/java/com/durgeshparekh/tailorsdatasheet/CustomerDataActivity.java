package com.durgeshparekh.tailorsdatasheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.durgeshparekh.tailorsdatasheet.model.Bean_Customer;

import java.util.ArrayList;

public class CustomerDataActivity extends AppCompatActivity {

    EditText name, contact, address;
    Spinner gender;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data);

        databaseHandler = new DatabaseHandler(this);

        fetchID();
    }

    private void fetchID() {
        name = findViewById(R.id.et_name);
        address = findViewById(R.id.et_address);
        contact = findViewById(R.id.et_contact);
        gender = findViewById(R.id.spn_gender);

        ArrayList<String> genderType = new ArrayList<>();
        genderType.add("Male");
        genderType.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.custom_pattern_spinner_size, genderType);
        gender.setAdapter(adapter);

    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(CustomerDataActivity.this, SizeDetailsActivity.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("address", address.getText().toString());
        intent.putExtra("gender", gender.getSelectedItem().toString());
        intent.putExtra("contact", contact.getText().toString());

        startActivity(intent);

    }

}
