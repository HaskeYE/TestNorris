package com.example.testnorris.ui.home

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HTTPhelper {
    companion object {
        private val url = "https://api.icndb.com/jokes/random/"
        var okHttpClient: OkHttpClient = OkHttpClient()
        fun getJokes(num: Int): List<String> {

            var out = emptyList<String>()
            val usableUrl = url + num.toString()
            val request: Request = Request.Builder().url(usableUrl).build()
            val response = okHttpClient.newCall(request).execute()
            response.use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                else {
                    //If we got answer we are trying to parse received json into list array
                    //Maybe not a smartest choice but reliable - maybe will be replaced in final ver
                    val json =
                        response?.body()?.string().toString().split("\"joke\": \"")
                    for (i in 1 until json.size)
                        out = out + json[i].split("\", \"categories\"")[0]
                }
                return out
            }
        }
    }
}