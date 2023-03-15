package com.appninjas.eventscalendartspc.presentation.screens.profile

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.appninjas.data.repository.EventRepositoryImpl
import com.appninjas.data.repository.UserRepositoryImplementation
import com.appninjas.domain.model.Event
import com.appninjas.domain.model.User
import com.appninjas.domain.usecase.*
import com.appninjas.eventscalendartspc.presentation.screens.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getEventsFromStorageUseCase: GetEventsFromStorageUseCase,
    private val notificationControlUseCase: NotificationControlUseCase,
    private val getNotificationStateUseCase: GetNotificationStateUseCase,
    private val logoutUseCase: LogoutUseCase
    ): ViewModel() {

    private val _userData: MutableLiveData<User> = MutableLiveData()
    val userData: LiveData<User> = _userData

    private val _eventsList: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsList: LiveData<List<Event>> = _eventsList

    private val _notifState: MutableLiveData<Boolean> = MutableLiveData()
    val notifState: LiveData<Boolean> = _notifState

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

    fun switchNotifications(toState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationControlUseCase.invoke(toState)
        }
    }

    fun getNotificationsState() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNotificationStateUseCase.invoke()
            _notifState.postValue(result)
        }
    }

    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke()
        }
    }

}