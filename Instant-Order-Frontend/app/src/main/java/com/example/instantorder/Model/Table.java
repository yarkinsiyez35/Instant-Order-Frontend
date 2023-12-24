package com.example.instantorder.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {
    private String objectId;
    private int tableId;
    private int employeeId;
    private List<FoodTable> foodOrders;
    private boolean paymentReceived;
    private double total;

    public Table(){}

    public Table(int tableId, int employeeId, List<FoodTable> foodOrders, boolean paymentReceived, double total) {
        this.tableId = tableId;
        this.employeeId = employeeId;
        this.foodOrders = foodOrders;
        this.paymentReceived = paymentReceived;
        this.total = total;
    }

    public Table(int tableId) {
        this.tableId = tableId;
        this.foodOrders = new ArrayList<>();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<FoodTable> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodTable> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public boolean isPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addFoodOrderTable(FoodTable foodTable)
    {
        this.foodOrders.add(foodTable);
        this.total += foodTable.getCount() * foodTable.getFood().getPrice();          //updates the price
    }

    public boolean hasNull()
    {
        return this.tableId == 0 || this.total == 0 || this.foodOrders.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableId == table.tableId && employeeId == table.employeeId && paymentReceived == table.paymentReceived && Objects.equals(objectId, table.objectId) && Objects.equals(foodOrders, table.foodOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, tableId, employeeId, foodOrders, paymentReceived);
    }

    @Override
    public String toString() {
        return "Table{" +
                "objectId='" + objectId + '\'' +
                ", tableId=" + tableId +
                ", employeeId=" + employeeId +
                ", foodOrders=" + foodOrders +
                ", paymentReceived=" + paymentReceived +
                '}';
    }
}
