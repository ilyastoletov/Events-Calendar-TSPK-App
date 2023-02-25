package com.appninjas.eventscalendartspc.presentation.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.User
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val activity: AppCompatActivity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.hide()
        binding.registerBtn.setOnClickListener {
            val validationResult = validateCredentials()
            if (validationResult) {
                registerUser(User(
                    fullName = binding.fullNameEditText.text.toString(),
                    email = binding.emailRegisterEditText.text.toString(),
                    password = binding.passwordRegisterEditText.text.toString()
                ))
            }
        }
        binding.loginFromRegFragment.setOnClickListener {
            findNavController().navigate(R.id.loginFragment2)
        }
    }

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
        val registerSuccess = {
            toast("Вы успешно зарегестрировались!")
            findNavController().navigate(R.id.loginFragment2)
        }
        val registerFail = {
            toast("Произошла ошибка базы данных при регистрации")
        }
        viewModel.registerUser(user, registerSuccess, registerFail)
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}