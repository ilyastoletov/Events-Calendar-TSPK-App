package com.appninjas.data.network

import com.appninjas.data.network.model.Notification
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("fcm/send")
    @Headers("Authorization: key=AAAAjVcoxBo:APA91bHraWD2AcBczKi4BsoPkTyxFMnwEtBnyP0doMikXAqM0b2MPiM1KBUpNSqUBKQJ6o6KvrehmtgYCIIPCGCMs6-PfEqedaRfucKFQeb0fFKvsL6K42WL9iHq-h0V_d1Qs4Wy1gcN")
    suspend fun notifyUsers(@Body notificationBody: Notification)

}