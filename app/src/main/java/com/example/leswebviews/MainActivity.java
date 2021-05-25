package com.example.leswebviews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView wv_WebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupérer l'objet webview
        wv_WebView = (WebView) findViewById(R.id.monwebview);

        //Surcharger le webViewClient
        wv_WebView.setWebViewClient( new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        //Les settings
        WebSettings settings = wv_WebView.getSettings();

        //Pour afficher le bouton de zoom
        settings.setBuiltInZoomControls(true);

        //Activer le Javascript

        settings.setJavaScriptEnabled(true);

        WebView.setWebContentsDebuggingEnabled(true);

        //Effacement du cache
        wv_WebView.clearCache(true);

        //suppression du stockage de données dans le cache
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        wv_WebView.getSettings().setDomStorageEnabled(true);

        wv_WebView.setWebChromeClient(new WebChromeClient());
        //wv_WebView.

        //Chargement de l'URL :
        wv_WebView.loadUrl("https://fr.wikipedia.org/wiki/Sanglier");


        String s_Web = "<html><body>Bonjour le monde !</body></html>";
        wv_WebView.loadDataWithBaseURL("",s_Web,"text/html","UTF-8","");

        wv_WebView.loadData(s_Web,"text/html","UTF-8");

        wv_WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                return super.onJsAlert(view, url, message, result);
            }
        });

        //Un bouton, tu cliques dessus et ça affiche une alert et un message dans la console (visible dans le logcat) :
        String WEB_CONTENT = "<html><body><input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android !')\">\n"+
                "<script type=\"text/javascript\">function showAndroidToast(toast) {alert(toast);console.log(toast);}</script></body></html>";

        wv_WebView.loadData(WEB_CONTENT,"text/html","UTF-8");

        //Lier l'interface JavaScript à la WebView
        wv_WebView.addJavascriptInterface(new JavaScriptToastInterface(this),"Android");
        /*"Android" est l'identifiant qu'aura cette interface dans le code HTML =>
        * Possibilité d'ajouter plusieurs interfaces javascript avec des identifiants différents.*/

        WEB_CONTENT = "<html><body><input type=\"button\" value=\"Bonjour\" onClick=\"AfficherToastAndroid('Bonjour !')\">\n"+
                "<script type=\"text/javascript\">function AfficherToastAndroid(Texte) {Android.showToast(Texte);}</script></body></html>";
       // wv_WebView.loadData(WEB_CONTENT,"text/html","UTF-8");
    }

    //Gestion du bouton Retour
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.wv_WebView.canGoBack())
        {
            this.wv_WebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}