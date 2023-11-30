package com.salazarisaiahnoel.sapmodule_shpl;

public class StringTriple {
    public final String data;
    public final String number;
    public final String price;

    public StringTriple(String data, String number, String price){
        this.data = data;
        this.number = number;
        this.price = price;
    }

    public String toString(){
        return "(" + this.data + ", " + this.number + ", " + this.price + ")";
    }
}
