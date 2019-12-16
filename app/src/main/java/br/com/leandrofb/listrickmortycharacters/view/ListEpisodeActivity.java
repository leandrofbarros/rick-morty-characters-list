package br.com.leandrofb.listrickmortycharacters.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.leandrofb.listrickmortycharacters.R;
import br.com.leandrofb.listrickmortycharacters.controller.ListEpisodeController;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Episode;


public class ListEpisodeActivity extends AppCompatActivity implements ListEpisodeController.EpisodeCallbackListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Episode> mEpisodeList = new ArrayList<>();
    private br.com.leandrofb.listrickmortycharacters.adapter.ListEpisodeAdapter mListEpisodeAdapter;
    private ListEpisodeController mController;
    private ProgressDialog pDialog;

    private final Pattern sPattern
            = Pattern.compile("^([A-Z]{0,2})?(\\d)?([A-Z-]{0,5})");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(ListEpisodeActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        mSwipeRefreshLayout = this.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mController.startFetching();
            }
        });


        mController = new ListEpisodeController(ListEpisodeActivity.this);
        mController.startFetching();

        ListEpisodeActivity.this.setTitle("Episode List");

    }

    private void configViews() {

        mRecyclerView = this.findViewById(R.id.list);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListEpisodeActivity.this));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        mListEpisodeAdapter = new br.com.leandrofb.listrickmortycharacters.adapter.ListEpisodeAdapter(mEpisodeList, this);
        mRecyclerView.setAdapter(mListEpisodeAdapter);

        pDialog.dismiss();


    }

    @Override
    public void onFetchProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchComplete(List<Episode> episodesList) {
        mEpisodeList = episodesList;
        configViews();
    }

    @Override
    public void onFetchFailed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("We are undergoing maintenance please try again later!").setCancelable(false);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


}
