package com.example.lazyloading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View stub = findViewById(R.id.Mon_viewstub);
        Button afficher = (Button) findViewById(R.id.button);
        afficher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                stub.setVisibility(View.VISIBLE);

                Button boutonToast = (Button) findViewById(R.id.Mon_viewstubvisible);
                boutonToast.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(getApplicationContext(), "Bravo vous avez appuyé sur le bouton magique qui était caché au début de l'application et maintenant, il disparait." , Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                stub.setVisibility(View.GONE);
                            }
                        }, 2000);

                    }
                });

            }
        });
    }
}