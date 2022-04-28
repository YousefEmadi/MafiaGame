package com.yousefemadi.mafiagame;


import java.util.ArrayList;
import java.util.Collections;

public class Scenario {
    public int code;
    public ArrayList<Role> roles;

    public Scenario() {
    }

    public Scenario(int code) {
        this.code = code;
        this.roles = new ArrayList();
        if (code == 10) {
            this.initializeForTen();
        } else {
            System.out.println("Scenario not found");
        }

    }

    public Scenario(int code, ArrayList<Role> roles) {
        this.code = code;
        this.roles = roles;
    }

    public void initializeForTen() {
        this.code = 10;
        this.roles = new ArrayList();
        this.roles.add(new Role("Godfather", true));
        this.roles.add(new Role("Nato", true));
        this.roles.add(new Role("Gerogangir", true));
        this.roles.add(new Role("Doctor", false));
        this.roles.add(new Role("Karagah", false));
        this.roles.add(new Role("Takavar", false));
        this.roles.add(new Role("Tofangdar", false));
        this.roles.add(new Role("Negahban", false));
        this.roles.add(new Role("Sharvand", false));
        this.roles.add(new Role("Shahrvand", false));
        Collections.shuffle(this.roles);
    }

    public String toString() {
        return "Scenario{code=" + this.code + ", roles=" + this.roles + "}";
    }
}

