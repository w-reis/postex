package com.postex.app.api;

import com.postex.app.Recipient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RecipientService {
    @POST("/recipients")
    Call<Recipient> registerRecipient(@Body Recipient recipient);
}
