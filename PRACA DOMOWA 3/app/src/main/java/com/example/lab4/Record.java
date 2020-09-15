package com.example.lab4;

public class Record {

    private String Date;
    private String ServiceActivity;
    private String Costs;
    private String Mileage;
    private int PictureNumber;

    public  String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    private String Hash;

    public int getPictureNumber() {
        return PictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        PictureNumber = pictureNumber;
    }

    public String getDate() {
        return Date;
    }

    public String getServiceActivity() {
        return ServiceActivity;
    }

    public void setServiceActivity(String serviceActivity) {
        ServiceActivity = serviceActivity;
    }

    public void setDate(String date) {
        Date = date;
    }


    public String getCosts() {
        return Costs;
    }

    public void setCosts(String costs) {
        Costs = costs;
    }

    public String getMileage() {
        return Mileage;
    }

    public void setMileage(String mileage) {
        Mileage = mileage;
    }

    public Record()
    {

    }

}
