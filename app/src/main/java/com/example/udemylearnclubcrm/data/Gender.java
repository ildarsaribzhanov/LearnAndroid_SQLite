package com.example.udemylearnclubcrm.data;

public class Gender {
    private int gender;

    public Gender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        if (gender == 1) {
            return "Male";
        }

        if (gender == 22) {
            return "Female";
        }

        return "Unknown";
    }
}
