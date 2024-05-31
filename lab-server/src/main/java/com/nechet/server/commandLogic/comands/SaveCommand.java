package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.fileManipulation.WriteJSONCollection;
import com.nechet.server.fileManipulation.WriteToObject;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.UserConsole;
import com.nechet.server.system.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class SaveCommand implements BaseCommand{
    private final String name = "save";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d) throws FileNotFoundException{
        TreeSet<SpaceMarine> coll = SpaceMarinesManager.getInstance().getCollection();
        WriteToObject<TreeSet<SpaceMarine>> saver = new WriteJSONCollection<>();
        String path = System.getenv(Utils.getEnv());
        try {
            saver.write(path, coll);
            result+="Файл сохранен";
        } catch (IOException e){
            result+="Неопознанная ошибка работы с файлами. Проверьте права доступа к файлу и повторите.\n"+"Ошибка: "+e.getMessage();
        }

    }

    @Override
    public String descr() {
        return "Сохраняет коллекцию в файл";
    }

    @Override
    public String getName() {
        return name;
    }
}
