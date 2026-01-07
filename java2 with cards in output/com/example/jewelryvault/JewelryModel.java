package com.example.jewelryvault;

public class JewelryModel {
    private int id;
    private String name;
    private String type;
    private String value;

    public JewelryModel(int id, String name, String type, String value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getValue() { return value; }
}