package com.example.grpc.chat;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        Server server = ServerBuilder.forPort(9100).addService(new ChatServiceImpl()).build();
        server.start();
        System.out.println("Server start at "+server.getPort());
        server.awaitTermination();
    }
}
