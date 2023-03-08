package com.appninjas.eventscalendartspc.presentation.screens.profile

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.appninjas.data.repository.EventRepositoryImpl
import com.appninjas.data.repository.UserRepositoryImplementation
import com.appninjas.domain.model.Event
import com.appninjas.domain.model.User
import com.appninjas.domain.usecase.GetEventsFromStorageUseCase
import com.appninjas.domain.usecase.GetUserDataUseCase
import com.appninjas.domain.usecase.LogoutUseCase
import com.appninjas.eventscalendartspc.presentation.screens.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getEventsFromStorageUseCase: GetEventsFromStorageUseCase,
    private val logoutUseCase: LogoutUseCase
    ): ViewModel() {

    private val _userData: MutableLiveData<User> = MutableLiveData()
    val userData: LiveData<User> = _userData

    private val _eventsList: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsList: LiveData<List<Event>> = _eventsList

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserDataUseCase.invoke()
            _userData.postValue(result)
        }
    }

    fun getEventsFromStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getEventsFromStorageUseCase.invoke()
            _eventsList.postValue(result)
        }
    }

    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke()
        }
    }

}