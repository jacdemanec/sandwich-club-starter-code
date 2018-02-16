package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            //Initialize our json object from our json String
            JSONObject jsonSandwidch = new JSONObject(json);

            //Get the name into a json object
            JSONObject name = jsonSandwidch.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray jsonAlsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < jsonAlsoKnownAsArray.length(); i++) {
                alsoKnownAsList.add(jsonAlsoKnownAsArray.getString(i));
            }

            //Get placeOfOrigin, description, image and ingredients from main json object
            String placeOfOrigin = jsonSandwidch.getString("placeOfOrigin");
            String description = jsonSandwidch.getString("description");
            String image = jsonSandwidch.getString("image");

            JSONArray jsonIngredientsArray = jsonSandwidch.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < jsonIngredientsArray.length(); i++) {
                ingredientsList.add(jsonIngredientsArray.getString(i));
            }

            //Retrun a new Sandwich object with the data obtained from our json
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
