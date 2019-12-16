package br.com.leandrofb.listrickmortycharacters.model.api;

import br.com.leandrofb.listrickmortycharacters.model.utilities.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiManager {

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static br.com.leandrofb.listrickmortycharacters.model.api.RickMortyApi getApiService() {
        return getRetrofitInstance().create(br.com.leandrofb.listrickmortycharacters.model.api.RickMortyApi.class);
    }

}
