package com.appninjas.eventscalendartspc.presentation.di

import com.appninjas.data.mapper.EventMapper
import com.appninjas.data.repository.EventRepositoryImpl
import com.appninjas.data.repository.UserRepositoryImplementation
import com.appninjas.domain.repository.EventsRepository
import com.appninjas.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<EventsRepository> {
        EventRepositoryImpl(
            firebaseDb = get(),
            eventDao = get(),
            mapper = EventMapper(),
            notificationApiService = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImplementation(
            firebaseDb = get(),
            firebaseAuth = get(),
            mapper = EventMapper()
        )
    }
}