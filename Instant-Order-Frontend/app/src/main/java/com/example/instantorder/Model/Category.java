package com.example.instantorder.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {

    private String objectId;
    private String name;

    private List<Food> foods;

    public Category() {
    }

    public Category(String name, List<Food> foods) {
        this.name = name;
        this.foods = foods;
    }

    public Category(String name)
    {
        this.name = name;
        this.foods = new ArrayList<>();
    }

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
    public void addFood(Food food)
    {
        this.foods.add(food);
    }

    public boolean foodExists(Food food)
    {
        return this.foods.contains(food);
    }

    public boolean hasNull()
    {
        return this.foods.isEmpty() || this.name == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(objectId, category.objectId) && Objects.equals(name, category.name) && Objects.equals(foods, category.foods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, name, foods);
    }

    @Override
    public String toString() {
        return "Category{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", foods=" + foods +
                '}';
    }
}
