package com.example.maryam.log_in.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maryam on 9/9/19.
 */

public class Item implements Parcelable {
    public Item() {
    }

    private String id;
    private String name;
    private int quantity;
    private double price;


    protected Item(Parcel in) {
        id = in.readString();
        name = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(quantity);
        parcel.writeDouble(price);
    }
}
