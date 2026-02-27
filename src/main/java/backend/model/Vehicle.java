package backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String vehicleId;
    private String brand;
    private String model;
    private int year;
    private double price;
    private double tax;
    private double commision;
    private double sellingPrice;

    public Vehicle() {}

    public Vehicle(String vehicleId, String brand, String model, int year, double price,double tax,
                   double commision,double sellingPrice) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.tax = tax;
        this.commision = commision;
        this.sellingPrice = sellingPrice;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getCommision() { return commision; }
    public void setCommision(double commision) { this.commision = commision; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }


}
