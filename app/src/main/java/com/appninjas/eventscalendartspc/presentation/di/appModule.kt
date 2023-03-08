package com.appninjas.eventscalendartspc.presentation.di

import com.appninjas.eventscalendartspc.presentation.screens.admin.AdminViewModel
import com.appninjas.eventscalendartspc.presentation.screens.login.LoginViewModel
import com.appninjas.eventscalendartspc.presentation.screens.main.MainViewModel
import com.appninjas.eventscalendartspc.presentation.screens.profile.ProfileViewModel
import com.appninjas.eventscalendartspc.presentation.screens.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        AdminViewModel(addEventUseCase = get())
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
            logoutUseCase = get()
        )
    }

    viewModel {
        RegistrationViewModel(
            registerUserUseCase = get()
        )
    }

}