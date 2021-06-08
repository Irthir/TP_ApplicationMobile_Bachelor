package com.example.lapersistancedesdonnees4bddmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edUsername;
    private EditText edPassword;
    private EditText edMail;

    private Button btRegister;
    private Button btLogin;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tester les sharedPreferences
        //Si on est connecté, faire un finish, puis un start activity vers profile activity.
        //Rajouter un return à la fin.
        if(SharedPreferenceManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,profileActivity.class));
            return;
        }


        edUsername = (EditText) findViewById(R.id.Username);
        edPassword = (EditText) findViewById(R.id.Password);
        edMail = (EditText) findViewById(R.id.Mail);
        btRegister = (Button) findViewById(R.id.Valider);
        btLogin = (Button) findViewById(R.id.Login);

        progressDialog = new ProgressDialog(this);

        btRegister.setOnClickListener(this::onClick);
        btLogin.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view)
    {
        if (view==btRegister)
        {
            registerUser();
        }
        if (view==btLogin)
        {
            login();
        }
    }

    private void registerUser()
    {
        //Récupérer les valeurs des 3 champs et ôter les espaces avec .trim()
        final String username = edUsername.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();
        final String email = edMail.getText().toString().trim();

        //Afficher la boîte de dialogue
        progressDialog.setMessage("Création utilisateur en cours...");
        progressDialog.show();

        /*Créer la requête POST d'insertion en base*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constante.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                //Gestion du JSon Object avec la réponse (cf. php)
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        //Ajouter la requête à RequestQueue Object
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        //Version 1 non optimisée.

        //Version 2 "SingletonRequestQueue"
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void login()
    {
        //Préparation de la navigation.
        Intent monIntent = new Intent(MainActivity.this,Login.class);
        //Navigation
        this.startActivity(monIntent);
        this.finish();
    }
}