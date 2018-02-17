package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String JSON_NAME_KEY = "name";
    public static final String JSON_MAIN_NAME_KEY = "mainName";
    public static final String JSON_AKA_KEY = "alsoKnownAs";
    public static final String JSON_ORIGIN_KEY = "placeOfOrigin";
    public static final String JSON_DESC_KEY = "description";
    public static final String JSON_IMG_KEY = "image";
    public static final String JSON_INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            //Initialize our json object from our json String
            JSONObject jsonSandwidch = new JSONObject(json);

            //Check if json key exists
            if (jsonSandwidch.has(JSON_NAME_KEY)) {
                //Get the name into a json object
                JSONObject name = jsonSandwidch.getJSONObject(JSON_NAME_KEY);
                String mainName = name.getString(JSON_MAIN_NAME_KEY);
                JSONArray jsonAlsoKnownAsArray = name.getJSONArray(JSON_AKA_KEY);
                List<String> alsoKnownAsList = new ArrayList<>();
                for (int i = 0; i < jsonAlsoKnownAsArray.length(); i++) {
                    alsoKnownAsList.add(jsonAlsoKnownAsArray.getString(i));
                }

                //Get placeOfOrigin, description, image and ingredients from main json object
                String placeOfOrigin = jsonSandwidch.getString(JSON_ORIGIN_KEY);
                String description = jsonSandwidch.getString(JSON_DESC_KEY);
                String image = jsonSandwidch.getString(JSON_IMG_KEY);

                JSONArray jsonIngredientsArray = jsonSandwidch.getJSONArray(JSON_INGREDIENTS_KEY);
                List<String> ingredientsList = new ArrayList<>();
                for (int i = 0; i < jsonIngredientsArray.length(); i++) {
                    ingredientsList.add(jsonIngredientsArray.getString(i));
                }

                //Return a new Sandwich object with the data obtained from our json
                return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
