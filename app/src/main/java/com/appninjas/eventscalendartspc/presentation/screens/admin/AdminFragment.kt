package com.appninjas.eventscalendartspc.presentation.screens.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.appninjas.domain.model.Event
import com.appninjas.eventscalendartspc.R
import com.appninjas.eventscalendartspc.databinding.FragmentAdminBinding
import kotlin.random.Random

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private val viewModel: AdminViewModel by viewModels { AdminViewModel.Factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val activity: AppCompatActivity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Добавить событие"
        binding.addEventBtn.setOnClickListener {
            val validationResult = validateFields()
            if (validationResult) {
                viewModel.addEvent(Event(
                    eventId = Random.nextInt(),
                    eventName = binding.eventNameEditText.text.toString(),
                    eventDescription = binding.eventDescriptionEditText.text.toString(),
                    eventPictureUrl = binding.eventImageEditText.text.toString(),
                    category = binding.eventCategorySpinner.selectedItem.toString(),
                    status = when(binding.eventConditionSpinner.selectedItem.toString()) {
                        "Завершено" -> "true"
                        "Не завершено" -> "false"
                        else -> "false"
                    },
                    date = binding.eventDateEditText.text.toString()
                ))
                Toast.makeText(context, "Событие успешно добавлено", Toast.LENGTH_SHORT).show()
                with(binding) {
                    eventNameEditText.text.clear()
                    eventDescriptionEditText.text.clear()
                    eventImageEditText.text.clear()
                    eventDateEditText.text.clear()
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val dateET = binding.eventDateEditText.text
        val descET = binding.eventDescriptionEditText.text
        val nameET = binding.eventNameEditText.text
        val picET = binding.eventImageEditText.text
        return if(dateET.isEmpty() || descET.isEmpty() || nameET.isEmpty() || picET.isEmpty()) {
            Toast.makeText(context, "Заполните все текстовые поля", Toast.LENGTH_SHORT).show()
            false
        } else if(binding.eventCategorySpinner.selectedItem.toString() == "Выберите категорию") {
            Toast.makeText(context, "Выберите категорию события", Toast.LENGTH_SHORT).show()
            false
        } else if(binding.eventConditionSpinner.selectedItem.toString() == "Выберите состояние") {
            Toast.makeText(context, "Выберите состояние события", Toast.LENGTH_SHORT).show()
            false
        } else if(!binding.eventImageEditText.text.toString().startsWith("https://", ignoreCase = false)) {
            Toast.makeText(context, "Введите правильную ссылку на превью", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

}