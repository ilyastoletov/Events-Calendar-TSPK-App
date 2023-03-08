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

class AdminViewModel(private val addEventUseCase: AddEventUseCase): ViewModel() {

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            addEventUseCase.invoke(event)
        }
    }

}