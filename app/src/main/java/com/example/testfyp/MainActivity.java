package com.example.testfyp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button buttonTurnOn,buttonGetTurnOn,buttonListDevices,buttonTurnOff;
    Button buttonSpeedLow,buttonSpeedHigh,buttonSpeedMedium,buttonTempAutomatic;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        buttonGetTurnOn=(Button)findViewById(R.id.buttonGetTurnOn);
        buttonListDevices=(Button)findViewById(R.id.buttonListDevices);
        buttonTurnOff=(Button)findViewById(R.id.buttonTurnOff);

        buttonTempAutomatic=(Button)findViewById(R.id.button_automatic_status_show);
        buttonSpeedLow=(Button)findViewById(R.id.button_speed_low);
        buttonSpeedHigh=(Button)findViewById(R.id.button_speed_medium);
        buttonSpeedMedium=(Button)findViewById(R.id.button_speed_high);


        BA= BluetoothAdapter.getDefaultAdapter();
        lv=(ListView) findViewById(R.id.listView);

        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);

    }

    public void on(View v){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v){
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }

    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    public void list(View v){
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();


        for(BluetoothDevice bt : pairedDevices) {
            list.add(bt.getName());
        }
        Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }


    public void automaticButton(View v){

    }
}