package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mIngredientsTv;
    private TextView mOriginTv;
    private TextView mAkaTv;
    private TextView mDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mAkaTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);

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
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        ingredientsIv.setVisibility(View.GONE);
                    }
                });

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Append ingredients from the Sandwich object into the ingredients text view
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            mIngredientsTv.append(sandwich.getIngredients().get(i));
            if (i+2 < sandwich.getIngredients().size()){
                mIngredientsTv.append(", ");
            } else if (i+2 == sandwich.getIngredients().size()){
                mIngredientsTv.append(" and ");
            }
        }

        //Set the place of origin from our Sandwich object into the text view
        mOriginTv.setText(sandwich.getPlaceOfOrigin());

        //Append AKAs from our Sandwich object into the proper text view
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            mAkaTv.append(sandwich.getAlsoKnownAs().get(i));
            if (i+2 < sandwich.getAlsoKnownAs().size()){
                mAkaTv.append(", ");
            } else if (i+2 == sandwich.getAlsoKnownAs().size()){
                mAkaTv.append(" or ");
            }
        }

        //Set the description from our Sandwich object into the proper text view
        mDescriptionTv.setText(sandwich.getDescription());
    }
}
