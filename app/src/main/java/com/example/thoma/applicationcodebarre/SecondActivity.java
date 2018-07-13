package com.example.thoma.applicationcodebarre;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewRoom, textViewEquipment;

    private IntentIntegrator qrScan;

    private String selectedRoom;

    private Button buttonScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewRoom = (TextView) findViewById(R.id.textViewRoom2);
        textViewEquipment = (TextView) findViewById(R.id.textViewEquipment2);

        Intent intent = getIntent();

        if (intent != null)

        {
            selectedRoom = intent.getStringExtra("SalleChoisie");
            textViewRoom.setText(selectedRoom);
        }


        qrScan = new IntentIntegrator(this);

        buttonScan.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject();
                    obj.put("room", selectedRoom);
                    obj.put("equipment", result.getContents());
                    textViewEquipment.setText(result.getContents());
                    MonAsyncTask monAsyncTask = new MonAsyncTask();
                    monAsyncTask.execute(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == buttonScan) {
            qrScan.initiateScan();
        }

    }

    public class MonAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                WebServiceUtils.addEquipment((JSONObject) objects[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}