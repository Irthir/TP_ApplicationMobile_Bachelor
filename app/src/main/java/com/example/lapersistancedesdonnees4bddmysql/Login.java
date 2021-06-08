package com.example.lapersistancedesdonnees4bddmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edUsername;
    private EditText edPassword;

    private Button btLogin;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        btLogin = (Button) findViewById(R.id.connexion);

        progressDialog = new ProgressDialog(this);

        btLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        if (view==btLogin)
        {
            loginUser();
        }
    }


    private void loginUser()
    {
        //Récupérer les valeurs des 2 champs et ôter les espaces avec .trim()
        final String username = edUsername.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();

        final String urlparam = "?username="+username+"&password="+password;

        //Afficher la boîte de dialogue
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.show();

        /*Créer la requête GET de lecture en base*/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constante.URL_LOGIN+urlparam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                //Gestion du JSon Object avec la réponse (cf. php)
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Login.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (!jsonObject.getBoolean("error"))
                    {
                        //Stocker les SharedPreferences.
                        saveUser(username); //Sauvegarde de l'utilisateur.
                        Connexion(); //Redirection vers profileActivity.
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Échec de Connexion.", Toast.LENGTH_SHORT).show();
                    }
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
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
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

    private void saveUser(String s_User)
    {
        //Récupérer les valeurs des 2 champs et ôter les espaces avec .trim()
        final String urlparam = "?username="+s_User;

        //Afficher la boîte de dialogue
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.show();

        /*Créer la requête GET de lecture en base*/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constante.URL_USER+urlparam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                //Gestion du JSon Object avec la réponse (cf. php)
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Login.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    /*String s_Affichage = "Nom d'Utilisateur : "+jsonObject.getString("username")+"\nEmail : "+jsonObject.getString("email");
                    tvAffiche.setText(s_Affichage);*/

                    SharedPreferenceManager.getInstance(Login.this).userLogin(jsonObject.getString("username"),jsonObject.getString("email"));
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
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username", s_User);
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

    private void Connexion()
    {
        //Préparation de la navigation.
        Intent monIntent = new Intent(Login.this,profileActivity.class);

        //Navigation
        Login.this.startActivity(monIntent);
        Login.this.finish();
    }
}