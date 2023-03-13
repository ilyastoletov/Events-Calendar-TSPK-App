package com.appninjas.eventscalendartspc.presentation.screens.add_event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Event
import com.appninjas.domain.usecase.AddEventUseCase
import com.appninjas.domain.usecase.SendNotificationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEventViewModel(private val addEventUseCase: AddEventUseCase,
                        private val sendNotificationUseCase: SendNotificationUseCase): ViewModel() {

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            addEventUseCase.invoke(event)
        }
    }

}