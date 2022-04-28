package com.yousefemadi.mafiagame;


import java.util.ArrayList;

public class Game {
    public int id;
    public Scenario scenario;
    public ArrayList<Player> players;
    public ArrayList<Role> roles;
    public boolean isActive;

    public Game(int id, ArrayList<Player> players, Scenario scenario) {
        this.id = id;
        this.players = players;
        this.scenario = scenario;
        this.roles = scenario.roles;
        this.isActive = false;
    }

    public void start() {
        this.isActive = true;
    }

    public void stop() {
        this.isActive = false;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public String toString() {
        return "Game{id=" + this.id + ", scenario code=" + this.scenario.code + ", players=\n" + this.players + ", roles=" + this.roles + ", isActive=" + this.isActive + "}\n";
    }



}
