package com.example.testnorris.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnorris.CustomRecyclerAdapter
import com.example.testnorris.databinding.FragmentHomeBinding
import okhttp3.*
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText: EditText = binding.editText
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = CustomRecyclerAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val button: Button = binding.button
        button.setOnClickListener() {
            //Needed this toInt because this is guarantee that definitely number was entered
            try {
                val list = getJokes(editText.text.toString().toInt())
                recyclerView.adapter = CustomRecyclerAdapter(list)
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    this.context, "Don't fool me: can't see here a numeric value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val url = "https://api.icndb.com/jokes/random/"
    var okHttpClient: OkHttpClient = OkHttpClient()

    fun getJokes(num: Int): List<String> {
        var out = emptyList<String>()
        var usableUrl = url + num.toString()
        val request: Request = Request.Builder().url(usableUrl).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                throw IOException("no answer from server")
            }
            //If we got answer we are trying to parse received json into list array
            override fun onResponse(call: Call?, response: Response?) {
                //Maybe not a smartest choice but reliable - maybe will be replaced in final ver
                val json = response?.body()?.string().toString().split("\"joke\": \"")
                for (i in 1 until json.size)
                    out = out + json[i].split("\", \"categories\"")[0]
            }
        })
        Thread.sleep(400)
        return out
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}