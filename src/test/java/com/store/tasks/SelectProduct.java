package com.store.tasks;

import com.store.ui.ProductsScreen;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SelectProduct implements Task {

    private final String productName;

    public SelectProduct(String productName) {
        this.productName = productName;
    }

    public static SelectProduct named(String productName) {
        return instrumented(SelectProduct.class, productName);
    }

    @Step("{0} selects product #productName")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ProductsScreen.FIRST_PRODUCT)
        );
    }
}