package com.appninjas.eventscalendartspc.presentation.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.User
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        binding.buttonRegSubmit.setOnClickListener {
            val validationResult = validateCredentials()
            if (validationResult) {
                registerUser(User(
                    fullName = binding.fullNameEditText.text.toString(),
                    email = binding.emailRegisterEditText.text.toString(),
                    password = binding.passwordRegisterEditText.text.toString()
                ))
            }
        }
    }

    private fun initUI() {}

    private fun validateCredentials(): Boolean {
        val inputFullName = binding.fullNameEditText.text.toString()
        val inputEmail = binding.emailRegisterEditText.text.toString()
        val inputPassword = binding.passwordRegisterEditText.text.toString()
        val inputConfirmPassword = binding.passwordConfirmEditText.text.toString()

        return if (inputFullName.isEmpty() || inputEmail.isEmpty() || inputPassword.isEmpty() || inputConfirmPassword.isEmpty()) {
            toast("Ошибка. Заполните все поля")
            false
        } else if (inputPassword != inputConfirmPassword) {
            toast("Пароли не совпадают")
            false
        } else {
            true
        }
    }

    private fun registerUser(user: User) {
        val firebaseAuth = Firebase.auth
        val firebaseDb = Firebase.firestore

        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: HashMap<String, String> = hashMapOf(
                    "fullName" to user.fullName,
                    "email" to user.email,
                    "password" to user.password
                )

                firebaseDb.collection("users")
                    .add(user)
                    .addOnCompleteListener {
                        toast("Вы успешно зарегестрировались!")
                        findNavController().navigate(R.id.loginFragment2)
                    }
                    .addOnFailureListener { e -> toast("Произошла ошибка при регистрации $e") }
            } else {
                toast("Произошла ошибка базы данных при регистрации")
            }
        }

    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}