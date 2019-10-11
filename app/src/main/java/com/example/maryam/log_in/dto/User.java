package com.example.maryam.log_in.dto;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by maryam on 9/2/19.
 */

public class User extends RealmObject implements Parcelable{
    public User() {
    }
    @PrimaryKey
    private String id;
    private String username;
    private String lastName;
    private String firstName;
    private String password;

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String id, String username, String lastName, String firstName, String password) {
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(lastName);
        parcel.writeString(firstName);
        parcel.writeString(password);
    }
}
