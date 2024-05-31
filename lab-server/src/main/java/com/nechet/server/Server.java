package com.nechet.server;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.server.connectionLogic.RequestHandler;
import com.nechet.server.connectionLogic.TCPserver;
import com.nechet.server.fileManipulation.ReadFromFileObject;
import com.nechet.server.fileManipulation.ReadJSONCollection;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;

import java.util.TreeSet;

public class Server {
        public static void main(String[] args) throws Exception {

            Utils.setEnv("lab5");
            RequestHandler requestHandler = new RequestHandler();
            TCPserver server = new TCPserver(requestHandler);
            SpaceMarinesManager marineManager = SpaceMarinesManager.getInstance();
            ReadFromFileObject<TreeSet<SpaceMarine>> reader = new ReadJSONCollection();
            TreeSet<SpaceMarine> marines = reader.read(System.getenv(Utils.getEnv())+"/SpaceMarines.json");
            marineManager.setCollection(marines);
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

