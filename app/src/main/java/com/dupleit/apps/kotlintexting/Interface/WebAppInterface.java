package com.dupleit.apps.kotlintexting.Interface;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface
{
    @JavascriptInterface
    public void callback(String value)
    {
        Log.v("Selection", "SELECTION:" + value);
    }
}