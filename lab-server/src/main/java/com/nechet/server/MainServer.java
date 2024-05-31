package com.nechet.server;

import com.nechet.server.connectionLogic.RequestHandler;
import com.nechet.server.connectionLogic.TCPserver;
import com.nechet.server.system.Utils;

public class MainServer {
        public static void main(String[] args) throws Exception {

            Utils.setEnv(System.getenv("lab5"));
            RequestHandler requestHandler = new RequestHandler();
            TCPserver server = new TCPserver(requestHandler);
            try {
                server.openConnection();
                server.run();
            } finally {
                server.close();
            }
        }
    }

