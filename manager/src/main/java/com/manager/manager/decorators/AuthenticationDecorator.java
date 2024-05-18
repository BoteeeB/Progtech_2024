package com.manager.manager.decorators;

import com.manager.manager.Interfaces.Authentication;

public abstract class AuthenticationDecorator implements Authentication {
    protected Authentication wrapped;

    public AuthenticationDecorator(Authentication wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void execute() {
        wrapped.execute();
    }
}
