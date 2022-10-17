package com.most4dev.acquaintanceapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.most4dev.acquaintanceapp.R
import com.most4dev.acquaintanceapp.viewModels.AcquaintanceViewModel
import kotlinx.android.synthetic.main.fragment_send.*

class SendFragment : Fragment() {

    private lateinit var acquaintanceViewModelAcquaintance: AcquaintanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        acquaintanceViewModelAcquaintance =
            ViewModelProvider(this)[AcquaintanceViewModel::class.java]
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendButton.setOnClickListener {
            if (inputEditTextName.text!!.isNotEmpty() &&
                inputEditTextTheme.text!!.isNotEmpty() &&
                inputEditTextMessage.text!!.isNotEmpty()
            ) {
                acquaintanceViewModelAcquaintance.sendAbuse(
                    inputEditTextName.text.toString(),
                    inputEditTextTheme.text.toString(),
                    inputEditTextMessage.text.toString()
                )
                Toast.makeText(
                    requireContext(),
                    R.string.send_mess_complaint,
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.enter_valid_data,
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }
}