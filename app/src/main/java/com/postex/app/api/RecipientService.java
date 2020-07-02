package com.postex.app.api;

import com.postex.app.CorrespondenceData;
import com.postex.app.Recipient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipientService {
    //rota para cadastro
    @POST("/recipients")
    Call<Void> registerRecipient(@Body Recipient recipient);

    //rota editar
    @PUT("/recipients/{id}")
    Call<Void> updateRecipient(@Path("id") Integer id, @Body Recipient recipient, @Header("Authorization") String token);
    //rota para login
    @GET("/login")
    Call<Recipient> login(@Header("Authorization") String authHeader);

    //dados das correspondencias
    @GET("/correspondences")
    Call<CorrespondenceData> getCorrespondencesData(@Query("id_recipient") Integer id, @Header("Authorization") String token);
}
