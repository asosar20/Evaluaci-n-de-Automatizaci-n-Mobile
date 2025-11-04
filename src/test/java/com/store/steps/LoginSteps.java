package com.store.steps;

import com.store.hooks.AppiumHooks;
import com.store.models.User;
import com.store.questions.IsLoginScreenVisible;
import com.store.questions.IsLogoutOptionVisible;
import com.store.tasks.Login;
import com.store.ui.ProductsScreen;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static org.hamcrest.Matchers.is;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private Actor actor;

    @Given("{word} is on the products screen")
    public void user_is_on_the_products_screen(String actorName) {
        System.out.println("Actor: " + actorName);

        AppiumDriver driver = AppiumHooks.getDriver();

        if (driver == null) {
            throw new IllegalStateException("Driver no inicializado");
        }

        System.out.println("Session: " + driver.getSessionId());

        actor = OnStage.theActorCalled(actorName);
        actor.can(BrowseTheWeb.with(driver));

        actor.attemptsTo(
                WaitUntil.the(ProductsScreen.title_product(), isVisible())
                        .forNoMoreThan(20).seconds()
        );

        System.out.println("✅ Products screen verified on " + AppiumHooks.getCurrentPlatform());

    }

    @When("the user opens the menu")
    public void the_user_opens_the_menu() {
        actor.attemptsTo(Login.openMenu());
    }

    @And("the user selects Log In")
    public void the_user_selects_LogIn() {
        actor.attemptsTo(Login.clickLoginOption());
    }

    @Then("the user should see the login screen")
    public void the_user_should_see_the_login_screen() {
        actor.should(seeThat("Login screen is visible", IsLoginScreenVisible.displayed(), is(true)));
    }

    @And("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        actor.attemptsTo(Login.withCredentials(new User(username, password)));
    }

    @And("the Products screen is displayed")
    public void the_products_screen_is_displayed() {
        actor.attemptsTo(
                WaitUntil.the(ProductsScreen.title_product(), isVisible())
                        .forNoMoreThan(20).seconds()
        );
    }

    @Then("the menu shows the option Log Out")
    public void the_menu_shows_the_option_Log_Out() {
        actor.attemptsTo(Login.openMenu());
        actor.should(seeThat("Logout option is visible", IsLogoutOptionVisible.displayed(), is(true)));
    }

}