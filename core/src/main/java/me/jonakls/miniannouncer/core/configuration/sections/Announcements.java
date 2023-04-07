package me.jonakls.miniannouncer.core.configuration.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class Announcements {

    private List<String> lines = List.of(
            "[MESSAGE] <reset>",
            "[TITLE]<aqua>Title announce;<green>Subtitle Announce;2;4;2",
            "[ACTIONBAR]<aqua>Actionbar announce",
            "[MESSAGE]<red>This is a text announcer",
            "[MESSAGE]<gradient:red:blue>Compatible with hex colors and gradient effect</gradient>",
            "[MESSAGE]<color:#EAAC46>Please view <click:open_url:https://docs.adventure.kyori.net/minimessage#format><hover:show_text:'<yellow>Click here'><red>MiniMessage</hover></click>",
            "[MESSAGE]<color:#EAAC46>for view documentation minimessage and write more formats!!",
            "[MESSAGE] <reset>",
            "[SOUND] ENTITY_GHAST_SCREAM;5;5"
    );

    public List<String> lines() {
        return lines;
    }
}
