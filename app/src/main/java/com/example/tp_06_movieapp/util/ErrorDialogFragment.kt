package com.example.tp_06_movieapp.util

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import com.example.tp_06_movieapp.activity.MainActivity
import com.example.tp_06_movieapp.databinding.FragmentDialogBinding

class ErrorDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding
    private var titulo_dialogo: String? = null
    private var descripcion_dialogo: String? = null

    companion object {
        private const val TITLE = "Title"
        private const val DESCRIPTION = "Description"

        fun newInstance(
            title: String? = null,
            description: String? = null
        ): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            val arguments = Bundle()
            arguments.putString(TITLE, title)
            arguments.putString(DESCRIPTION, description)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogBinding.inflate(layoutInflater)
        showsDialog = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo_dialogo = arguments?.getString(TITLE)
        descripcion_dialogo = arguments?.getString(DESCRIPTION)
        binding.botonDialogo.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}