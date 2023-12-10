package com.example.divesanimaandroid.fragments

import android.content.Context.MODE_PRIVATE
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
import kotlinx.android.synthetic.main.fragment_authorization.buttonSignIn
import kotlinx.android.synthetic.main.fragment_authorization.buttonSignUp
import kotlinx.android.synthetic.main.fragment_authorization.editTextLogin
import kotlinx.android.synthetic.main.fragment_authorization.editTextPassword
import kotlinx.coroutines.launch

class AuthorizationFragment : Fragment() {

    private lateinit var divesAnimaClient: DivesAnimaClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        divesAnimaClient = DivesAnimaClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignIn.setOnClickListener {
            lifecycleScope.launch {
                divesAnimaClient.login(
                    AuthRequest(
                        editTextLogin.text.trim().toString(),
                        editTextPassword.text.trim().toString()
                    )
                )?.let { response ->
                    activity?.getPreferences(MODE_PRIVATE)?.edit()
                        ?.putString(getString(R.string.sp_jwt), response.token)?.apply()

                    Navigation.findNavController(view)
                        .navigate(R.id.action_authorizationFragment_to_articlesFragment)
                }
            }
        }

        buttonSignUp.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
    }

}