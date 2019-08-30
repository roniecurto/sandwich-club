package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            Sandwich sandwich;

            JSONObject sandwichJson = new JSONObject(json);

            JSONObject sandwichNameJsonObject = sandwichJson.getJSONObject("name");
            String sandwichName = sandwichNameJsonObject.getString("mainName");
            JSONArray sandwichAliases = sandwichNameJsonObject.getJSONArray("alsoKnownAs");

            String sandwichPlaceOrigin = sandwichJson.getString("placeOfOrigin");
            String sandwichDescription = sandwichJson.getString("description");
            String sandwichImage = sandwichJson.getString("image");

            JSONArray sandwichIngredients = sandwichJson.getJSONArray("ingredients");

            List<String> ingredients = new ArrayList<String>();
            if(sandwichIngredients.length() > 0) {
                for (int i = 0; i < sandwichIngredients.length(); i++) {
                    ingredients.add(sandwichIngredients.getString(i));
                }
            }

            List<String> aliases = new ArrayList<String>();
            if(sandwichAliases.length() > 0){
                for (int i = 0; i < sandwichAliases.length(); i++) {
                    aliases.add(sandwichAliases.getString(i));
                }
            }

            sandwich = new Sandwich(sandwichName, aliases, sandwichPlaceOrigin, sandwichDescription, sandwichImage, ingredients);
            return sandwich;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
