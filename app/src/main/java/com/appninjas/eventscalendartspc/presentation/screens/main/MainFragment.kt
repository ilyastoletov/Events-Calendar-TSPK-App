package com.appninjas.eventscalendartspc.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutBtn.setOnClickListener {
            userLogout()
        }
    }

    private fun userLogout() {
        val firebaseAuth = Firebase.auth
        firebaseAuth.signOut()
        Toast.makeText(context, "Вы вышли из приложения", Toast.LENGTH_SHORT).show()
    }
}