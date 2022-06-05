package me.jonakls.miniannouncer.message;

import me.jonakls.miniannouncer.MiniAnnouncer;

import java.util.HashSet;
import java.util.Set;

public final class MessageHandlerBuilder {

    private final MiniAnnouncer plugin;
    private final Set<MessageInterceptor> interceptors;

    protected MessageHandlerBuilder(MiniAnnouncer plugin) {
        this.plugin = plugin;
        this.interceptors = new HashSet<>();
    }

    public MessageHandlerBuilder addInterceptor(MessageInterceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    public MessageHandler build() {
        return new MessageHandler(plugin, interceptors);
    }

}
