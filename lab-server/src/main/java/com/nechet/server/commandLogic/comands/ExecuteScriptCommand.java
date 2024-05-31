package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;

import java.nio.file.Path;
import java.util.ArrayDeque;

public class ExecuteScriptCommand implements BaseCommand {
    private final String name = "execute_script";
    private static ArrayDeque<Path> openScriptDeque = new ArrayDeque<>();
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription descr) {
        result+="Скрипт выполнен";
    }

    @Override
    public String descr() {
        return "Вызывает скрипт из файла";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
