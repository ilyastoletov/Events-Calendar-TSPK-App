package com.appninjas.eventscalendartspc.presentation.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.User
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if(binding.emailLoginEditText.text.toString().isNotEmpty() && binding.passwordLoginEditText.text.toString(). isNotEmpty()) {
                loginUser(User(email = binding.emailLoginEditText.text.toString(), password = binding.passwordLoginEditText.text.toString()))
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(user: User) {
        val firebaseAuth = Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Успешный вход в приложение", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainFragment)
            } else {
                Toast.makeText(context, "Неверный адрес эл. почты или пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }

}