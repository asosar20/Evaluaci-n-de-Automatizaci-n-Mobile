package com.store.tasks;

import com.store.ui.ProductsScreen;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;


import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AddToCart implements Task {

    @Step("{0} adds product to cart")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(ProductsScreen.ADD_TO_CART_BUTTON));
    }

    public static AddToCart theProduct() {
        return instrumented(AddToCart.class);
    }
}
