package com.yousefemadi.mafiagame;

public class Role {
    public String name;
    public boolean isMafia;

    public Role(String name, boolean isMafia) {
        this.name = name;
        this.isMafia = isMafia;
    }

    public void actInDay() {
    }

    public void actInNight() {
    }

    public String toString() {
        return "Role{name='" + this.name + "'}";
    }
}
