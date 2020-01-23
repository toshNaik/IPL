package com.example.navdrawer;

public class Team {
    private String name;
    private int Budget;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudget() {
        return Budget;
    }

    public void setBudget(int budget) {
        Budget = budget;
    }

    public Team() {
    }

    public Team(String name, int budget) {
        this.name = name;
        Budget = budget;
    }
}
