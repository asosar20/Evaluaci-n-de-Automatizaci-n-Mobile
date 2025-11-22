package com.store.steps;

import com.store.hooks.AppiumHooks;
import com.store.ui.ProductDetailsScreen;
import com.store.ui.ProductsScreen;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.junit.Assert;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CarritoSteps {

    private Actor actor;

    public void inicializar() {
        String actorName = "Usuario";
        AppiumDriver driver = AppiumHooks.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver no inicializado");
        }

        OnStage.setTheStage(new OnlineCast());

        actor = OnStage.theActorCalled(actorName);
        actor.can(BrowseTheWeb.with(driver));

        System.out.println("Actor: " + actorName + " | Session: " + driver.getSessionId());
    }

    @Given("estoy en la aplicación de SauceLabs")
    public void estoyEnLaAplicacionDeSauceLabs() {
        this.inicializar();

        actor.attemptsTo(
                WaitUntil.the(ProductsScreen.title_product(), isVisible())
                        .forNoMoreThan(20).seconds()
        );

        boolean esVisible = ProductsScreen.title_product().resolveFor(actor).isVisible();
        Assert.assertTrue("La app no cargó correctamente", esVisible);

        System.out.println("Products screen verified on " + AppiumHooks.getCurrentPlatform());
    }

    @And("valido que carguen correctamente los productos en la galeria")
    public void validoQueCarguenCorrectamenteLosProductosEnLaGaleria() {
        Actor actorEnEscena = OnStage.theActorInTheSpotlight();

        boolean esVisible = ProductsScreen.title_product().resolveFor(actorEnEscena).isVisible();
        Assert.assertTrue("No se ven los productos", esVisible);
    }

    @When("agrego {int} del siguiente producto {string}")
    public void agregoUnidadesDelSiguienteProducto(int unidades, String nombreProducto) {
        Actor actorEnEscena = OnStage.theActorInTheSpotlight();


        actorEnEscena.attemptsTo(
                WaitUntil.the(ProductsScreen.PRODUCT_BY_NAME(nombreProducto), isVisible()).forNoMoreThan(10).seconds(),
                Click.on(ProductsScreen.PRODUCT_BY_NAME(nombreProducto))
        );

        actorEnEscena.attemptsTo(
                WaitUntil.the(ProductDetailsScreen.ADD_TO_CART_BUTTON, isVisible()).forNoMoreThan(10).seconds()
        );

        for (int i = 1; i < unidades; i++) {
            actorEnEscena.attemptsTo(Click.on(ProductDetailsScreen.PLUS_BUTTON));
        }

        actorEnEscena.attemptsTo(Click.on(ProductDetailsScreen.ADD_TO_CART_BUTTON));
    }

    @Then("valido el carrito de compra actualice correctamente a {int}")
    public void validoElCarritoDeCompraActualiceCorrectamente(int unidadesEsperadas) {
        Actor actorEnEscena = OnStage.theActorInTheSpotlight();

        actorEnEscena.attemptsTo(
                WaitUntil.the(ProductDetailsScreen.CART_BADGE, isVisible()).forNoMoreThan(10).seconds()
        );

        String textoBadge = Text.of(ProductDetailsScreen.CART_BADGE).answeredBy(actorEnEscena);

        Assert.assertEquals("El número en el carrito es incorrecto",
                String.valueOf(unidadesEsperadas), textoBadge);
    }
}