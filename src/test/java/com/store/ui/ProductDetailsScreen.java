package com.store.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ProductDetailsScreen {

    public static final Target PLUS_BUTTON =
            Target.the("Increase quantity button")
                    .located(By.xpath("//android.widget.ImageView[@content-desc='Increase item quantity']"));

    public static final Target ADD_TO_CART_BUTTON =
            Target.the("Add to cart button")
                    .located(By.xpath("//*[@text='Add to cart']"));

    public static final Target CART_BADGE =
            Target.the("Cart badge counter")
                    .located(By.id("com.saucelabs.mydemoapp.android:id/cartTV"));
}