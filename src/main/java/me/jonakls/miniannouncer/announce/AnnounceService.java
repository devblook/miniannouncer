package me.jonakls.miniannouncer.announce;

import me.jonakls.miniannouncer.service.Service;

import javax.inject.Inject;

public class AnnounceService implements Service {

  @Inject
  private AnnouncementManager manager;

  @Override
  public void start() {
    manager.startTask(manager.createStack());
  }

  @Override
  public void stop() {
    manager.stopTask();
  }
}
