package src.core;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static src.core.ConfigParams.host;

public class Environment {

    private static SuperService service;

    public static synchronized SuperService getService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(host)
                    .build();
            service = retrofit.create(SuperService.class);
            return service;
        } else {
            return service;
        }
    }
}
