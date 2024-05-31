package com.nechet.server.system;

import com.nechet.common.util.model.SpaceMarine;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.TreeSet;

public class Utils {
    static public String isEmptyLine(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            return line;
        }
    }
    private static String enviromentPath;

    private static long id = 0L;
    static public Long getNewId() {
        TreeSet<SpaceMarine> coll = SpaceMarinesManager.getInstance().getCollection();
        LinkedHashSet<Long> ids = new LinkedHashSet<>();
        coll.forEach(obj -> ids.add(obj.getId()));
        Random dice = new Random();
        id = dice.nextLong();
        while (ids.contains(id) | id < 0L) {
            id = dice.nextLong();
        }
        return id;
    }


    public static String getEnv() {
        return enviromentPath;
    }

    public static void setEnv(String enviromentPath) {
        Utils.enviromentPath = enviromentPath;
    }
}

