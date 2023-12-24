package com.example.instantorder.Model;

public class Employee {

    private String objectId;
    private int employeeId;
    private String password;
    private String firstName;
    private String lastName;

    public Employee() {
    }

    public Employee(String objectId, int employeeId, String password, String firstName, String lastName) {
        this.objectId = objectId;
        this.employeeId = employeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(int employeeId, String password, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean hasNull()
    {
        return this.firstName == null || this.lastName == null || this.password == null;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "objectId='" + objectId + '\'' +
                ", employeeId=" + employeeId +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
