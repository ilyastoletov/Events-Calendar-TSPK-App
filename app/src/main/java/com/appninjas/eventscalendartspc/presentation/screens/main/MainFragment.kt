package com.appninjas.eventscalendartspc.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.model.Event
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentMainBinding
import com.appninjas.eventscalendartspc.presentation.adapters.EventsAdapter


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val eventsTestList: List<Event> = listOf(
            Event("https://avatars.mds.yandex.net/i?id=8e42b9b8fb7261b0a579f19d31702073f4a977b4-8498221-images-thumbs&n=13", "Пончики захватили шарагу!", "Сенсационная хуйня! Всем срочно спрыгнуть с крыши", "22 февраля 2023", "Пончики", "Не завершено"),
            Event("https://avatars.mds.yandex.net/i?id=8e42b9b8fb7261b0a579f19d31702073f4a977b4-8498221-images-thumbs&n=13", "Пончики захватили шарагу!", "Сенсационная хуйня! Всем срочно спрыгнуть с крыши", "22 февраля 2023", "Пончики", "Не завершено")
        )
        val secondEventsListText: List<Event> = listOf(
            Event("https://avatars.mds.yandex.net/i?id=8e42b9b8fb7261b0a579f19d31702073f4a977b4-8498221-images-thumbs&n=13", "Пончики захватили шарагу!", "Сенсационная хуйня! Всем срочно спрыгнуть с крыши", "22 февраля 2023", "Пончики", "Не завершено"),
            Event("https://avatars.mds.yandex.net/i?id=8e42b9b8fb7261b0a579f19d31702073f4a977b4-8498221-images-thumbs&n=13", "Пончики захватили шарагу!", "Сенсационная хуйня! Всем срочно спрыгнуть с крыши", "22 февраля 2023", "Пончики", "Не завершено")
        )
        val adapter1 = EventsAdapter(eventsTestList)
        val adapter2 = EventsAdapter(secondEventsListText)
        binding.incomingEventsRv.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(context)
        }
        binding.endedEventsRv.apply {
            adapter = adapter2
            layoutManager = LinearLayoutManager(context)
        }
    }

}