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

class ProfileViewModel(application: Application): ViewModel() {

    private val appContext = application.applicationContext

    private val userRepoImpl = UserRepositoryImplementation()
    private val eventsRepoImpl = EventRepositoryImpl(appContext)

    private val getUserDataUseCase = GetUserDataUseCase(userRepoImpl)
    private val getEventsFromStorageUseCase = GetEventsFromStorageUseCase(eventsRepoImpl)
    private val logoutUseCase = LogoutUseCase(userRepoImpl)

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
            _eventsList.postValue(getEventsFromStorageUseCase.invoke())
        }
    }

    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ProfileViewModel(application) as T
            }
        }
    }

}