package com.store.questions;

import com.store.ui.LoginScreen;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class IsLoginScreenVisible implements Question<Boolean> {

    private final Target elementToCheck;

    // Permite verificar cualquier elemento clave del LoginScreen
    public IsLoginScreenVisible(Target elementToCheck) {
        this.elementToCheck = elementToCheck;
    }

    // Método estático para mayor legibilidad
    public static IsLoginScreenVisible displayed() {
        return new IsLoginScreenVisible(LoginScreen.loginScreenTitle());
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            return elementToCheck.resolveFor(actor).isVisible();
        } catch (Exception e) {
            System.out.println("No se encontró el elemento de login: " + e.getMessage());
            return false;
        }
    }
}
