package com.appninjas.eventscalendartspc.presentation.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.User
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val activity: AppCompatActivity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.hide()
        binding.loginBtn.setOnClickListener {
            val emailField = binding.loginEmailEditText.text.toString()
            val passField = binding.loginPasswordEditText.text.toString()
            if(emailField.isNotEmpty() && passField.isNotEmpty()) {
                loginUser(User(email = emailField, password = passField))
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        binding.registerFromLoginFragment.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }

    private fun loginUser(user: User) {
        val authSuccess = {
            Toast.makeText(context, "Успешный вход в приложение", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.mainFragment)
        }
        val authFailure = { Toast.makeText(context, "Неверный адрес эл. почты или пароль", Toast.LENGTH_SHORT).show() }
        viewModel.loginUser(user, authSuccess, authFailure)
    }

}