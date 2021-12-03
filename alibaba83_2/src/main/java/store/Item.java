package store;

import java.io.DataInputStream;
import java.io.IOException;

// Please don't modify the class name.
public class Item {

    private String name;

    private int sellIn;

    private int value;

    // Please don't modify the signature of this method.
    public Item(String name, int sellIn, int value) {
        this.name = name;
        this.sellIn = sellIn;
        this.value = value;
    }

    // Please don't modify the signature of this method.
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

//    @FunctionalInterface  当抽象类中只有一个抽象方法时使用。


}
