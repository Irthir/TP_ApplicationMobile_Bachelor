package com.example.leswebviews;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptToastInterface {
    Context context;

    JavaScriptToastInterface(Context c)
    {
        context = c;
    }

    //Méthode affichant le toast
    @JavascriptInterface
    public void showToast(String toastMsg)
    {
        Toast.makeText(context,toastMsg,Toast.LENGTH_SHORT).show();
    }
}
