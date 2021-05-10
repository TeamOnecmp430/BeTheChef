package com.example.bthechef;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private EditText editText;
    private Button addIngredientBtn;
    TextView listOfIngredients;
    private String[] allIngredients;
    String delimiter = "[,][, ]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                allIngredients = null;
                String userInput = editText.getText().toString();
                allIngredients = userInput.split(delimiter);
                listOfIngredients.setText("Your ingredients:" + getAllIngredients(allIngredients));
            }
        });
        //addIngredientBtn = findViewById(R.id.addIngredBtn);
    }

    public String getAllIngredients(String[] array){
        String temp = "";
        for (int i = 0; i < array.length; ++i){
            temp += "\n- " + array[i];
        }
        return temp;
    }

    public void searchFood(View view) {
        Log.d(TAG, "inside searchFood");
        String userInput = editText.getText().toString();
        String searchQuery = "";
        for (int i = 0; i < allIngredients.length; ++i){
            searchQuery += allIngredients[i] + " ";
        }

        searchQuery += "recipes";
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, searchQuery);
        if (searchQuery != ""){
            startActivity(intent);
        }
        Log.d(TAG, "end of searchFood");
    }
}