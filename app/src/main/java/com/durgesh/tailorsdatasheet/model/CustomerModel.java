package com.durgesh.tailorsdatasheet.model;

import io.realm.RealmObject;

/**
 * Created by Durgesh Parekh on 01/18/18.
 */

public class CustomerModel extends RealmObject{
    private int customer_id;
    private String name;
    private String gender;
    private String address;
    private String contact;

    private int pent_length;
    private int bottom;
    private int west;
    private int hip;
    private int fly;
    private int thigh;
    private int thighReady;

    private int shirt_length;
    private int chest;
    private int stomach;
    private int loosing;
    private int neck;
    private int shoulder;
    private int sleves;


    public CustomerModel() {
    }

    public int getPent_length() {
        return pent_length;
    }

    public void setPent_length(int pent_length) {
        this.pent_length = pent_length;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getFly() {
        return fly;
    }

    public void setFly(int fly) {
        this.fly = fly;
    }

    public int getThigh() {
        return thigh;
    }

    public void setThigh(int thigh) {
        this.thigh = thigh;
    }

    public int getThighReady() {
        return thighReady;
    }

    public void setThighReady(int thighReady) {
        this.thighReady = thighReady;
    }

    public int getShirt_length() {
        return shirt_length;
    }

    public void setShirt_length(int shirt_length) {
        this.shirt_length = shirt_length;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getStomach() {
        return stomach;
    }

    public void setStomach(int stomach) {
        this.stomach = stomach;
    }

    public int getLoosing() {
        return loosing;
    }

    public void setLoosing(int loosing) {
        this.loosing = loosing;
    }

    public int getNeck() {
        return neck;
    }

    public void setNeck(int neck) {
        this.neck = neck;
    }

    public int getShoulder() {
        return shoulder;
    }

    public void setShoulder(int shoulder) {
        this.shoulder = shoulder;
    }

    public int getSleves() {
        return sleves;
    }

    public void setSleves(int sleves) {
        this.sleves = sleves;
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "customer_id=" + customer_id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", pent_length=" + pent_length +
                ", bottom=" + bottom +
                ", west=" + west +
                ", hip=" + hip +
                ", fly=" + fly +
                ", thigh=" + thigh +
                ", thighReady=" + thighReady +
                ", shirt_length=" + shirt_length +
                ", chest=" + chest +
                ", stomach=" + stomach +
                ", loosing=" + loosing +
                ", neck=" + neck +
                ", shoulder=" + shoulder +
                ", sleves=" + sleves +
                '}';
    }
}
