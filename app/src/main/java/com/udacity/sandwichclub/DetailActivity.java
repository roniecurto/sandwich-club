package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView sandwichOriginTextView = (TextView) findViewById(R.id.origin_tv);
        sandwichOriginTextView.setText(sandwich.getPlaceOfOrigin());

        TextView aliasTextView = (TextView) findViewById(R.id.also_known_tv);
        List<String> sandwichAliases = sandwich.getAlsoKnownAs();
        if(sandwichAliases.size() > 0) {
            for (int i = 0; i < sandwichAliases.size(); i++) {
                if(i == sandwichAliases.size() - 1) {
                    aliasTextView.append(sandwichAliases.get(i));
                }else{
                    aliasTextView.append(sandwichAliases.get(i) + ", ");
                }
            }
        }else{
            aliasTextView.setText("No values found");
        }

        TextView sandwichDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        sandwichDescriptionTextView.setText(sandwich.getDescription());

        TextView sandwichIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        List<String> sandwichIngredients = sandwich.getIngredients();
        if(sandwichIngredients.size() > 0) {
            for (int i = 0; i < sandwichIngredients.size(); i++) {
                if(i == sandwichIngredients.size() - 1) {
                    sandwichIngredientsTextView.append(sandwichIngredients.get(i));
                }else{
                    sandwichIngredientsTextView.append(sandwichIngredients.get(i) + ", ");
                }
            }
        }else{
            sandwichIngredientsTextView.setText("No ingredients found");
        }
    }
}
