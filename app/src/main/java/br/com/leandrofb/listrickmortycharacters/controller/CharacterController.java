package br.com.leandrofb.listrickmortycharacters.controller;

import android.util.Log;

import br.com.leandrofb.listrickmortycharacters.model.api.RestApiManager;
import br.com.leandrofb.listrickmortycharacters.model.api.RickMortyApi;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Character;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CharacterController {

    private static final String TAG = CharacterController.class.getSimpleName();
    private CharacterCallbackListener mListener;
    private RestApiManager mApiManager;

    private Character character;

    public CharacterController(CharacterCallbackListener listener) {
        mListener = listener;
        mApiManager = new RestApiManager();
    }

    public void startFetching(String c) {

        RickMortyApi api = mApiManager.getApiService();

        Call<Character> call = api.getCharacter(c);

        mListener.onFetchProgress();

        call.enqueue(new Callback<Character>() {


            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.isSuccessful()) {
                    character = response.body();
                }
                mListener.onFetchComplete(character);

            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d(TAG, "Error :: " + t.getMessage());
                mListener.onFetchFailed();
            }

        });



    }

    public interface CharacterCallbackListener {

        void onFetchProgress();

        void onFetchComplete(Character character);

        void onFetchFailed();
    }


}
