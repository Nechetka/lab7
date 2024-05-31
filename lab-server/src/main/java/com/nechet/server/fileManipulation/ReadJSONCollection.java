package com.nechet.server.fileManipulation;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.server.system.UserConsole;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.TreeSet;

public class ReadJSONCollection implements ReadFromFileObject<TreeSet<SpaceMarine>> {
    @Override
    public TreeSet<SpaceMarine> read(String path) {
        Gson gson = new Gson();
        Type type = new TypeToken<TreeSet<SpaceMarine>>() {
        }.getType();
        UserConsole console = new UserConsole(new Scanner(System.in));
        console.printLine(path);
        TreeSet<SpaceMarine> marines = new TreeSet<SpaceMarine>();
        try {
            Reader file = new FileReader(path);
            marines = gson.fromJson(file, type);
            return marines;
        } catch (FileNotFoundException e) {
            console.printLine("Предыдущий файл не был найден или его вообще не существовало.");
            return marines;
        } catch (JsonSyntaxException e){
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            try {
                f.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            console.printLine("Предыдущий файл не правильно записан в Json. Он удален и создан новый");
            return marines;
        }
    }
}
