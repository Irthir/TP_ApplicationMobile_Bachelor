package com.example.lapersistancedesdonnees4bddmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btGestion;
    private TextView tvAffiche;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Tester les sharedPreferences
        //Si on n'est pas connecté, faire un finish, puis un start activity vers login activity.
        //Rajouter un return à la fin.
        if(!SharedPreferenceManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
            return;
        }

        tvAffiche = (TextView) findViewById(R.id.affiche);
        btGestion = (Button) findViewById(R.id.accueil);


        btGestion.setOnClickListener(this::onClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        afficheUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings :
                Toast.makeText(profileActivity.this, "Les parametres !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout :
                SharedPreferenceManager.getInstance(this).logout();

                finish();
                Intent monIntent = new Intent(profileActivity.this,MainActivity.class);
                startActivity(monIntent);
                break;
            default:
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }


    @Override
    public void onClick(View view)
    {
        if (view==btGestion)
        {
            Menu();
        }
    }

    private void afficheUser()
    {
        String s_UserName = SharedPreferenceManager.getInstance(this).getUserName();
        String s_Email = SharedPreferenceManager.getInstance(this).getUserEmail();

        String s_Affichage = "Nom d'Utilisateur : "+s_UserName+"\nEmail : "+s_Email;
        tvAffiche.setText(s_Affichage);
    }

    private void Menu()
    {
        //Préparation de la navigation.
        Intent monIntent = new Intent(profileActivity.this,Recherche.class);
        //Navigation
        this.finish();
        this.startActivity(monIntent);
    }
}