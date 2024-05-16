package com.example.se2Assignment.dto;

public class TheaterDto {
    private String theaterName;
    private String address;
    private String imageUrl;
    private String description;
    private int a_section;
    private int b_section;
    private int vip_section;

    public TheaterDto(String theaterName, String address, String imageUrl, String description, int a_section, int b_section, int vip_section) {
        this.theaterName = theaterName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.description = description;
        this.a_section = a_section;
        this.b_section = b_section;
        this.vip_section = vip_section;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getA_section() {
        return a_section;
    }

    public void setA_section(int a_section) {
        this.a_section = a_section;
    }

    public int getB_section() {
        return b_section;
    }

    public void setB_section(int b_section) {
        this.b_section = b_section;
    }

    public int getVip_section() {
        return vip_section;
    }

    public void setVip_section(int vip_section) {
        this.vip_section = vip_section;
    }
}
