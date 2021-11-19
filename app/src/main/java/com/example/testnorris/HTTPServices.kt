package com.example.testnorris
//For now this is out of use - will be in cycle in final build
import okhttp3.*
import java.io.IOException

class HTTPServices {
    val URL = "https://api.icndb.com/jokes/random/"
    var okHttpClient: OkHttpClient = OkHttpClient()

    fun getJokes(num: Int): List<String> {
        var out = emptyList<String>()
        var usableUrl = URL + num.toString()
        val request: Request = Request.Builder().url(usableUrl).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string().toString().split("\"joke\": \"")
                for (i in 1 until json.size)
                    out = out + json[i].split("\", \"categories\"")[0]
            }
        })
        Thread.sleep(1000)
        return out
    }
}