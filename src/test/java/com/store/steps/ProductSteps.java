package com.store.steps;

import com.store.questions.CurrentProduct;
import com.store.tasks.AddToCart;
import com.store.tasks.SelectProduct;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSteps {

    private Actor actor;

    @Given("User is logged into the application")
    public void user_is_logged_into_the_application() {
        // opcional: haz login aquí o deja vacío si no es necesario
    }

    @When("he selects the first product")
    public void he_selects_the_first_product() {
        actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                SelectProduct.named("First Product")
        );
    }

    @When("he adds the product to the cart")
    public void he_adds_the_product_to_the_cart() {
        actor.attemptsTo(
                AddToCart.theProduct()
        );
    }

    @Then("he should see the product details")
    public void he_should_see_the_product_details() {
        String productName = actor.asksFor(CurrentProduct.name());
        assertThat(productName).isNotBlank();
    }
}
