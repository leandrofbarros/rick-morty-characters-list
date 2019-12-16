package br.com.leandrofb.listrickmortycharacters.model.api;

import java.util.List;

import br.com.leandrofb.listrickmortycharacters.model.pojo.Character;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Episodes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RickMortyApi {

    @GET("api/episode")
    Call<Episodes> getEpisodes();

    @GET("api/character/{characters}")
    Call<List<Character>> getCharacters(@Path("characters") String characters);

    @GET("api/character/{character}")
    Call<Character> getCharacter(@Path("character") String character);

}
