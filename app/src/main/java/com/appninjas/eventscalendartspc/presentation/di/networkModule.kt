package com.appninjas.eventscalendartspc.presentation.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val networkModule = module {

    single<FirebaseFirestore> {
        Firebase.firestore
    }

    single<FirebaseAuth> {
        Firebase.auth
    }

}