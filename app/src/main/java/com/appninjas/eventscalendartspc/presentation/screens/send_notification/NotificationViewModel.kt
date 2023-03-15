package com.appninjas.eventscalendartspc.presentation.screens.send_notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.usecase.SendNotificationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(private val sendNotificationUseCase: SendNotificationUseCase) : ViewModel() {

    fun sendNotification(title: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendNotificationUseCase.invoke(title, body)
        }
    }

}