package br.com.leandrofb.listrickmortycharacters.controller;

import android.util.Log;

import java.util.List;

import br.com.leandrofb.listrickmortycharacters.model.api.RestApiManager;
import br.com.leandrofb.listrickmortycharacters.model.api.RickMortyApi;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Character;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListCharacterController {

    private static final String TAG = ListCharacterController.class.getSimpleName();
    private CharacterCallbackListener mListener;
    private RestApiManager mApiManager;

    private List<Character> characterList;

    public ListCharacterController(CharacterCallbackListener listener) {
        mListener = listener;
        mApiManager = new RestApiManager();
    }

    public void startFetching(String characters) {

        RickMortyApi api = mApiManager.getApiService();

        Call<List<Character>> call = api.getCharacters(characters);

        mListener.onFetchProgress();

        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {

                if (response.isSuccessful()) {
                    characterList = response.body();
                }

                mListener.onFetchComplete(characterList);

            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {

                Log.d(TAG, "Error :: " + t.getMessage());
                mListener.onFetchFailed();

            }

        });



    }

    public interface CharacterCallbackListener {

        void onFetchProgress();

        void onFetchComplete(List<Character> characterList);

        void onFetchFailed();
    }


}
