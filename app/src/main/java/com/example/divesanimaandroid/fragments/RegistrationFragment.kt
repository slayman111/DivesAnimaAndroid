package com.example.divesanimaandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.models.dto.request.AuthRequest
import com.example.divesanimaandroid.utils.http.DivesAnimaClient
import com.example.divesanimaandroid.utils.toast.ToastUtil
import kotlinx.android.synthetic.main.fragment_registration.buttonSignUp
import kotlinx.android.synthetic.main.fragment_registration.editTextLogin
import kotlinx.android.synthetic.main.fragment_registration.editTextPassword
import kotlinx.android.synthetic.main.fragment_registration.editTextPasswordRepeat
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {

    private lateinit var divesAnimaClient: DivesAnimaClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        divesAnimaClient = DivesAnimaClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignUp.setOnClickListener {
            if (editTextPassword.text.isNotEmpty() && editTextLogin.text.isNotEmpty()) {
                if (editTextPassword.text.toString() == editTextPasswordRepeat.text.toString())
                    lifecycleScope.launch {
                        divesAnimaClient.register(
                            AuthRequest(
                                editTextLogin.text.trim().toString(),
                                editTextPasswordRepeat.text.trim().toString()
                            )
                        )?.let {
                            ToastUtil.show(
                                requireContext(),
                                requireContext().getString(R.string.account_created)
                            )
                            Navigation.findNavController(view).popBackStack()
                        }
                    }
                else ToastUtil.show(
                    requireContext(),
                    requireContext().getString(R.string.password_not_match)
                )
            } else {
                ToastUtil.show(
                    requireContext(),
                    requireContext().getString(
                        R.string.empty_password_or_login
                    )
                )
            }
        }
    }

}