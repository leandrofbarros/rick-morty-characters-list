package br.com.leandrofb.listrickmortycharacters.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Episodes {

    @SerializedName("results")
    @Expose
    private ArrayList<Episode> results = null;

    public ArrayList<Episode> getResults() {
        return results;
    }

    public void setResults(ArrayList<Episode> results) {
        this.results = results;
    }

}