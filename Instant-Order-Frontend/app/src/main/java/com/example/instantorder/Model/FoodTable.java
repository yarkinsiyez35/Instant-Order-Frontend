package com.example.instantorder.Model;

public class FoodTable {
    private Food food;

    private int count;

    private int tableId;

    public FoodTable(Food food, int count, int tableId) {
        this.food = food;
        this.count = count;
        this.tableId = tableId;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
