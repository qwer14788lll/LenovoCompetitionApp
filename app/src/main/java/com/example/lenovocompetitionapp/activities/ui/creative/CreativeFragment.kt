package com.example.lenovocompetitionapp.activities.ui.creative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lenovocompetitionapp.databinding.FragmentCreativeBinding

class CreativeFragment : Fragment() {

    private lateinit var mCreativeViewModel: CreativeViewModel
    private var _binding: FragmentCreativeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCreativeViewModel =
            ViewModelProvider(this).get(CreativeViewModel::class.java)

        _binding = FragmentCreativeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        mCreativeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}