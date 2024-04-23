package com.workintech.s17d2.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{

    @Override
    public int getSimpleTaxRate() {
        return 15;
    }

    @Override
    public int getMiddleTaxRate() {
        return 25;
    }

    @Override
    public int getUpperTaxRate() {
        return 35;
    }
}
