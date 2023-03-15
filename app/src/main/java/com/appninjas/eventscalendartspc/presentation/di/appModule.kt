package com.appninjas.eventscalendartspc.presentation.di

import com.appninjas.eventscalendartspc.presentation.screens.add_event.AddEventViewModel
import com.appninjas.eventscalendartspc.presentation.screens.login.LoginViewModel
import com.appninjas.eventscalendartspc.presentation.screens.main.MainViewModel
import com.appninjas.eventscalendartspc.presentation.screens.profile.ProfileViewModel
import com.appninjas.eventscalendartspc.presentation.screens.registration.RegistrationViewModel
import com.appninjas.eventscalendartspc.presentation.screens.send_notification.NotificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        AddEventViewModel(
            addEventUseCase = get(),
            sendNotificationUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(loginUseCase = get())
    }

    viewModel {
        MainViewModel(
            getEventsListUseCase = get(),
            getUserDataUseCase = get(),
            saveEventToStorageUseCase = get(),
            endEventUseCase = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getUserDataUseCase = get(),
            getEventsFromStorageUseCase = get(),
            logoutUseCase = get(),
            notificationControlUseCase = get(),
            getNotificationStateUseCase = get()
        )
    }

    viewModel {
        RegistrationViewModel(
            registerUserUseCase = get()
        )
    }

    viewModel {
        NotificationViewModel(
            sendNotificationUseCase = get()
        )
    }

}