package com.example.lapersistancedesdonnees;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialisation();
    }

    //Déclaration des objets manipulés
    TextView tvDate=null;
    EditText etNom=null;
    EditText etPrenom=null;
    EditText etEmail=null;
    EditText etPhone=null;

    Button btSauvegarde=null;

    TextView tvDateSauvegarde=null;
    TextView tvNomSauvegarde=null;
    TextView tvPrenomSauvegarde=null;
    TextView tvEmailSauvegarde=null;
    TextView tvPhoneSauvegarde=null;
    TextView tvNbSauvegarde=null;

    //Déclaration de l'objet SharedPreferences
    SharedPreferences MonSharedPreferences;

    //Déclaration des clés de sauvegarde
    private static final String MyPREFERENCES = "MyPrefs";
    private static final String s_Date="SauvegardeDate";
    private static final String s_Nom="SauvegardeNom";
    private static final String s_Prenom="SauvegardePrenom";
    private static final String s_Mail="SauvegardeMail";
    private static final String s_Phone="SauvegardePhone";
    private static final String s_Sauvegarde= "SauvegardeNombre";

    private void Initialisation()
    //BUT : Initialiser l'application au lancement.
    {
        //Gestion de la date.
        tvDate = (TextView) findViewById(R.id.Date);
        Date hui = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(hui);
        tvDate.setText(date);
        tvDate.setTextColor(Color.BLUE);
        tvDate.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

        //Liaison des objets manipulés.
        etNom = (EditText) findViewById(R.id.Nom);
        etPrenom = (EditText) findViewById(R.id.Prenom);
        etEmail = (EditText) findViewById(R.id.Email);
        etPhone = (EditText) findViewById(R.id.Tel);
        btSauvegarde = (Button) findViewById(R.id.sauvegarde);
        tvDateSauvegarde = (TextView) findViewById(R.id.DateSauvegarde);
        tvNomSauvegarde = (TextView) findViewById(R.id.NomSauvegarde);
        tvPrenomSauvegarde = (TextView) findViewById(R.id.PrenomSauvegarde);
        tvEmailSauvegarde = (TextView) findViewById(R.id.EmailSauvegarde);
        tvPhoneSauvegarde = (TextView) findViewById(R.id.TelSauvegarde);
        tvNbSauvegarde = (TextView) findViewById(R.id.NBSauvegarde);

        //Récupération de la sauvegarde précédente
        MonSharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Onclick Bouton sauvegarde
        btSauvegarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            //Sauvegarder les données
            {
                SharedPreferences.Editor myEditor = MonSharedPreferences.edit();
                myEditor.putString(s_Date,tvDate.getText().toString());
                myEditor.putString(s_Nom,etNom.getText().toString());
                myEditor.putString(s_Prenom,etPrenom.getText().toString());
                myEditor.putString(s_Mail,etEmail.getText().toString());
                myEditor.putString(s_Phone,etPhone.getText().toString());

                int nb_Sauvegarde = MonSharedPreferences.getInt(s_Sauvegarde,0);
                nb_Sauvegarde++;
                myEditor.putInt(s_Sauvegarde,nb_Sauvegarde);

                //myEditor.apply(); //Asynchrone
                myEditor.commit(); //Synchrone
                Toast.makeText(getApplicationContext(),"Sauvegarde des données effectuées", Toast.LENGTH_SHORT).show();
                AfficheSauvegarde();
            }
        });

        //Affichage de la sauvegarde si existe
        AfficheSauvegarde();
    }

    @SuppressLint("SetTextI18n")
    private void AfficheSauvegarde()
    //BUT : Afficher la sauvegarde si elle existe.
    {
        tvDateSauvegarde.setText(MonSharedPreferences.getString(s_Date,""));
        tvNomSauvegarde.setText(MonSharedPreferences.getString(s_Nom,""));
        tvPrenomSauvegarde.setText(MonSharedPreferences.getString(s_Prenom,""));
        tvEmailSauvegarde.setText(MonSharedPreferences.getString(s_Mail,""));
        tvPhoneSauvegarde.setText(MonSharedPreferences.getString(s_Phone,""));
        tvNbSauvegarde.setText(Integer.toString(MonSharedPreferences.getInt(s_Sauvegarde,0)));
    }
}