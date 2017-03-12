package com.example.azfardaher.cw1_w1579033;


/**
 * Created by azfardaher on 09/03/2017.
 */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button playBtn, aboutBtn, exitBtn, continueBtn;
    private String[] levelNames = {"Novice", "Easy", "Medium", "Guru"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        playBtn = (Button)findViewById(R.id.play_btn);
        aboutBtn = (Button)findViewById(R.id.about_btn);
        exitBtn = (Button)findViewById(R.id.exit_btn);
        continueBtn = (Button)findViewById(R.id.continue_btn);
        playBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        continueBtn.setOnClickListener(this);


    }


    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a level")
                .setSingleChoiceItems(levelNames, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startPlay(which);
                    }
                });
        if(view.getId()==R.id.play_btn){
            AlertDialog ad = builder.create();
            ad.show();
        }
        else if(view.getId()==R.id.about_btn){
            Intent helpIntent = new Intent(this, About.class);
            this.startActivity(helpIntent);
        }
        else if(view.getId()==R.id.exit_btn){
            Intent helpIntent = new Intent(this, Exit.class);
            this.startActivity(helpIntent);
        }

        else if(view.getId()==R.id.continue_btn){
            Intent helpIntent = new Intent(this, Continue.class);
            this.startActivity(helpIntent);
        }

    }
    private void startPlay(int chosenLevel)
    {
        Intent playIntent = new Intent(this, PlayGame.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
