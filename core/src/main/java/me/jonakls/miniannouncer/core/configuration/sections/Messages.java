package me.jonakls.miniannouncer.core.configuration.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.List;

@ConfigSerializable
public class Messages {

    @Comment("Message sent when the player does not have permission to do something.")
    private String noPermission = "<red>You do not have permission to do that.";
    @Comment("Message sent when the player does not provide enough arguments.")
    private String insufficientArguments = "<red>Insufficient arguments.";
    @Comment("Message sent when the configuration is reloaded.")
    private String configReloaded = "<green>Configuration reloaded.";
    @Comment("Help message for the plugin.")
    private List<String> help = List.of(
            "<green>MiniAnnouncer Help",
            "<green>/miniannouncer reload - Reloads the configuration.",
            "<green>/miniannouncer toggle - Toggles announcements."
    );
    @Comment("Messages for the toggle command.")
    private ToggleAnnouncements toggleAnnouncements = new ToggleAnnouncements();

    public String noPermission() {
        return noPermission;
    }

    public String insufficientArguments() {
        return insufficientArguments;
    }

    public String configReloaded() {
        return configReloaded;
    }

    public List<String> help() {
        return help;
    }

    public ToggleAnnouncements toggleAnnouncements() {
        return toggleAnnouncements;
    }

    @ConfigSerializable
    public static class ToggleAnnouncements {
        @Comment("Message sent when the player toggles announcements on.")
        public String enabled = "<green>Enabled announcements.";
        @Comment("Message sent when the player toggles announcements off.")
        public String disabled = "<red>Disabled announcements.";

        public String enabled() {
            return enabled;
        }

        public String disabled() {
            return disabled;
        }
    }
}
