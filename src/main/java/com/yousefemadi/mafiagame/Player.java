package com.yousefemadi.mafiagame;

public class Player implements Comparable<Player> {
    public String name;
    public int id;
    public Role role;
    public boolean isAlive = true;
    public boolean canVote = true;
    public int receivedVotes = 0;
    public int badRecord = 0;

    public Player(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Player(String name, int id, Role role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public void die() {
        this.isAlive = false;
    }

    public void getVote() {
        ++this.receivedVotes;
    }

    public void joinGame(Game game) {
        game.addPlayer(this);
    }

    public void leaveGame(Game game) {
        game.removePlayer(this);
    }

    public void voteTo(Player player) {
        player.getVote();
    }

    public String toString() {
        return "Player{name='" + this.name + "', id=" + this.id + ", role=" + this.role + ", isAlive=" + this.isAlive + ", canVote=" + this.canVote + ", receivedVotes=" + this.receivedVotes + ", hasBadRecord=" + this.badRecord + "}\n";
    }

    public int compareTo(Player o) {
        return this.receivedVotes - o.receivedVotes;
    }
}

