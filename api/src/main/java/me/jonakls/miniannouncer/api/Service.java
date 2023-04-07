package me.jonakls.miniannouncer.api;

public interface Service {

    void start();

    default void stop() {
    }

}
