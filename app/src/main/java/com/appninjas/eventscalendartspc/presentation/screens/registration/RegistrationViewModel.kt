package com.appninjas.eventscalendartspc.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.data.repository.UserRepositoryImplementation
import com.appninjas.domain.model.User
import com.appninjas.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registerUserUseCase: RegisterUserUseCase): ViewModel() {

    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUserUseCase.invoke(user, onSuccess, onFailure)
        }
    }

}