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
import kotlinx.coroutines.*
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
            ViewModelProvider(this)[HomeViewModel::class.java]
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
        button.setOnClickListener {
            //Going coroutines! This will make our loading faster and will provide result awaiting
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    //Needed this toInt because this is guarantee that definitely number was entered
                    val list = HTTPhelper.getJokes(editText.text.toString().toInt())
                    launch(Dispatchers.Main) {
                        recyclerView.adapter = CustomRecyclerAdapter(list)
                    }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    this.context, "Don't fool me: can't see here a numeric value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val url = "https://api.icndb.com/jokes/random/"
    var okHttpClient: OkHttpClient = OkHttpClient()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}