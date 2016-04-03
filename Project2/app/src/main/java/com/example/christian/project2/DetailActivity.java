package com.example.christian.project2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Christian on 4/2/16.
 */
public class DetailActivity extends AppCompatActivity {
    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mHelper = DatabaseHelper.getInstance(DetailActivity.this);

        final int id = getIntent().getIntExtra("id", -1);

        Log.d("DetailActivity", "ID passed in: " + id);

        if (id >= 0) {
            String [] description = DatabaseHelper.getInstance(DetailActivity.this).getDescriptionById(id);
            //getFavoritedById
//            String locationName = description[0];

//            ImageView imageView = (ImageView) findViewById(R.id.image_view);
//            imageView.setImageResource(getDrawableValue(locationName));

            final CheckBox favoriteButton = (CheckBox) findViewById(R.id.favorite_button);
            favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (favoriteButton.isChecked()) {
//                       favoriteButton.setChecked(true);
                        mHelper.updateAddFavorite(id, 1);
                    }
                }
            });

            TextView textView = (TextView) findViewById(R.id.description);
            textView.setText(description[0]);

            TextView textView2 = (TextView) findViewById(R.id.price);
            textView2.setText(description[1]);

            TextView textView3 = (TextView) findViewById(R.id.type);
            textView3.setText(description[2]);

        }

    }

}