package com.example.lapersistancedesdonnees4bddmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class Recherche extends AppCompatActivity {

    private RecyclerView monRecyclerView;
    private RecyclerView.Adapter monAdapter;
    private RecyclerView.LayoutManager monLayoutManager;
    private ProgressDialog progressDialog;

    private EditText edUsername;
    private EditText edEmail;
    private EditText edLocalite;
    private EditText edDDN;

    private Button btRecherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        //Tester les sharedPreferences
        //Si on n'est pas connecté, faire un finish, puis un start activity vers login activity.
        //Rajouter un return à la fin.
        if(!SharedPreferenceManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
            return;
        }

        //Récupération des vues qu'on utilise.
        edUsername = (EditText) findViewById(R.id.rechercheUsername);
        edEmail = (EditText) findViewById(R.id.rechercheEmail);
        edLocalite = (EditText) findViewById(R.id.rechercheLocalite);
        edDDN = (EditText) findViewById(R.id.rechercheDDN);
        btRecherche = (Button) findViewById(R.id.recherche);


        btRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recherche();
            }
        });

        progressDialog = new ProgressDialog(this);

        //Création de la liste
        /*ArrayList<User> listeUser;
        listeUser = new ArrayList<User>();*/
        //initialisation de la liste
        /*for (int i=0; i<listeUser.size(); i++)
        {
            Toast.makeText(getApplicationContext(),listeUser.get(i).getUsername().toString(),Toast.LENGTH_SHORT).show();
        }*/

        //Préparation du RecyclerView
        monRecyclerView = (RecyclerView) findViewById(R.id.main_RecyclerView);
        monRecyclerView.setHasFixedSize(true);//Taille fixe des éléments de la liste.

        monLayoutManager = new LinearLayoutManager(this);
        monRecyclerView.setLayoutManager(monLayoutManager);

        Recherche();
        /*monAdapter = new UserAdapter(listeUser);
        monRecyclerView.setAdapter(monAdapter);*/
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
                Toast.makeText(Recherche.this, "Les parametres !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout :
                SharedPreferenceManager.getInstance(this).logout();

                finish();
                Intent monIntent = new Intent(Recherche.this,MainActivity.class);
                startActivity(monIntent);
                break;
            default:
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    private void Recherche()
    {
        //Afficher la boîte de dialogue
        progressDialog.setMessage("Recherche...");
        progressDialog.show();

        String s_GetURL ="?";
        s_GetURL = s_GetURL+"username="+edUsername.getText().toString()+"&";
        s_GetURL = s_GetURL+"email="+edEmail.getText().toString()+"&";
        s_GetURL = s_GetURL+"localite="+edLocalite.getText().toString()+"&";
        s_GetURL = s_GetURL+"ddn="+edDDN.getText().toString();

        //Toast.makeText(Recherche.this, s_GetURL, Toast.LENGTH_SHORT).show();

        /*Créer la requête GET de recherche en base*/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constante.URL_RECHERCHE+s_GetURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                //Gestion du JSon Object avec la réponse (cf. php)
                try
                {
                    ArrayList<User> listeUser = new ArrayList<User>();

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Recherche.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray j_Array = jsonObject.getJSONArray("resultat");
                    Toast.makeText(Recherche.this, "Nombre Resultat = "+j_Array.length(), Toast.LENGTH_SHORT).show();
                    for (int i=0; i<j_Array.length(); i++)
                    {
                        JSONObject j_User = j_Array.getJSONObject(i);
                        User o_User = new User();
                        o_User.setUsername(j_User.getString("USERNAME"));
                        o_User.setEmail(j_User.getString("EMAIL"));
                        o_User.setLocalite(j_User.getString("LOCALITE"));
                        o_User.setDDN(j_User.getString("DDN"));

                        listeUser.add(o_User);
                    }

                    if (listeUser.size()==0)
                    {
                        User o_User = new User();
                        o_User.setUsername("Erreur");
                        o_User.setEmail("Utilisateur non trouve");
                        o_User.setLocalite("Utilisateur non trouve");
                        o_User.setDDN("Utilisateur non trouve");

                        listeUser.add(o_User);
                    }

                    RecyclerView.Adapter monAdapter = new UserAdapter(listeUser);
                    monRecyclerView.setAdapter(null);
                    monRecyclerView.setAdapter(monAdapter);

                    monRecyclerView.addOnItemTouchListener(new UserItemClickListener(getApplicationContext(),new UserItemClickListener.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view, int position)
                        {
                            if (listeUser.get(position).getUsername()!="erreur")
                            {
                                final String EXTRA_USER="user";

                                Toast.makeText(getApplicationContext(),listeUser.get(position).getUsername().toString(),Toast.LENGTH_SHORT).show();
                                Intent monIntent = new Intent(Recherche.this,Admin.class);
                                Parcelable parcel = listeUser.get(position);
                                monIntent.putExtra(EXTRA_USER, parcel);

                                //Navigation
                                startActivity(monIntent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Veuillez réitérer votre recherche.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }));
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
                        Toast.makeText(Recherche.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
}