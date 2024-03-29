package com.example.grpc.chat;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase{
    private static Set<StreamObserver<Chat.ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();
//  Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public StreamObserver<Chat.ChatMessage> chat(StreamObserver<Chat.ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage value) {
                System.out.println(value);
                Chat.ChatMessageFromServer message = Chat.ChatMessageFromServer.newBuilder().setMessage(value).setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000)).build();

                for (StreamObserver<Chat.ChatMessageFromServer> observer : observers) {
                    observer.onNext(message);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }

}
