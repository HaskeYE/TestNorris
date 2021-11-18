package com.example.testnorris.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnorris.R
import com.example.testnorris.databinding.FragmentHomeBinding
import com.example.testnorris.ui.CustomRecyclerAdapter

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
        val root: View = binding.root

        val editText: EditText = binding.editText

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = CustomRecyclerAdapter(emptyList())

        return root
    }



    fun click(view: View) {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = CustomRecyclerAdapter(listOf("hehe", "hihi"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}