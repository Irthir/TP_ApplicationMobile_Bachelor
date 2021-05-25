package com.example.lapersistancedesdonnees2stockageinterne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //La gestion des fichiers
    public static final String s_Fichier = "StockageInterne"; // Le nom du fichier
    //public String s_Texte = "Ceci est un texte à sauvegarder"; // La chaine à sauvegarder.
    FileOutputStream f_GestionnaireFichier; //La gestion des fichiers.
    FileInputStream f_LecteurFichier; //La lecture des fichiers.
    File f_Fichier=null;

    //Les objets à manipuler
    EditText et_Texte = null;
    Button bt_Sauvegarde = null;
    Button bt_Affichage = null;
    TextView tv_Affichage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_Texte = (EditText) findViewById(R.id.texte);
        bt_Sauvegarde = (Button) findViewById(R.id.sauvegarde);
        bt_Affichage = (Button) findViewById(R.id.affichage);
        tv_Affichage = (TextView) findViewById(R.id.textesauvegarde);

        //Pour utiliser, le premier fichier du cache.
        //Décommenter pour utiliser le cache.
        /*File dir = new File(getCacheDir().getAbsolutePath());
        if (dir.exists()) {
            for (File f : dir.listFiles())
            {
                f_Fichier = f;
                break;
            }
        }*/

        bt_Sauvegarde.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Sauvegarde(et_Texte.getText().toString());
            }
        });

        bt_Affichage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Affichage();
            }
        });

    }

    private void Sauvegarde (String s_Texte)
    //BUT : Sauvegarder le contenu d'une chaine dans un fichier interne.
    {
        try
        {
            if (f_Fichier!=null)
            {
                f_GestionnaireFichier = new FileOutputStream(new File(f_Fichier,s_Fichier));
            }
            else {
                f_GestionnaireFichier = openFileOutput(s_Fichier, Context.MODE_PRIVATE);
            }
            f_GestionnaireFichier.write(s_Texte.getBytes());
            f_GestionnaireFichier.close();
        } catch (IOException e) // FileNotFoundException Extends de IOException
        {
            e.printStackTrace();
        }
    }

    private void Affichage()
    //BUT : Afficher le texte sauvegarder dans un TextView
    {
        try
        {
            if (f_Fichier!=null)
            {
                f_LecteurFichier = new FileInputStream(new File(f_Fichier,s_Fichier));
            }
            else {
                f_LecteurFichier = openFileInput(s_Fichier);
            }
            //Taille de 1024/2048/4096 bytes peut bloquer l'affichage du texte.
            byte[] buffer = new byte[4096]; //Se rempli à chaque lecture.
            StringBuilder contenu = new StringBuilder(); //Contenu du fichier.
            while ((f_LecteurFichier.read(buffer))!=-1)
            {
                contenu.append(new String (buffer));
            } //read retourne -1 quand lecture finie.

            tv_Affichage.setText(contenu.toString());
            //Toast.makeText(getApplicationContext(),contenu.toString(),Toast.LENGTH_LONG).show();
        }
        catch (IOException e) // FileNotFoundException Extends de IOException
        {
            e.printStackTrace();
        }
    }
}