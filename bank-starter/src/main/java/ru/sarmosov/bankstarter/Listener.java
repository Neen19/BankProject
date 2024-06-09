package ru.sarmosov.bankstarter;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class Listener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("стартер робит");
    }

    public void aVoid() {
        System.out.println("ksksdkdks");
    }
}
