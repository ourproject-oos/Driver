package com.example.driver.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAApGdJth8:APA91bHPbZYEoFashNDN_s7mLkDXMKtxsHZ09Lc1kmLWY99T2iwrUs8xADQATNY-Loa8C-WpGSWVLWFnRy2M0Bhg8SEz8CkahdzFQEc6jWKxUU-jtO-42K--F2ZqMSR-j8JgqZsExgkq"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
