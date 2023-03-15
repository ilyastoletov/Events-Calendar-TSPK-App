package com.appninjas.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.appninjas.data.mapper.EventMapper
import com.appninjas.domain.model.Event
import com.appninjas.domain.model.User
import com.appninjas.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

private const val SHARED_PREFS_KEY = "notifications"

class UserRepositoryImplementation(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDb: FirebaseFirestore,
    private val firebaseMessaging: FirebaseMessaging,
    private val sharedPrefs: SharedPreferences,
    private val mapper: EventMapper
    ): UserRepository {


    override suspend fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userDb: HashMap<String, String> = hashMapOf(
                        "fullName" to user.fullName,
                        "email" to user.email,
                        "password" to user.password,
                        "isAdmin" to user.isAdmin.toString()
                    )

                    firebaseDb.collection("users")
                        .document(user.email)
                        .set(userDb)
                        .addOnCompleteListener {onSuccess()}
                        .addOnFailureListener {onFailure()}
                } else {
                    onFailure()
                }
            }
    }

    override suspend fun loginUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun getUserData(): User {
        val userEmail = firebaseAuth.currentUser?.email ?: return User("error", "error", "error")
        val userDbData = firebaseDb.collection("users").document(userEmail).get().await()
        return try {
            val documentData = userDbData.data!!
            User(
                fullName = documentData["fullName"].toString(),
                email = documentData["email"].toString(),
                isAdmin = documentData["isAdmin"].toString().toBoolean()
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Unable to fetch user info $e")
            User("error", "error", "error")
        }
    }

    override suspend fun getEventsList(): Map<String, List<Event>> {
        val allEventsDocuments = firebaseDb.collection("events").get().await()
        val activeEventsList: ArrayList<Event> = arrayListOf()
        val endedEventsList: ArrayList<Event> = arrayListOf()
        for (document in allEventsDocuments.documents) {
            val documentData = document.data!!
            val modelFromDocument = mapper.mapEventDocumentToEntity(documentData)
            if (modelFromDocument.status == "Завершено") {
                endedEventsList.add(modelFromDocument)
            } else {
                activeEventsList.add(modelFromDocument)
            }
        }
        return mapOf(
            "active" to activeEventsList,
            "ended" to endedEventsList
        )
    }

    override suspend fun controlNotifications(toState: Boolean) {
        if (toState) {
            firebaseMessaging.subscribeToTopic("xyz")
            sharedPrefs.edit().putBoolean(SHARED_PREFS_KEY, true).commit()
        } else {
            firebaseMessaging.unsubscribeFromTopic("xyz")
            sharedPrefs.edit().putBoolean(SHARED_PREFS_KEY, false).commit()
        }
    }

    override suspend fun getNotificationState(): Boolean = sharedPrefs.getBoolean(SHARED_PREFS_KEY, true)

}