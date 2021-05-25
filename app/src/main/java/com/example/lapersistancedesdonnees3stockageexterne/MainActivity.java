package com.example.lapersistancedesdonnees3stockageexterne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    //Les objets à manipuler
    EditText et_Texte = null;
    Button bt_Sauvegarde = null;
    Button bt_Affichage = null;
    TextView tv_Affichage = null;
    CheckBox cb_FichierPartages=null;
    final static String s_NomFichier = "SauvegardeTest";
    File f_File=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File outFile = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DCIM);

        File sharedFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        et_Texte = (EditText) findViewById(R.id.texte);
        bt_Sauvegarde = (Button) findViewById(R.id.sauvegarde);
        bt_Affichage = (Button) findViewById(R.id.affichage);
        tv_Affichage = (TextView) findViewById(R.id.textesauvegarde);
        cb_FichierPartages = (CheckBox) findViewById(R.id.checkBox);
        f_File = outFile;
        cb_FichierPartages.setChecked(false);


        bt_Sauvegarde.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Sauvegarde(et_Texte.getText().toString(),f_File);
            }
        });

        bt_Affichage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Affichage(f_File);
            }
        });

        cb_FichierPartages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    f_File = sharedFile;
                }
                else
                {
                    f_File = outFile;
                }

            }
        });

        VerificationStockage();

    }

    private void VerificationStockage()
    //BUT : Vérifier l'état du stockage.
    {
        final String storageState = Environment.getExternalStorageState();
        String s_MessageEtat = "";
        switch (storageState)
        {
            case Environment.MEDIA_MOUNTED :
                s_MessageEtat = "Nous pouvons lire et écrire sur le stockage externe.";
                break;
            case Environment.MEDIA_MOUNTED_READ_ONLY :
                s_MessageEtat = "Nous pouvons seulement lire sur le stockage externe.";
                break;
            case Environment.MEDIA_REMOVED :
                s_MessageEtat = "Le stockage externe n'est pas disponible.";
                break;
            case  Environment.MEDIA_BAD_REMOVAL:
                s_MessageEtat = "Le stockage a été débranché avant d'être démonté";
                break;
            case  Environment.MEDIA_CHECKING:
                s_MessageEtat = "Le stockage est en cours de vérifiation";
                break;
            case  Environment.MEDIA_NOFS:
                s_MessageEtat = "Le stockage est disponible mais utilise un formatage non pris en charge";
                break;
            case  Environment.MEDIA_SHARED:
                s_MessageEtat = "Le stockage est utilisé en USB";
                break;
            case  Environment.MEDIA_UNMOUNTABLE:
                s_MessageEtat = "Le stockage est présent mais ne peut pas être monté";
                break;
            case  Environment.MEDIA_UNMOUNTED:
                s_MessageEtat = "Le stockage est présent mais pas monté.";
                break;
            default:
                s_MessageEtat = "Autre cas.";
                break;
        }
        Toast.makeText(getApplicationContext(),s_MessageEtat,Toast.LENGTH_LONG).show();
    }

    private void Sauvegarde (String s_Texte, File f_Fichier)
    //BUT : Sauvegarder le contenu d'une chaine dans un fichier interne.
    {
        FileOutputStream f_GestionnaireFichier; // L'écriture du fichier

        try
        {
            f_GestionnaireFichier = new FileOutputStream(new File(f_Fichier,s_NomFichier)); //openFileOutput(s_Fichier, Context.MODE_PRIVATE);
            Log.i("Sauvegarde",f_Fichier.getAbsolutePath());
            f_GestionnaireFichier.write(s_Texte.getBytes());
            f_GestionnaireFichier.close();
        } catch (IOException e) // FileNotFoundException Extends de IOException
        {
            e.printStackTrace();
        }
    }

    private void Affichage(File f_Fichier)
    //BUT : Afficher le texte sauvegarder dans un TextView
    {
        FileInputStream f_LecteurFichier; //La lecture des fichiers.
        try
        {
            f_LecteurFichier = new FileInputStream(new File(f_Fichier,s_NomFichier));
            Log.i("Sauvegarde",f_Fichier.getAbsolutePath());
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