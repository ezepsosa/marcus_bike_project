package com.ezepsosa.marcusbike;

import com.ezepsosa.marcusbike.config.ServerConfig;

public class App {

    public static void main(String[] args) {

        ServerConfig server = new ServerConfig();
        server.start();
    }
}
