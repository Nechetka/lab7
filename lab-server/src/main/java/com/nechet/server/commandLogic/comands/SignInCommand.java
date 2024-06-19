package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.databaseLogic.UserDAO;
import com.nechet.server.exception.UserNotFoundException;
import com.nechet.server.exception.WrongPasswordException;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

public class SignInCommand implements BaseCommand{
    private final String name = "sign_in";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription d) {
        try {
            UserDAO user = new UserDAO(Utils.getUrl(), Utils.getLogin(), Utils.getPassword());
            String[] args = d.getArgs().get(1).split(" ");
            user.authenticateUser(args[0],args[1]);
            result += "Пользователь успешно подключен";
        } catch (SQLException e){
            result += "Ошибка подключения к базе";
        }catch(WrongPasswordException e){
            result += "Введен неправильный пароль";
        }catch(UserNotFoundException e){
            result += "Такого пользователя нет в базе";
        }
    }
    @Override
    public String descr() {
        return "Входит под существующим логином пользователя, если такой логин есть в базе.";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
