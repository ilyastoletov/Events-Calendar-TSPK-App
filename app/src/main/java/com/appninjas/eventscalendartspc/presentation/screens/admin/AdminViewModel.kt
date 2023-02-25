package com.appninjas.eventscalendartspc.presentation.screens.admin

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.appninjas.data.repository.EventRepositoryImpl
import com.appninjas.domain.model.Event
import com.appninjas.domain.usecase.AddEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel(application: Application): ViewModel() {

    private val appContext: Context = application.applicationContext

    private val eventsRepoImpl = EventRepositoryImpl(appContext)
    private val addEventUseCase = AddEventUseCase(eventsRepoImpl)

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            addEventUseCase.invoke(event)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return AdminViewModel(application) as T
            }
        }
    }

}