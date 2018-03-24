package com.example.micha.shoppingapp2;

/**
 * Created by micha on 22.11.2017.
 */

public class ListObject {
    private String nazwa;
    private String ile;
    private String cena;
    private Boolean zapisane;

    ListObject(String nazwa, String ile, String cena, Boolean zapisane){
        this.nazwa = nazwa;
        this.cena = cena;
        this.ile = ile;
        this.zapisane = zapisane;

    }

    ListObject(){
        nazwa = "przedmiot";
        cena = "1";
        ile = "1";
        zapisane = false;
    }

    public Boolean isZapisane() {
        return zapisane;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getIle() {
        return ile;
    }

    public String getCena() {
        return cena;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setIle(String ile) {
        this.ile = ile;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }
}
