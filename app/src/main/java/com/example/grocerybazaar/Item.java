package com.example.grocerybazaar;

public class Item {
    private final String name;
    private final String price;
    private final int imageResId;

    public Item(String name, String price) {
        this(name, price, android.R.drawable.ic_menu_gallery);
    }

    public Item(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}