package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.requestLogic.Command;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.common.util.requestLogic.RequestArgumentType;
import com.nechet.server.databaseLogic.UserDAO;
import com.nechet.server.exception.UserNotFoundException;
import com.nechet.server.exception.WrongPasswordException;
import com.nechet.server.system.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpCommand implements BaseCommand{
    private final String name = "sign_up";
    private String result = "";
    @Override
    public void execute(CommandDescription d) throws WrongValuesOfCommandArgumentException, IOException {
        try {
            UserDAO user = new UserDAO(Utils.getUrl(), Utils.getLogin(), Utils.getPassword());
            String[] args = d.getArgs().get(1).split(" ");
            if(user.addUser(args[0],args[1])){
                result += "Пользователь успешно подключен";
            } else {
                result +="Пользователь с таким именем уже существует";
            }
        } catch (SQLException e){
            result += "Ошибка работы с базой"+e.getMessage();
        }
    }
    @Override
    public String getResult(){
            return result;
    }
    @Override
    public String descr() {
        return "Регистрирует нового пользователя, если такого логина нет в базе.";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
