package com.nechet.client;

import com.nechet.client.TCPconnection.Sender;
import com.nechet.client.TCPconnection.TCPclient;
import com.nechet.client.comands.Invoker;
import com.nechet.client.exceptions.InvokerException;
import com.nechet.client.system.UserConsole;


import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        UserConsole console = new UserConsole(new Scanner(System.in));
        TCPclient client = new TCPclient("localhost", 5060);
        Sender sender = new Sender(client);
        Invoker invoker = new Invoker();
        while (true) {
            try {
                client.run();
                invoker.startToInvoke(new InputStreamReader(System.in), sender);
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    {
                        System.out.println("Получен сигнал завершения работы (Ctrl+D).");
                        System.out.println("Закрываем программу");
                        System.exit(1);
                    }

                    @Override
                    public void run() {
                    }
                });
            } catch (InvokerException e) {
                console.printLine(e.getMessage());
            } catch (ConnectException e) {
                System.out.println("Сервер не запущен! Запустите программу через время");
                System.exit(0);

            } catch (SocketTimeoutException e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
