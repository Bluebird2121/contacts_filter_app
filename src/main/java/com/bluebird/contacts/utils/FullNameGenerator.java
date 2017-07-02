package com.bluebird.contacts.utils;

import java.util.Random;

public class FullNameGenerator {

    private static final String[] BEGIN_PARTS = { "Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk" };
    private static final String[] MIDDLE_PARTS = { "air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak" };
    private static final String[] END_PARTS = { "d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur" };

    private static Random random = new Random();

    public static String generate() {
        return firstName() + ' ' + lastName();
    }

    private static String firstName() {
        return BEGIN_PARTS[random.nextInt(BEGIN_PARTS.length)] +
                END_PARTS[random.nextInt(END_PARTS.length)];
    }

    private static String lastName() {
        return BEGIN_PARTS[random.nextInt(BEGIN_PARTS.length)] +
                MIDDLE_PARTS[random.nextInt(MIDDLE_PARTS.length)] +
                END_PARTS[random.nextInt(END_PARTS.length)];
    }
}
