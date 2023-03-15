package com.appninjas.eventscalendartspc.presentation.screens.send_notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appninjas.eventscalendartspc.databinding.FragmentNotificationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.sendNotificationButton.setOnClickListener {
            val titleTextField = binding.notificationTitleEditText.text
            val bodyTextField = binding.notificationBodyEditText.text
            if(titleTextField.toString().isNotEmpty() && bodyTextField.toString().isNotEmpty()) {
                viewModel.sendNotification(titleTextField.toString(), bodyTextField.toString())
                Toast.makeText(context, "Уведомление отправлено пользователям", Toast.LENGTH_SHORT).show()
                titleTextField.clear()
                bodyTextField.clear()
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

}