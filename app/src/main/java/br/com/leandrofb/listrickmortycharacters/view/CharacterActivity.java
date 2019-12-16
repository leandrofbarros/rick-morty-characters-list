package br.com.leandrofb.listrickmortycharacters.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.leandrofb.listrickmortycharacters.R;
import br.com.leandrofb.listrickmortycharacters.controller.CharacterController;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Character;
import br.com.leandrofb.listrickmortycharacters.model.utilities.DownloadImageTask;

public class CharacterActivity extends AppCompatActivity implements CharacterController.CharacterCallbackListener {

    final String TAG = CharacterActivity.class.getSimpleName();

    private CharacterController mController;
    private ProgressDialog pDialog;
    private Character mCharacter = new Character();

    public ImageView imageView;
    public TextView name;
    public TextView status;
    public TextView specie;
    public TextView gender;
    public TextView origin;
    public TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        pDialog = new ProgressDialog(CharacterActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        mController = new CharacterController(CharacterActivity.this);

        Intent intent = getIntent();
        String character = intent.getStringExtra("CHARACTER");

        mController.startFetching(character);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CharacterActivity.this.setTitle("Character");

    }


    private void configViews() {


        this.imageView = findViewById(R.id.person_photo);
        this.name = findViewById(R.id.person_name);
        this.status = findViewById(R.id.person_status);
        this.specie = findViewById(R.id.person_specie);
        this.gender = findViewById(R.id.person_gender);
        this.origin = findViewById(R.id.person_origin);
        this.location = findViewById(R.id.person_location);


        new DownloadImageTask(imageView).execute(mCharacter.getImage());
        name.setText(mCharacter.getName());
        status.setText(mCharacter.getStatus());
        specie.setText(mCharacter.getSpecies());
        gender.setText(mCharacter.getGender());
        origin.setText(mCharacter.getOrigin().getName());
        location.setText(mCharacter.getLocation().getName());

        pDialog.dismiss();

    }

    @Override
    public void onFetchProgress() {
    }

    @Override
    public void onFetchComplete(Character character) {
        mCharacter = character;
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
