package com.example.bthechef;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private EditText editText;
    TextView listOfIngredients;
    private String[] allIngredients;
    // helps to remove the commas the user inserts
    String delimiter = "[,][, ]";


    private Toolbar mtoolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // call function for toolbar crashing the app.
//        mtoolBar = findViewById(R.id.toolBar) ;
//        setSupportActionBar(mtoolBar);
        listOfIngredients = findViewById(R.id.listOfIngredsTView);
        editText = findViewById(R.id.ingredEditText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // first, empty the array
                allIngredients = null;
                String userInput = editText.getText().toString();
                allIngredients = userInput.split(delimiter);
                listOfIngredients.setText("Your ingredients:" + printAllIngredients(allIngredients));
            }

        });

        Button aboutAppButton = findViewById(R.id.aboutAppBtn);
        aboutAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick function start");
                //Toast.makeText(this, "Works!", Toast.LENGTH_SHORT);
                showAboutUsPopUp();
            }
        });

        Button shareIngredientsBtn = findViewById(R.id.shareIngredBtn);
        shareIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Sharing ingredients btn pressed");
                if (editText.getText().toString().matches("")){
                    Log.d(TAG, "user has not entered any ingredients");
                }
                else{
                    Log.d(TAG, editText.getText().toString());
                    sendText();
                }
            }
        });
    }

    private void setSupportActionBar(Toolbar toolBar) {


    }

    // returns a String containing the ingredients user enters
    public String printAllIngredients(String[] array){
        String temp = "";
        for (int i = 0; i < array.length; ++i){
            temp += "\n- " + array[i];
        }
        return temp;
    }

    // makes an Intent to search the web for the ingredients
    // the user enters
    public void searchFood(View view) {
        Log.d(TAG, "inside searchFood");
        String searchQuery = "";
        // add every item in the allIngredients array to searchQuery
        for (int i = 0; i < allIngredients.length; ++i){
            searchQuery += allIngredients[i] + " ";
        }
        // finally add the word 'recipes' to complete the search query
        searchQuery += "recipes";
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, searchQuery);
        if (searchQuery != ""){
            startActivity(intent);
        }
        Log.d(TAG, "end of searchFood");
    }

    public void showAboutUsPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About BTheChef");
        builder.setMessage("Ever wanted to cook something nice, but looked in your pantry only to find scant ingredients?\n\nYou can still cook fine meals thanks to \'BTheChef\'.\n\nBTheChef: Allowing you to...well it's in the title ;)\n\nBTheChef was developed by: Zaheen Shahriyar, Muzzafar Ahmed, Andrew Ohakam, and Elizabeth Bocs\n");
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void sendText(){
        Log.d(TAG, "sendText started");
        String textToSend = "From BTheChef:\nRecipes containing ";
        String searchURL = "https://www.google.com/search?q=";
        for (int i = 0; i < allIngredients.length; ++i){
            textToSend += allIngredients[i] + " ";
            searchURL += allIngredients[i] + "+";
        }
        searchURL += "recipes";
        textToSend += ": " + searchURL;
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Send a Text")
                .setText(textToSend)
                .startChooser();
        Log.d(TAG, "sendText ended");
    }
}