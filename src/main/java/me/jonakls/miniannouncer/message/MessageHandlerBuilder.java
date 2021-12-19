package me.jonakls.miniannouncer.message;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public final class MessageHandlerBuilder {

    private final FileConfiguration configuration;
    private final Set<MessageInterceptor> interceptors;

    protected MessageHandlerBuilder(FileConfiguration configuration) {
        this.configuration = configuration;
        this.interceptors = new HashSet<>();
    }

    public MessageHandlerBuilder addInterceptor(MessageInterceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    public MessageHandler build() {
        return new MessageHandler(configuration, interceptors);
    }

}
