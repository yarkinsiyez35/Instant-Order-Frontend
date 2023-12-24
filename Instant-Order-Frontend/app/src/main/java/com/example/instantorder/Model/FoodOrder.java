package com.example.instantorder.Model;

public class FoodOrder {
    private String objectId;
    private Food food;
    private int tableId;

    private int count;

    private String note;
    private boolean cookingStatus;

    public FoodOrder() {
    }

    public FoodOrder(Food food, int tableId, int count, String note, boolean cookingStatus) {
        this.food = food;
        this.tableId = tableId;
        this.count = count;
        this.note = note;
        this.cookingStatus = cookingStatus;
    }

    public String getObjectId() {
        return objectId;
    }

    public Food getFood() {
        return food;
    }

    public int getTableId() {
        return tableId;
    }

    public int getCount() {
        return count;
    }

    public String getNote() {
        return note;
    }

    public boolean isCookingStatus() {
        return cookingStatus;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCookingStatus(boolean cookingStatus) {
        this.cookingStatus = cookingStatus;
    }

    public boolean hasNull()
    {
        return this.food.hasNull() || this.tableId == 0;
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "objectId='" + objectId + '\'' +
                ", food=" + food +
                ", tableId=" + tableId +
                ", count=" + count +
                ", note='" + note + '\'' +
                ", cookingStatus=" + cookingStatus +
                '}';
    }
}


