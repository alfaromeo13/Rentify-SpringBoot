package com.example.rentify.search;

public class ApartmentSearch {
    private Integer id;
    private String title;
    private String description;
    private Integer sqMeters;
    private Double price;
    private Integer numOfBedrooms;
    private Integer numOfBathrooms;
    private String number;

    private String addressStreet;//we need join for this
    private String cityName;//we need join

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSqMeters() {
        return sqMeters;
    }

    public void setSqMeters(Integer sqMeters) {
        this.sqMeters = sqMeters;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumOfBedrooms() {
        return numOfBedrooms;
    }

    public void setNumOfBedrooms(Integer numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddress(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Integer getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public void setNumOfBathrooms(Integer numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
