package me.jonakls.miniannouncer.service;

public interface Service {

  void start();

  default void stop() { }
}
