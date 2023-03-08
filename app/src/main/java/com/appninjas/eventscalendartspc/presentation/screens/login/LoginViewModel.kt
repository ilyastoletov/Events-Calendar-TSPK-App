package com.appninjas.eventscalendartspc.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.data.repository.UserRepositoryImplementation
import com.appninjas.domain.model.User
import com.appninjas.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {

    fun loginUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.invoke(user, onSuccess, onFailure)
        }
    }

}