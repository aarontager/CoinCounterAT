package com.mcon521.coincounterat.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcon521.coincounterat.R;
import com.mcon521.coincounterat.classes.CoinCounter;
import com.mcon521.coincounterat.classes.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CoinCounter mCoinCounter;
    private EditText etPennies,
            etNickels,
            etDimes,
            etQuarters;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupFAB();
        setupCoinCounterVariables();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCoinsCount();
                updateStatusBar();
            }
        });
    }

    private void setupCoinCounterVariables() {
        mCoinCounter = new CoinCounter();
        etPennies = findViewById(R.id.edit_text_pennies);
        etNickels = findViewById(R.id.edit_text_nickles);
        etDimes = findViewById(R.id.edit_text_dimes);
        etQuarters = findViewById(R.id.edit_text_quarters);
        tvStatus = findViewById(R.id.text_view_status);
    }

    private void setCoinsCount() {
        mCoinCounter.setCountOfPennies(etPennies.getText().toString());
        mCoinCounter.setCountOfNickels(etNickels.getText().toString());
        mCoinCounter.setCountOfDimes(etDimes.getText().toString());
        mCoinCounter.setCountOfQuarters(etQuarters.getText().toString());
    }

    private void updateStatusBar() {
        String total = "Total in cents: " + mCoinCounter.getCentsValueTotal();
        tvStatus.setText(total);
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
        if (id == R.id.action_clear_all) {
            return true;
        }
        else if(id == R.id.action_about) {
            Utils.showInfoDialog (MainActivity.this,
                    R.string.action_About, R.string.about_text);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CoinCounter", CoinCounter.getJSONStringFrom(mCoinCounter));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCoinCounter = CoinCounter.getCoinCounterObjectFromJSONString(savedInstanceState.getString("COINCOUNTER"));
        loadValues();
    }

    private void loadValues() {
        etPennies.setText(mCoinCounter.getCountOfPennies());
        etNickels.setText(mCoinCounter.getCountOfNickels());
        etDimes.setText(mCoinCounter.getCountOfDimes());
        etQuarters.setText(mCoinCounter.getCountOfQuarters());
        updateStatusBar();
    }
}