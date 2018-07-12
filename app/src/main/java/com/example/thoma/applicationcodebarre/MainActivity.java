package com.example.thoma.applicationcodebarre;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thoma.applicationcodebarre.model.RoomBean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //composants graphique
    private Button validerSalle;
    private Spinner choixSalle;

    //data
    ArrayList<RoomBean> resultatRequete;


    //outils
    ArrayAdapter<RoomBean> dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validerSalle = findViewById(R.id.valider_salle_button);
        choixSalle = findViewById(R.id.liste_deroulante_rooms);

        validerSalle.setOnClickListener(this);
        resultatRequete = new ArrayList<>();
        resultatRequete.add(new RoomBean(-1, "Aucune salle chargée"));

        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resultatRequete);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choixSalle.setAdapter(dataAdapter);

        MonAsyncTask monAsyncTask = new MonAsyncTask();
        monAsyncTask.execute();


    }

    @Override
    public void onClick(View v) {

        if (v == validerSalle) {
            String valeurSelect = choixSalle.getSelectedItem().toString();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("SalleChoisie",valeurSelect);
            startActivity(intent);
        }
    }


    public class MonAsyncTask extends AsyncTask<Void, Void, ArrayList<RoomBean>> {


        Exception e;

        @Override
        protected ArrayList<RoomBean> doInBackground(Void... voids) {
            try {
                return WebServiceUtils.getRooms();
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RoomBean> roomBeans) {
            super.onPostExecute(roomBeans);

            if (e != null) {
                Toast.makeText(MainActivity.this, "Chargement des salles echoués : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                resultatRequete.clear();
                resultatRequete.addAll(roomBeans);
                dataAdapter.notifyDataSetChanged();

            }


        }
    }

}
