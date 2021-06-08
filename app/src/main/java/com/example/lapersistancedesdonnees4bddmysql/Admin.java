package com.example.lapersistancedesdonnees4bddmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin extends AppCompatActivity {

    private User utilisateur;
    private ProgressDialog progressDialog;

    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvLocalite;
    private TextView tvDDN;

    private EditText edUsername;
    private EditText edEmail;
    private EditText edLocalite;
    private EditText edDDN;

    private Button btSuppr;
    private Button btUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Tester les sharedPreferences
        //Si on n'est pas connecté, faire un finish, puis un start activity vers login activity.
        //Rajouter un return à la fin.
        if(!SharedPreferenceManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
            return;
        }

        final String EXTRA_USER="user";

        progressDialog = new ProgressDialog(this);

        Intent monIntent = getIntent();
        utilisateur = new User();
        utilisateur = monIntent.getParcelableExtra(EXTRA_USER);

        Affiche();

        btSuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Delete();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Update();
            }
        });
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
                Toast.makeText(Admin.this, "Les parametres !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout :
                SharedPreferenceManager.getInstance(this).logout();

                finish();
                Intent monIntent = new Intent(Admin.this,MainActivity.class);
                startActivity(monIntent);
                break;
            default:
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    private void Affiche()
    {
        tvUsername.setText(utilisateur.getUsername().toString());
        tvEmail.setText(utilisateur.getEmail().toString());
        tvLocalite.setText(utilisateur.getLocalite().toString());
        tvDDN.setText(utilisateur.getDDN().toString());
    }

    private void Update()
    {
        //Afficher la boîte de dialogue
        progressDialog.setMessage("Mise à jour...");
        progressDialog.show();

        //Appeler Update
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constante.URL_UPDATE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                //Gestion du JSon Object avec la réponse (cf. php)
                try
                {
                    ArrayList<User> listeUser = new ArrayList<User>();

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Admin.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    //Mettre à jour utilisateur
                    if(!jsonObject.isNull("newemail"))
                    {
                        utilisateur.setEmail(jsonObject.getString("newemail"));
                    }
                    if(!jsonObject.isNull("newlocalite"))
                    {
                        utilisateur.setLocalite(jsonObject.getString("newlocalite"));
                    }
                    if(!jsonObject.isNull("newddn"))
                    {
                        utilisateur.setDDN(jsonObject.getString("newddn"));
                    }
                    if(!jsonObject.isNull("newusername"))
                    {
                        utilisateur.setUsername(jsonObject.getString("newusername"));
                    }

                    //Mettre à jour l'affichage
                    Affiche();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.hide();
                        Toast.makeText(Admin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("usernamebase",utilisateur.getUsername());
                params.put("email", edEmail.getText().toString());
                params.put("localite",edLocalite.getText().toString());
                params.put("ddn",edDDN.getText().toString());
                params.put("username",edUsername.getText().toString());
                return params;
            }
        };

        //Version 2 "SingletonRequestQueue"
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void Delete()
    {
        progressDialog.setMessage("Suppression...");
        progressDialog.show();

        //Appeler le delete
        /*Créer la requête POST d'insertion en base*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constante.URL_DELETE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                //Gestion du JSon Object avec la réponse (cf. php)
                try
                {
                    ArrayList<User> listeUser = new ArrayList<User>();

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Admin.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },

        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.hide();
                Toast.makeText(Admin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        //Ajouter la requête à RequestQueue Object
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        //Version 1 non optimisée.

        //Version 2 "SingletonRequestQueue"
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        //Si le delete a eu lieu rediriger

        finish();
        startActivity(new Intent(Admin.this,profileActivity.class));
    }
}