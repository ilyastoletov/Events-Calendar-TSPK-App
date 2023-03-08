package com.appninjas.eventscalendartspc.presentation.screens.profile

import android.content.DialogInterface
import com.appninjas.domain.model.Event
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentProfileBinding
import com.appninjas.eventscalendartspc.presentation.adapters.EventsAdapter
import com.appninjas.eventscalendartspc.presentation.screens.admin.AdminViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val activity: AppCompatActivity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Профиль"
        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) {
            binding.profileNameTextView.text = "Имя и фамилия: " + it.fullName
            binding.profileStatusTextView.text = "Статус: " + when(it.isAdmin) {
                true -> "Администратор"
                false -> "Пользователь"
            }
        }
        viewModel.getEventsFromStorage()
        viewModel.eventsList.observe(viewLifecycleOwner) {events ->
            println(events.size)
            binding.profileEventsTextView.text = "Посещено мероприятий: " + events.size.toString()
            val rvAdapter = EventsAdapter(eventsList = events, userAdmin = false, fromProfile = true, listener = null)
            binding.profileEventsRecyclerView.apply {
                adapter = rvAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val listener = DialogInterface.OnClickListener { dialog, element ->
            when(element) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.logoutUser()
                    findNavController().navigate(R.id.loginFragment2)
                    Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.cancel()
            }
        }
        val confirmationDialog = AlertDialog.Builder(requireContext())
            .setTitle("Выход")
            .setMessage("Вы действительно хотите выйти из аккаунта?")
            .setPositiveButton("Да", listener)
            .setNegativeButton("Нет", listener)
            .setCancelable(true)
            .setOnCancelListener { it.cancel() }
            .create()

        confirmationDialog.show()
    }

}