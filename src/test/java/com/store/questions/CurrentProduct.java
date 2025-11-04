package com.store.questions;

import com.store.ui.ProductsScreen;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class CurrentProduct implements Question<String> {

    public static Question<String> name() {
        return new CurrentProduct();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ProductsScreen.PRODUCT_NAME).answeredBy(actor);
    }
}
