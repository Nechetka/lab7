package com.nechet.client.system;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;

public class Utils {
    static public String isEmptyLine(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            return line;
        }
    }

    public static void checkArgumentsOrThrow(int given, int needed) throws WrongValuesOfCommandArgumentException {
        if (given - 1 != needed)
            throw new WrongValuesOfCommandArgumentException("Вы ввели " + (given - 1) + " аргументов комманды, а надо " + needed);
    }

}

