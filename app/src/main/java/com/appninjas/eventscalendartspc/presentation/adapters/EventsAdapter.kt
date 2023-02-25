package com.appninjas.eventscalendartspc.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.Event
import com.appninjas.eventscalendartspc.databinding.EventItemBinding
import com.squareup.picasso.Picasso

class EventsAdapter(private val eventsList: List<Event>,
                    private val userAdmin: Boolean,
                    private val fromProfile: Boolean = false,
                    private val listener: EventClickListener?): RecyclerView.Adapter<EventsAdapter.Holder>() {

    inner class Holder(val binding: EventItemBinding, view: View): RecyclerView.ViewHolder(view) {
        fun bind(model: Event) {
            Picasso.get().load(model.eventPictureUrl).into(binding.eventPicture)
            with(binding) {
                eventHeading.text = model.eventName
                eventDescription.text = model.eventDescription
                eventInformation.text = "${model.date} • ${model.category} • ${model.status}"
                if (userAdmin) {
                    adminBtnEndEvent.visibility = View.VISIBLE
                }
                if (fromProfile) {
                    adminBtnEndEvent.visibility = View.GONE
                    eventBtnAddToVisited.visibility = View.GONE
                }
            }
            if(listener != null) {
                binding.eventBtnAddToVisited.setOnClickListener {
                    listener?.onAddEventBtnClick(model)
                }
                binding.adminBtnEndEvent.setOnClickListener {
                    listener?.onEndEventBtnClick(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(eventsList[position])
        if (position == eventsList.size - 1) {
            holder.binding.border.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = eventsList.size

    interface EventClickListener {
        fun onAddEventBtnClick(model: Event)
        fun onEndEventBtnClick(model: Event)
    }

}