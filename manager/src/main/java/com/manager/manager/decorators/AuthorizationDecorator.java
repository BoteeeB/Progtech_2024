package com.manager.manager.decorators;

import com.manager.manager.Interfaces.Authentication;
import com.manager.manager.user.UserSession;

public class AuthorizationDecorator extends AuthenticationDecorator {
    private boolean requiresAdmin;

    public AuthorizationDecorator(Authentication wrapped, boolean requiresAdmin) {
        super(wrapped);
        this.requiresAdmin = requiresAdmin;
    }



    private boolean ellenorizJogosultsag() {
        // Ellenőrizzük a felhasználó típusát, ha admin jogosultság szükséges
        if (requiresAdmin) {
            int userType = UserSession.getUserType();
            System.out.println("Jogosultság ellenőrzése...");
            return userType == 1; // Csak akkor sikeres, ha admin (userType == 1)
        }
        return true; // Ha nem szükséges admin jogosultság, mindig sikeres
    }

    @Override
    public void execute() {
        if (ellenorizJogosultsag()) {
            super.execute();
        } else {
            System.out.println("Nincs megfelelő jogosultság a művelet végrehajtásához!");
        }
    }

    @Override
    public String getUsername() {
        return "";
    }
}
