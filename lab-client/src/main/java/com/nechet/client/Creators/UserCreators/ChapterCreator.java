package com.nechet.client.Creators.UserCreators;

import com.nechet.client.exceptions.ConsoleReadException;
import com.nechet.common.util.model.Chapter;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.checkers.ChapterMarineCountChecker;
import com.nechet.common.util.model.checkers.NameChecker;
import com.nechet.client.system.UserConsole;


import java.util.InputMismatchException;
import java.util.Scanner;

public class ChapterCreator implements BaseObjectUserCreator<Chapter> {
    private final Scanner out;
    public ChapterCreator (){
        out = new Scanner(System.in);
    }
    @Override
    public Chapter create() {
        Chapter chapter = new Chapter();
        UserConsole console = new UserConsole(out);
        console.printLine("Начинаем создание объекта класса Chapter:");
        while (true) {
            try {
                console.printLine("Введите значение name (тип String,значение не null и не пустое)");
                String line = "";
                NameChecker nameCheck = new NameChecker();
                line = console.read();
                if (nameCheck.check(line)) {
                    chapter.setName(line);
                } else {
                    console.printLine("Значение строки должно быть не пустым и не null. Попробуйте заново.");
                    continue;
                }
            } catch (InputMismatchException e) {
                console.printLine("Неправильный ввод строки. Попробуйте заново.");
                continue;
            }catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        while (true) {
            try {
                console.printLine("Введите значение parentLegion (тип String)");
                String name;
                name = console.read();
                chapter.setParentLegion(name);
            } catch (InputMismatchException e) {
                console.printLine("Неправильный ввод строки. Попробуйте заново.");
                continue;
            } catch (ConsoleReadException e){
            console.printLine(e.getMessage()+" Попробуйте заново.");
            continue;
        }
            break;
        }
        while (true) {
            try {
                console.printLine("Введите значение marineCount (тип Integer,значение больше 0, максимальное значение: 1000)");
                int value = 0;
                ChapterMarineCountChecker countCheck = new ChapterMarineCountChecker();
                String line = console.read();
                if (line!=null)
                    value = Integer.parseInt(line);
                else {
                    console.printLine("Значение числа должно быть не null. Попробуйте заново.");
                    continue;
                }
                if (countCheck.check(value)) {
                    chapter.setMarineCount(value);
                } else {
                    console.printLine("Значение числа должно быть целым больше 0 и не больше 1000. Попробуйте заново.");
                    continue;
                }
            } catch (NumberFormatException e) {
                console.printLine("Неправильный ввод числа. Попробуйте заново.");
                continue;
            } catch (ConsoleReadException e){
            console.printLine(e.getMessage()+" Попробуйте заново.");
            continue;
        }
            break;
        }
        while (true) {
            try {
                console.printLine("Введите значение world (тип String,значение не null)");
                String line = console.read();
                if (line!=null) {
                    chapter.setWorld(line);
                } else {
                    console.printLine("Значение числа должно быть не null. Попробуйте заново.");
                    continue;
                }
            } catch (NumberFormatException e) {
                console.printLine("Неправильный ввод числа. Попробуйте заново.");
                continue;
            }catch (ConsoleReadException e){
                console.printLine(e.getMessage()+" Попробуйте заново.");
                continue;
            }
            break;
        }
        console.printLine("Обьект Chapter успешно создан!");
        return chapter;
    }
}
