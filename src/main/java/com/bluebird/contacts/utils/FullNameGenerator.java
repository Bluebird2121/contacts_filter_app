package com.bluebird.contacts.utils;

import java.util.Random;

public class FullNameGenerator {

    private static String[] beginningParts = { "Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk" };
    private static String[] middleParts = { "air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak" };
    private static String[] endParts = { "d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur" };

    private static Random rand = new Random();

    public static String generate() {
        return firstName() + " " + lastName();
    }

    private static String firstName() {
        return beginningParts[rand.nextInt(beginningParts.length)] +
                endParts[rand.nextInt(endParts.length)];
    }

    private static String lastName() {
        return beginningParts[rand.nextInt(beginningParts.length)] +
                middleParts[rand.nextInt(middleParts.length)] +
                endParts[rand.nextInt(endParts.length)];
    }
}
