package com.example.grocerybazaar;

import android.os.Bundle;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            );
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_product_list);

        findViewById(R.id.backButton)
                .setOnClickListener(v -> finish());

        String category = getIntent().getStringExtra("category");
        if (category == null) category = "";

        TextView title = findViewById(R.id.categoryTitle);
        title.setText(category);

        RecyclerView recyclerView =
                findViewById(R.id.productRecyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerView.setAdapter(
                new ItemAdapter(getProducts(category)));
    }

    private List<Item> getProducts(String category) {
        List<Item> items = new ArrayList<>();

        switch (category) {
            case "Fruits":
                items.add(new Item("Apple", "₹120/kg", R.drawable.apple));
                items.add(new Item("Banana", "₹50/dozen", R.drawable.banana));
                items.add(new Item("Grapes", "₹90/kg", R.drawable.grapes));
                items.add(new Item("Guava", "₹60/kg", R.drawable.guava));
                items.add(new Item("Mango", "₹150/kg", R.drawable.mango));
                break;

            case "Vegetables":
                items.add(new Item("Tomato", "₹40/kg", R.drawable.tomato));
                items.add(new Item("Carrot", "₹60/kg", R.drawable.carrot));
                items.add(new Item("Beetroot", "₹50/kg", R.drawable.beetroot));
                items.add(new Item("Capsicum", "₹80/kg", R.drawable.capsicum));
                items.add(new Item("Onion", "₹35/kg", R.drawable.onion));
                break;

            case "Breads":
                items.add(new Item("Baguettes", "₹100/pc", R.drawable.baguettes));
                items.add(new Item("Challah", "₹180/pc", R.drawable.challah));
                items.add(new Item("Focaccia", "₹200/pc", R.drawable.focaccia));
                items.add(new Item("Pita", "₹80/pack", R.drawable.pita));
                items.add(new Item("Rye Bread", "₹160/loaf", R.drawable.rye_bread));
                break;

            case "Beverages":
                items.add(new Item("Coffee", "₹120/pack", R.drawable.coffee));
                items.add(new Item("Tea", "₹100/pack", R.drawable.tea));
                items.add(new Item("Energy Drinks", "₹110/can", R.drawable.energy_drinks));
                items.add(new Item("Hot Chocolate", "₹150/pack", R.drawable.hot_chocolate));
                break;

            case "Milk":
                items.add(new Item("Milk", "₹60/L", R.drawable.milk));
                items.add(new Item("Yogurt", "₹40/cup", R.drawable.yogurt));
                items.add(new Item("Butter", "₹60/100g", R.drawable.butter));
                items.add(new Item("Curd", "₹40/500g", R.drawable.curd));
                items.add(new Item("Ghee", "₹350/500ml", R.drawable.ghee));
                break;

            case "Snacks":
                items.add(new Item("Kachori", "₹25/pc", R.drawable.kachori));
                items.add(new Item("Pani Puri", "₹40/plate", R.drawable.pani_puri));
                items.add(new Item("Potato Chips", "₹20/pack", R.drawable.potato_chips));
                items.add(new Item("Samosa", "₹20/pc", R.drawable.samosa));
                break;
        }

        return items;
    }

    private void addNames(List<Item> items, String... names) {
        for (String name : names) {
            items.add(new Item(name, "Price not available"));
        }
    }
}