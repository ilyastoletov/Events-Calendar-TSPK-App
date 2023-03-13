package com.appninjas.data.network.model

import com.google.gson.annotations.Expose

data class Notification(
    @Expose
    val to: String,
    @Expose
    val notification: NotificationParams
)
