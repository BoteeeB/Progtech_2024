package com.manager.manager.decorators;

import com.manager.manager.Interfaces.Authentication;

public class LoggingDecorator extends AuthenticationDecorator {

    public LoggingDecorator(Authentication wrapped) {
        super(wrapped);
    }

    @Override
    public void execute() {
        System.out.println("Naplózás: Művelet megkezdése...");
        super.execute();
        System.out.println("Naplózás: Művelet befejezve.");
    }

    @Override
    public String getUsername() {
        return wrapped.getUsername();
    }
}
