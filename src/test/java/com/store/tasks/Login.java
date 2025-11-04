package com.store.tasks;

import com.store.models.User;
import com.store.ui.LoginScreen;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Login implements Task {

    private final User user;

    public Login(User user) {
        this.user = user;
    }

    // 🔹 Tarea principal: ingresar credenciales y presionar login
    public static Login withCredentials(User user) {
        return instrumented(Login.class, user);
    }

    @Step("{0} logs in with username #user.getUsername()")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LoginScreen.usernameField(), isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(user.getUsername()).into(LoginScreen.usernameField()),
                Enter.theValue(user.getPassword()).into(LoginScreen.passwordField()),
                Click.on(LoginScreen.loginButton())
        );
    }

    // ============================================================
    // 🔹 Subtasks relacionadas dentro de la misma clase
    // ============================================================

    /** Task: Clic en el botón del menú  */
    public static Task openMenu() {
        return new Task() {
            @Step("{0} clicks on the menu button")
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(Click.on(LoginScreen.menuButton()));
            }
        };
    }

    /** Task: Clic en la opción "Log In" del menú */
    public static Task clickLoginOption() {
        return new Task() {
            @Step("{0} clicks on the login option from menu")
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(Click.on(LoginScreen.loginOptionMenu()));
            }
        };
    }



    /** Task: Clic en la opción "Log Out" (para cerrar sesión) */
    /*public static Task clickLogoutOption() {
        return new Task() {
            @Step("{0} clicks on the logout option from menu")
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(Click.on(MenuScreen.LOGOUT_OPTION));
            }
        };
    }*/
}
