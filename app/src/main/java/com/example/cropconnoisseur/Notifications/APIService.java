package com.example.cropconnoisseur.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA7wNv5Zo:APA91bFr8AWLN0L_cwxJPQLa8QYnb-LsgkMIYsL_cCPTDpo9_3EhSWPYK0X2cd3dmRnxXm3WphBZ_nygUeX1ST-rKX7Do-dQcNUk0TQ_lkxW09usRw6bV42R0WBRk5Z_x8JI1hDFsNi7"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
