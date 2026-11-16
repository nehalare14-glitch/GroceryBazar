package com.example.grocerybazaar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private final String[] categories = {
        "Fruits", "Vegetables", "Breads",
        "Beverages", "Milk", "Snacks"
    };

    private final String[] symbols = {
        "\uD83C\uDF4E", "\uD83E\uDD55", "\uD83C\uDF5E",
        "\u2615", "\uD83E\uDD5B", "\uD83C\uDF7F"
    };

    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        GridLayout grid = findViewById(R.id.categoryGrid);

        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];

            TextView tile = new TextView(this);
            tile.setText(symbols[i] + "\n" + category);
            tile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            tile.setTypeface(null, Typeface.BOLD);
            tile.setTextColor(Color.rgb(21, 128, 61));
            tile.setGravity(Gravity.CENTER);
            tile.setPadding(dp(8), dp(18), dp(8), dp(18));
            tile.setMinHeight(dp(112));

            GradientDrawable background = new GradientDrawable();
            background.setColor(Color.WHITE);
            background.setCornerRadius(dp(16));
            background.setStroke(dp(1), Color.rgb(220, 235, 225));

            tile.setBackground(background);
            tile.setElevation(dp(2));

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = dp(120);
            params.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, 1f);

            params.setMargins(
                    dp(5), dp(5), dp(5), dp(5));

            grid.addView(tile, params);

            tile.setOnClickListener(v -> {
                Intent intent = new Intent(
                        this, ProductListActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            });
        }

        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }
}