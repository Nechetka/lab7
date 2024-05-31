package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.commandLogic.ServerCommandManager;

public class HelpCommand implements BaseCommand{
    private final String name = "help";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription d) {
        ServerCommandManager manager = new ServerCommandManager();
        manager.getAllCommands().forEach((name,command) -> result+=name  + ": " + command.descr()+"\n");
    }

    @Override
    public String descr() {
        return "Показывает описание для каждой команды";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
