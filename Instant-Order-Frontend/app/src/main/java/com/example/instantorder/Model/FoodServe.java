package com.example.instantorder.Model;

public class FoodServe {

    private String objectId;
    private String foodName;
    private int count;
    private int tableId;
    private String note;
    private boolean served;

    public FoodServe() {
    }

    public FoodServe(FoodOrder foodOrder, boolean served) {
        this.foodName = foodOrder.getFood().getName();
        this.count = foodOrder.getCount();
        this.note = foodOrder.getNote();
        this.tableId = foodOrder.getTableId();
        this.served = served;

    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }
}

