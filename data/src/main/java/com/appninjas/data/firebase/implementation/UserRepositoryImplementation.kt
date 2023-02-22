package com.appninjas.data.firebase.implementation

import com.appninjas.domain.model.User
import com.appninjas.domain.repository.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepositoryImplementation: UserRepository {

    private val firebaseAuth = Firebase.auth
    private val firebaseDb = Firebase.firestore

    override suspend fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: HashMap<String, String> = hashMapOf(
                        "fullName" to user.fullName,
                        "email" to user.email,
                        "password" to user.password
                    )

                    firebaseDb.collection("users")
                        .add(user)
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

}