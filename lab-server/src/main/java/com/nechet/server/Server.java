package com.nechet.server;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.server.connectionLogic.RequestHandler;
import com.nechet.server.connectionLogic.TCPserver;
import com.nechet.server.databaseLogic.SpaceMarineDAO;
import com.nechet.server.databaseLogic.TableMaker;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeSet;

public class Server {
        public static void main(String[] args) throws Exception {
            String login;
            String password;
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            while (true) {
                //System.out.println("Введите логин и пароль к бд через пробел:");
                //Scanner sc = new Scanner(System.in);
                //String[] line = sc.nextLine().split(" ");
                //login = line[0]; password = line[1];
                try {
                    TableMaker maker = new TableMaker(Utils.getUrl(), "s409427","bcWQPoi6Q1UFrwVT");
                    maker.createTable();

                    break;
                } catch (SQLException e) {
                    System.out.println("Не удалось подключиться к бд: "+e.getMessage());
                    e.printStackTrace();
                }
            }
            String path = System.getenv("lab5");
            Utils.setEnv(path);
            RequestHandler requestHandler = new RequestHandler();
            TCPserver server = new TCPserver(requestHandler);
            SpaceMarinesManager marineManager = SpaceMarinesManager.getInstance();
            SpaceMarineDAO marinesDB = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
            TreeSet<SpaceMarine> trsMarines = new TreeSet<>(marinesDB.getAllSpaceMarines());
            marineManager.setCollection(trsMarines);
            try {
                server.openConnection();
                System.out.println("Сервер запущен");
                server.run();
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    {
                        System.out.println("Получен сигнал завершения работы (Ctrl+D).");
                        System.out.println("Закрываем программу");
                        server.close();
                        System.exit(1);
                    }

                    @Override
                    public void run() {
                    }
                });
            } finally {
                server.close();
            }
        }
    }

