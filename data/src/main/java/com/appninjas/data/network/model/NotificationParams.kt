package com.appninjas.data.network.model

import com.google.gson.annotations.Expose

data class NotificationParams(
    @Expose
    val title: String,
    @Expose
    val body: String,
    @Expose
    val mutable_content: Boolean = true,
    @Expose
    val sound: String = "Tri-tone"
)
