package com.dupleit.apps.kotlintexting

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.ActionMode
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.dupleit.apps.kotlintexting.Interface.WebAppInterface
import com.dupleit.apps.kotlintexting.Models.ServerData
import com.dupleit.demo.kotlintest.Network.ApiClient
import com.dupleit.demo.kotlintest.Network.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.square1.richtextlib.v2.RichTextV2
import io.square1.richtextlib.v2.content.RichTextDocumentElement



class MainActivity : AppCompatActivity() {
    var element: RichTextDocumentElement? = null
    lateinit var ctx:Context
    private var mActionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ctx = this
        getDataServer()
        userText.getSettings().setJavaScriptEnabled(true)
        userText.addJavascriptInterface(WebAppInterface(), "js")
       /* userText.setOnLongClickListener(View.OnLongClickListener {
            userText.loadUrl("javascript:WebAppInterface.callback(window.getSelection().toString())")
            false
        })*/
    }

    private fun getDataServer() {
        val service = ApiClient.client!!.create(ApiService::class.java)
        val userCall = service.getData()
        userCall.enqueue(object : Callback<ServerData> {
            override fun onResponse(call: Call<ServerData>, response: Response<ServerData>) {
                if (response.isSuccessful){
                    for (item in response.body()!!.data!!) {
                        // body of loop
                        //element = RichTextV2.textFromHtml(ctx, item.text)
                        userText.loadData(item.text, "text/html", "UTF-8")
                        //userText.loadUrl("javascript:WebAppInterface.callback(window.getSelection().toString())")
                        /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //userText. = Html.fromHtml(item.text,Html.FROM_HTML_MODE_COMPACT)
                        }else{
                            //userText.text = Html.fromHtml(item.text)
                        }*/
                    }
                }
            }

            override fun onFailure(call: Call<ServerData>, t: Throwable) {
                Log.d("onFailure", t.toString())
            }
        })
    }


    override fun onActionModeStarted(mode: ActionMode) {
        if (mActionMode == null) {
            mActionMode = mode
            val menu = mode.menu
            // Remove the default menu items (select all, copy, paste, search)
            menu.clear()

            // If you want to keep any of the defaults,
            // remove the items you don't want individually:
            // menu.removeItem(android.R.id.[id_of_item_to_remove])

            // Inflate your own menu items
            mode.menuInflater.inflate(R.menu.menu, menu)
        }

        super.onActionModeStarted(mode)
    }

    // This method is what you should set as your item's onClick
    // <item android:onClick="onContextualMenuItemClicked" />
    fun onContextualMenuItemClicked(item: MenuItem) {
        when (item.itemId) {
            R.id.example_item_1 -> {
                userText.loadUrl("javascript:WebAppInterface.callback(window.getSelection().toString())")
            }
            R.id.example_item_2 -> {
            }
            else -> {
            }
        }// do some stuff
        // do some different stuff
        // ...

        // This will likely always be true, but check it anyway, just in case
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
    }

    override fun onActionModeFinished(mode: ActionMode) {
        mActionMode = null
        super.onActionModeFinished(mode)
    }
}
