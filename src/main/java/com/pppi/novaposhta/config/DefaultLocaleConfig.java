package com.pppi.novaposhta.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Locale;

@Configuration
public class DefaultLocaleConfig {

    @EventListener
    public void setDefaultApplicationLocale2En(ApplicationStartedEvent startedEvent) {
        Locale.setDefault(Locale.ENGLISH);
    }

}