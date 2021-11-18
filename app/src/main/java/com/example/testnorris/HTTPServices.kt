package com.example.testnorris

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class HTTPServices: AppCompatActivity() {
    val URL = "https://api.icndb.com/jokes/random/"
    var okHttpClient: OkHttpClient = OkHttpClient()

    public fun getJokes(num: Int): List<String> {
        var out = emptyList<String>()
        var usableUrl = URL + num.toString()
        val request: Request = Request.Builder().url(usableUrl).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                val got = JSONObject(json).getJSONObject("value").toString().
                split(" \"joke\": \"")
                for (i in 1 until got.size) {
                    out += got[i].split("\"")[0]
                }
            }
        })
    return out
    }
}