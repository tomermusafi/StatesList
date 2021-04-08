package com.musafi.stateslist.API.entity;

public class StateEntity {
    private String flag;
    private String name;
    private String capital;
    private String [] borders;
    private String nativeName;
    private String alpha3Code;

    public StateEntity(String flag, String name, String capital, String[] borders, String nativeName,String alpha3Code) {
        this.flag = flag;
        this.name = name;
        this.capital = capital;
        this.borders = borders;
        this.nativeName = nativeName;
        this.alpha3Code= alpha3Code;
    }

    public StateEntity() {
    }

    public String getFlag() {
        return flag;
    }

    public StateEntity setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public String getName() {
        return name;
    }

    public StateEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getCapital() {
        return capital;
    }

    public StateEntity setCapital(String capital) {
        this.capital = capital;
        return this;
    }

    public String[] getBorders() {
        return borders;
    }

    public StateEntity setBorders(String[] borders) {
        this.borders = borders;
        return this;
    }

    public String getNativeName() {
        return nativeName;
    }

    public StateEntity setNativeName(String nativeName) {
        this.nativeName = nativeName;
        return this;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public StateEntity setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
        return this;
    }
}
