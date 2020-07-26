package src.core;

import retrofit.Call;
import retrofit.http.*;

public interface SuperService {

    @GET("ping/")
    Call<Object> ping();

    @POST("authorize/")
    @FormUrlEncoded
    Call<Object> authorize(@Field("username") String username, @Field("password") String password);

    @POST("api/save_data/")
    @FormUrlEncoded
    Call<Object> saveData(@Header("Authorization") String bearerToken, @Field("payload") String payload);

    @POST("api/save_data/")
    Call<Object> saveData(@Header("Authorization") String bearerToken);

    @POST("api/save_data/")
    @FormUrlEncoded
    Call<Object> saveDataWithoutAuthHeader(@Field("payload") String payload);
}
