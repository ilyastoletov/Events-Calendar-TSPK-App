package com.appninjas.eventscalendartspc.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.data.firebase.implementation.UserRepositoryImplementation
import com.appninjas.domain.model.User
import com.appninjas.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel: ViewModel() {

    private val userRepository = UserRepositoryImplementation()

    private val registerUserUseCase = RegisterUserUseCase(userRepository)

    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUserUseCase.invoke(user, onSuccess, onFailure)
        }
    }

}