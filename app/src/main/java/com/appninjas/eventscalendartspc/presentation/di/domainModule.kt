package com.appninjas.eventscalendartspc.presentation.di

import com.appninjas.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory {
        AddEventUseCase(repository = get())
    }

    factory {
        EndEventUseCase(repository = get())
    }

    factory {
        GetEventsFromStorageUseCase(repository = get())
    }

    factory {
        GetEventsListUseCase(repository = get())
    }

    factory {
        GetUserDataUseCase(repository = get())
    }

    factory {
        LoginUseCase(repository = get())
    }

    factory {
        LogoutUseCase(repository = get())
    }

    factory {
        RegisterUserUseCase(repository = get())
    }

    factory {
        SaveEventToStorageUseCase(repository = get())
    }

    factory {
        SendNotificationUseCase(repository = get())
    }

    factory {
        NotificationControlUseCase(repository = get())
    }

    factory {
        GetNotificationStateUseCase(repository = get())
    }
}