package com.example.testnorris
//For now this is out of use - will be in cycle in final build
import android.os.AsyncTask
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.*
import java.io.IOException

class HTTPServices {
    val url = "https://api.icndb.com/jokes/random/"
    var okHttpClient: OkHttpClient = OkHttpClient()

    public suspend fun getJokes(num: Int): List<String> = coroutineScope {
        var out = emptyList<String>()
        val usableUrl = url + num.toString()
        val request: Request = Request.Builder().url(usableUrl).build()
        val response = async { okHttpClient.newCall(request).execute() }.await()
        response.use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            else {
                //If we got answer we are trying to parse received json into list array
                //Maybe not a smartest choice but reliable - maybe will be replaced in final ver
                val json = async {
                    response?.body()?.string().toString().split("\"joke\": \"")
                }.await()
                for (i in 1 until json.size)
                    out = out + json[i].split("\", \"categories\"")[0]

            }
            return@coroutineScope out
        }
    }
}