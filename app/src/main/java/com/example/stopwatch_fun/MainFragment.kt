package com.example.stopwatch_fun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val time = view.findViewById<TextView>(R.id.time)

        viewModel.time
            .onEach { time.text = it.toString() }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        view.findViewById<View>(R.id.start).setOnClickListener {
            viewModel.start()
        }
        view.findViewById<View>(R.id.pause).setOnClickListener {
            viewModel.pause()
        }
        view.findViewById<View>(R.id.reset).setOnClickListener {
            viewModel.reset()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}