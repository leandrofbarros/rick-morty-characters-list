package br.com.leandrofb.listrickmortycharacters.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.leandrofb.listrickmortycharacters.model.api.RestApiManager;
import br.com.leandrofb.listrickmortycharacters.model.api.RickMortyApi;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Episode;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Episodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListEpisodeController {

    private static final String TAG = ListEpisodeController.class.getSimpleName();
    private EpisodeCallbackListener mListener;
    private RestApiManager mApiManager;

    private ArrayList<Episode> episodesList;

    public ListEpisodeController(EpisodeCallbackListener listener) {
        mListener = listener;
        mApiManager = new RestApiManager();
    }

    public void startFetching() {

        RickMortyApi api = mApiManager.getApiService();

        Call<Episodes> call = api.getEpisodes();

        mListener.onFetchProgress();

        call.enqueue(new Callback<Episodes>() {
            @Override
            public void onResponse(Call<Episodes> call, Response<Episodes> response) {

                if (response.isSuccessful()) {
                    episodesList = response.body().getResults();
                    mListener.onFetchComplete(episodesList);

                    if (episodesList.size() > 0) {
                        for (Episode e : episodesList) {

                            ArrayList<String> characters = new ArrayList<>();

                            for (String s : e.getCharacters()) {
                                characters.add(s.replaceAll("\\D+", ""));
                            }

                            e.setCharacters(characters);
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<Episodes> call, Throwable t) {
                Log.d(TAG, "Error :: " + t.getMessage());
                mListener.onFetchFailed();
            }


        });

    }

    public interface EpisodeCallbackListener {

        void onFetchProgress();

        void onFetchComplete(List<Episode> episodeList);

        void onFetchFailed();
    }


}
