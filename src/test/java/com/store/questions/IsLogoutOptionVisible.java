package com.store.questions;

import com.store.ui.LoginScreen;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class IsLogoutOptionVisible implements Question<Boolean> {

    private final Target elementToCheck;

    public IsLogoutOptionVisible(Target elementToCheck) {
        this.elementToCheck = elementToCheck;
    }

    public static IsLogoutOptionVisible displayed() {
        return new IsLogoutOptionVisible(LoginScreen.logoutOption());
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            return elementToCheck.resolveFor(actor).isVisible();
        } catch (Exception e) {
            System.out.println("No se encontró la opción de Logout: " + e.getMessage());
            return false;
        }
    }
}
