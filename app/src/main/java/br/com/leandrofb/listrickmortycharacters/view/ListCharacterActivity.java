package br.com.leandrofb.listrickmortycharacters.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.leandrofb.listrickmortycharacters.R;
import br.com.leandrofb.listrickmortycharacters.controller.ListCharacterController;
import br.com.leandrofb.listrickmortycharacters.model.adapter.ListCharacterAdapter;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Character;

public class ListCharacterActivity extends AppCompatActivity implements ListCharacterController.CharacterCallbackListener {

    private ListCharacterController mController;
    private ProgressDialog pDialog;
    private List<Character> mCharacterList = new ArrayList<>();
    private ListCharacterAdapter mListCharacterAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pDialog = new ProgressDialog(ListCharacterActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        mController = new ListCharacterController(ListCharacterActivity.this);

        Intent intent = getIntent();
        String characters = intent.getStringExtra("CHARACTERS");
        String episode = intent.getStringExtra("EPISODE");

        mController.startFetching(characters);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListCharacterActivity.this.setTitle(episode+" - Character List");

    }


    private void configViews() {

        mRecyclerView = this.findViewById(R.id.listCharacter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListCharacterActivity.this));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        mListCharacterAdapter = new ListCharacterAdapter(mCharacterList, this);
        mRecyclerView.setAdapter(mListCharacterAdapter);

        pDialog.dismiss();

    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public void onFetchComplete(List<Character> characterList) {
        mCharacterList = characterList;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
