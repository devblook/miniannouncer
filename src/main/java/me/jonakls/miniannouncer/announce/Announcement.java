package me.jonakls.miniannouncer.announce;

import java.util.List;

public class Announcement {

    private final List<String> lines;

    public Announcement(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

}
