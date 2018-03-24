package com.example.micha.shoppingapp;

/**
 * Created by micha on 22.11.2017.
 */

public class ListObject {
    private String nazwa;
    private String ile;
    private String cena;
    private Boolean zapisane;



    private String id;

    ListObject(String id, String nazwa, String ile, String cena, Boolean zapisane){
        this.nazwa = nazwa;
        this.cena = cena;
        this.ile = ile;
        this.zapisane = zapisane;
        this.id = id;

    }

    public Boolean isZapisane() {
        return zapisane;
    }

    public Boolean getZapisane() {
        return zapisane;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
