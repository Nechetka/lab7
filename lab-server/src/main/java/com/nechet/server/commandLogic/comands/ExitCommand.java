package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.requestLogic.CommandDescription;

import java.io.IOException;

public class ExitCommand implements BaseCommand{
    private final String name = "exit";
    private String result = "";

    public String getResult(){
        return result;
    }

        @Override
        public void execute(CommandDescription d) throws IOException {
        result+="клиент закрыт";
        }

        @Override
        public String descr() {
            return "Завершает работу клиента";
        }

        @Override
        public String getName() {
            return this.name;
        }

}
