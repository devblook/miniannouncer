package me.jonakls.miniannouncer.paper.service;

public interface Service {

    void start();

    default void stop() { }
}
