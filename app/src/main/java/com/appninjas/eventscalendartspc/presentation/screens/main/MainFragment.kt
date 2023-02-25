package com.appninjas.eventscalendartspc.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.model.Event
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentMainBinding
import com.appninjas.eventscalendartspc.presentation.adapters.EventsAdapter
import com.google.android.material.navigation.NavigationView


// TODO(Слушатель нажатий на сохранение эвента) +
// TODO(Админский функционал)
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initializeToolbar()
    }

    private fun initializeToolbar() {
        val activity: AppCompatActivity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.show()
        activity.supportActionBar?.title = "Мероприятия ТСПК"
        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) {user ->
            viewModel.getEventsList()
            viewModel.eventsList.observe(viewLifecycleOwner) { eventsMap ->
                binding.incomingEventsTextView.text = "Предстоящие (${eventsMap["active"]?.size})"
                binding.endedEventsTextView.text = "Завершенные (${eventsMap["ended"]?.size})"
                if (user.isAdmin) {
                    val activityNavView: NavigationView =
                        activity.findViewById(R.id.main_fragment_nav_menu)
                    activityNavView.menu.clear()
                    activityNavView.inflateMenu(R.menu.admin_nav_menu)
                    activityNavView.setCheckedItem(R.id.nav_menu_btn)
                    initializeEventsRv(true, eventsMap)
                } else {
                    initializeEventsRv(false, eventsMap)
                }
            }
        }
    }

    private fun initializeEventsRv(isUserAdmin: Boolean, eventsMap: Map<String, List<Event>>) {
        val adapterActive = EventsAdapter(eventsMap["active"]!!, isUserAdmin, listener = onAddButtonClickListener)
        val adapterEnded = EventsAdapter(eventsMap["ended"]!!, isUserAdmin, listener = onAddButtonClickListener)
        binding.incomingEventsRv.apply {
            adapter = adapterActive
            layoutManager = LinearLayoutManager(context)
        }
        binding.endedEventsRv.apply {
            adapter = adapterEnded
            layoutManager = LinearLayoutManager(context)
        }
    }

    private val onAddButtonClickListener = object : EventsAdapter.EventClickListener {
        override fun onAddEventBtnClick(model: Event) {
            viewModel.addEventToStorage(model)
            Toast.makeText(context, "Событие отмечено как Посещенное", Toast.LENGTH_SHORT).show()
        }

        override fun onEndEventBtnClick(model: Event) {
            viewModel.endEvent(model)
            Toast.makeText(context, "Статус события изменен на Завершено", Toast.LENGTH_SHORT).show()
            viewModel.getEventsList()
            viewModel.eventsList.observe(viewLifecycleOwner) {events ->
                initializeEventsRv(true, events)
            }
        }
    }

}