package com.store.ui;

import com.store.utils.ConfigHelper;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ProductsScreen {

    public static final Target PRODUCTS_TITLE_ANDROID =
            Target.the("products title")
                    .located(By.xpath("//android.widget.TextView[@text='Products']"));

    public static final Target PRODUCTS_TITLE_IOS =
            Target.the("products title")
                    .located(By.xpath("//XCUIElementTypeStaticText[@name='Products']"));

    public static Target title_product() {
        return ConfigHelper.isAndroid() ? PRODUCTS_TITLE_ANDROID : PRODUCTS_TITLE_IOS;
    }

    public static Target PRODUCT_BY_NAME(String productName) {
        return Target.the("Product: " + productName)
                .located(By.xpath("//android.widget.TextView[@text='" + productName + "']/parent::*"));
    }
}