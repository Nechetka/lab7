package com.nechet.client.comands;

import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.exceptions.CommandManagerException;
import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.client.exceptions.UnknownCommandException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.requestLogic.CommandDescription;
import static com.nechet.common.util.requestLogic.RequestArgumentType.*;

import com.nechet.common.util.requestLogic.Command;
import com.nechet.common.util.requestLogic.Requests.AnswerRequests;
import com.nechet.common.util.requestLogic.Requests.CommandRequest;
import com.nechet.client.Creators.NonUSerCreators.SpaceMarineNonUserCreator;
import com.nechet.client.Creators.UserCreators.SpaceMarineCreator;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.client.system.UserConsole;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Scanner;

public class CommandManager {
    private LinkedHashMap<String, Command> commands;
    private Sender sender;
    private UserConsole console = new UserConsole(new Scanner(System.in));

    public CommandManager(Scanner scanner,Sender sender)
    {
        this.sender=sender;
        this.commands = new LinkedHashMap<>();

        commands.put("exit",new Command("exit",NO_ARGS));
        commands.put("help", new Command("help",NO_ARGS));
        commands.put("info", new Command("info",NO_ARGS));
        commands.put("show", new Command("show",NO_ARGS));
        commands.put("remove_by_id", new Command("remove_by_id",LONG));
        commands.put("clear", new Command("clear",NO_ARGS));
        commands.put("print_fields_ascending_health", new Command("print_fields_ascending_health",NO_ARGS));
        commands.put("filter_greater_than_weapon_type", new Command("filter_greater_than_weapon_type",WEAPON));
        commands.put("filter_by_health", new Command("filter_by_health",DOUBLE));

        BaseObjectUserCreator<SpaceMarine> marineCreator;
        if (UserConsole.whatsMode()) {
            marineCreator = new SpaceMarineCreator();
        } else {
            marineCreator = new SpaceMarineNonUserCreator(scanner);
        }
        commands.put("add", new Command("add",NO_ARGS,marineCreator,1));
        commands.put("add_if_max", new Command("add_if_max",NO_ARGS,marineCreator,1));
        commands.put("update", new Command("update",LONG,marineCreator,1));
        commands.put("remove_greater", new Command("remove_greater",NO_ARGS,marineCreator,1));
        commands.put("remove_lower", new Command("remove_lower",NO_ARGS,marineCreator,1));
        commands.put("execute_script",new ExecuteScriptCommand(sender));

    }
    public void executer(String line) throws CommandManagerException {
        try {
            String[] tokens = line.trim().split(" ");
            Command command = Optional.ofNullable(commands.get(tokens[0])).orElseThrow(() -> new UnknownCommandException("Указанная команда не была обнаружена."));
            CommandDescription descr = command.createDescription(tokens);

            CommandRequest request = new CommandRequest(descr);
            AnswerRequests answer = sender.sendRequest(request);
            console.printLine(answer.getContainer());
        }catch (WrongValuesOfCommandArgumentException e){
            throw new CommandManagerException("Не верное колличество аргументов: "+e.getMessage());
        }catch (UnknownCommandException e){
            throw new CommandManagerException(e.getMessage());
        }catch (NullPointerException e) {
            throw new CommandManagerException(e.toString());
        } catch (CreateObjectException e) {
            throw new CommandManagerException("Проблемы во время создания объекта: "+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
