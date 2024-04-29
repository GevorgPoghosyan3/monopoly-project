package am.aua.monopoly.core;

public class Railroad extends Property {

    public Railroad(String name, int price, int rent, int level1Fee, int level2Fee, int level3Fee, int mortgage, PropertyType propertyType, boolean isBuildable) {
        super(name, price, rent, level1Fee, level2Fee, level3Fee, mortgage, propertyType, isBuildable);
    }
}
