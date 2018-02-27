package com.dupleit.apps.kotlintexting

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.ActionMode
import android.view.MenuItem
import android.widget.Toast
import com.dupleit.apps.kotlintexting.Interface.MyTagHandler
import com.dupleit.apps.kotlintexting.Models.Datum
import com.dupleit.apps.kotlintexting.Models.ServerData
import com.dupleit.apps.kotlintexting.SpannableTextModal.Spandata
import com.dupleit.demo.kotlintest.Network.ApiClient
import com.dupleit.demo.kotlintest.Network.ApiService
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.realm.RealmConfiguration



class MainActivity : AppCompatActivity() {
    lateinit var ctx:Context
    private var mActionMode: ActionMode? = null
    lateinit var realm: Realm
    var DataId:String? = null
    //lateinit var userServe: ArrayList<ServerData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctx = this
        try {
            realm = Realm.getDefaultInstance()
        } catch (e: Exception) {
            // Get a Realm instance for this thread
            val config = RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build()
            realm = Realm.getInstance(config)
        }

        val results = realm.where<Datum>().findAll()

        Log.d("result","Size of Data ${results.size}")
        if (results.size<=0){
            getDataServer()
        }else{
            for (item in results) {
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    userText.text = Html.fromHtml(item.text, Html.FROM_HTML_MODE_COMPACT, null, MyTagHandler())
                } else {
                    userText.text = Html.fromHtml(item.text, null, MyTagHandler())
                }*/
                //userText.text = item.editedText
                DataId = item.id
                updateSpanUI(item.text)
            }
        }
    }

    private fun updateSpanUI(text: String?) {
        val selectiontable = realm.where<Spandata>().findAll()
        if (selectiontable.size>0){
            var originalTextWithHtmlTags: Spanned? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT, null, MyTagHandler())
            } else {
                Html.fromHtml(text, null, MyTagHandler())
            }
            val WordtoSpan = SpannableString(originalTextWithHtmlTags)
            for (item in selectiontable){
                val min = Math.max(0, Math.min(item.startIndex!!, item.endIndex!!))
                val max = Math.max(0, Math.max(item.startIndex!!, item.endIndex!!))
                // Perform your definition lookup with the selected text
                //val selectedText = userText.text.subSequence(min, max)
                Log.d("Realm Span min max"," $min-----$max")
                WordtoSpan.setSpan(ForegroundColorSpan(Color.RED), min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                Log.d("Realm Span"," ${item.startIndex}-----${item.endIndex}")
                userText.text = WordtoSpan
            }
        }else{
            var originalTextWithHtmlTags: Spanned? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT, null, MyTagHandler())
                } else {
                    Html.fromHtml(text, null, MyTagHandler())
                }
            userText.text = originalTextWithHtmlTags
            Log.d("Realm Span",""+selectiontable.size)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun getDataServer() {
        //Toast.makeText(this,"Server Called",Toast.LENGTH_LONG).show()
        val service = ApiClient.client!!.create(ApiService::class.java)
        val userCall = service.getData()
        userCall.enqueue(object : Callback<ServerData> {
            override fun onResponse(call: Call<ServerData>, response: Response<ServerData>) {
                if (response.isSuccessful){
                    for (item in response.body()!!.data!!) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            userText.text = Html.fromHtml(item.text,Html.FROM_HTML_MODE_COMPACT,null,MyTagHandler())
                        }else{
                            userText.text = Html.fromHtml(item.text, null, MyTagHandler())
                        }

                        realm.beginTransaction()

                        var data: Datum = realm.createObject<Datum>(item.id)
                        data.class_ = item.class_
                        //data.id = item.id
                        data.name = item.name
                        data.text = item.text
                        //data.editedText = SpannableString(item.text)
                        //data.uniqueId = Integer.parseInt(item.id)
                        realm.commitTransaction()

                        //userText.text = SpannableString(item.text)


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
               // userText.loadUrl("javascript:WebAppInterface.callback(window.getSelection().toString())")
                var min = 0
                var max = userText.text.length
                var selStart =0
                var selEnd = 0
                if (userText.isFocused) {
                    selStart = userText.selectionStart
                    selEnd = userText.selectionEnd

                    min = Math.max(0, Math.min(selStart, selEnd))
                    max = Math.max(0, Math.max(selStart, selEnd))
                }
                // Perform your definition lookup with the selected text
                val selectedText = userText.text.subSequence(min, max)
                val WordtoSpan = SpannableString(userText.text)
                WordtoSpan.setSpan(ForegroundColorSpan(Color.RED), min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                userText.text = WordtoSpan

                scrollView.smoothScrollTo(userText.selectionStart,0)
                // Finish and close the ActionMode
                Log.d("usersText",""+selectedText)

                var key: Int
                try {
                    key = realm.where(Spandata::class.java).findAll().size + 1
                } catch (ex: ArrayIndexOutOfBoundsException) {
                    key = 0
                }

                realm.beginTransaction()

                var data: Spandata = realm.createObject<Spandata>(key)
                data.dataID = DataId
                //data.id = item.id
                data.startIndex = selStart
                data.endIndex = selEnd
                //data.editedText = SpannableString(item.text)
                //data.uniqueId = Integer.parseInt(item.id)
                realm.commitTransaction()
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

