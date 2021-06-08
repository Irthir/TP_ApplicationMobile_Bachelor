package com.example.lapersistancedesdonnees4bddmysql;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager
{
    private static SharedPreferenceManager instance;
    private static Context ctx;

    private final String sharedPrefName="Session";
    private final String keyUserName="name";
    private final String keyUserEmail="mail";


    private SharedPreferenceManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    //À la connexion, on écrit dans les sharePreferences
    public boolean userLogin(String username, String email)
    {
        //Récupération de la sauvegarde précédente
        SharedPreferences MonSharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = MonSharedPreferences.edit();

        myEditor.putString(keyUserName,username);
        myEditor.putString(keyUserEmail,email);
        myEditor.apply();

        /*Finir la fonction*/
        return true;
    }

    public boolean isLoggedIn()
    {
        //Lire les sharedPreferences
        SharedPreferences MonSharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        if (MonSharedPreferences.getString(keyUserName,"")!="" && MonSharedPreferences.getString(keyUserEmail,"")!="")
        {
            return true;
        }

        return false;
    }

    public boolean logout()
    {
        SharedPreferences MonSharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = MonSharedPreferences.edit();

        myEditor.clear();
        myEditor.apply();

        return true;
    }

    //Utilisation par profile Activity
    public String getUserName()
    {
        SharedPreferences MonSharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return MonSharedPreferences.getString(keyUserName,"");
    }

    public String getUserEmail()
    {
        SharedPreferences MonSharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return MonSharedPreferences.getString(keyUserEmail,"");
    }
}
