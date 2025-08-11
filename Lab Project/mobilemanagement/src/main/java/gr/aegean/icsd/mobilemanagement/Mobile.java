package gr.aegean.icsd.mobilemanagement;

import gr.aegean.icsd.mobilemanagement.validators.*;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Mobile {

    @Id
    @ValidSerialNumber
    @Column(length = 11, nullable = false, unique = true,updatable = false, columnDefinition = "CHAR(11)")
    private String serialNumber;

    @Column(length = 15, nullable = false, unique = true,updatable = false, columnDefinition = "CHAR(15)")
    @ValidImei
    private String imei;

    @Column(nullable = false,updatable = false)
    @ValidModel
    private String model;

    @Column(nullable = false,updatable = false)
    @ValidBrand
    private String brand;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mobile_network_technologies", joinColumns = @JoinColumn(name = "mobile_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "network_technologies", nullable = false)
    @ValidNetworkTechnology
    private Set<NetworkTechnology> networkTechnologies;

    @Column(nullable = false)
    @ValidCameraCount
    private int cameraCount;

    @Column(nullable = false)
    @ValidCoreCount
    private int coreCount;

    @Column(nullable = false)
    @ValidWeightGrams
    private int weightGrams;

    @Column(nullable = false)
    @ValidBatteryCapacityMah
    private int batteryCapacityMah;

    @Column(nullable = false)
    @ValidCostEuro
    private double costEuro;

    public Mobile() {
    }

    public Mobile(
            String serialNumber,
            String imei,
            String model,
            String brand,
            Set<NetworkTechnology> networkTechnologies,
            int cameraCount,
            int coreCount,
            int weightGrams,
            int batteryCapacityMah,
            double costEuro) {
        this.serialNumber = serialNumber;
        this.imei = imei;
        this.model = model;
        this.brand = brand;
        this.networkTechnologies = networkTechnologies;
        this.cameraCount = cameraCount;
        this.coreCount = coreCount;
        this.weightGrams = weightGrams;
        this.batteryCapacityMah = batteryCapacityMah;
        this.costEuro = costEuro;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getImei() {
        return imei;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }


    public Set<NetworkTechnology> getNetworkTechnologies() {
        return networkTechnologies;
    }

    public void setNetworkTechnologies(Set<NetworkTechnology> networkTechnologies) {
        this.networkTechnologies = networkTechnologies;
    }

    public int getCameraCount() {
        return cameraCount;
    }

    public void setCameraCount(int cameraCount) {
        this.cameraCount = cameraCount;
    }

    public int getCoreCount() {
        return coreCount;
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(int weightGrams) {
        this.weightGrams = weightGrams;
    }

    public int getBatteryCapacityMah() {
        return batteryCapacityMah;
    }

    public void setBatteryCapacityMah(int batteryCapacityMah) {
        this.batteryCapacityMah = batteryCapacityMah;
    }

    public double getCostEuro() {
        return costEuro;
    }

    public void setCostEuro(double costEuro) {
        this.costEuro = costEuro;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "serialNumber='" + serialNumber + '\'' +
                ", imei='" + imei + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", networkTechnologies=" + networkTechnologies +
                ", cameraCount=" + cameraCount +
                ", coreCount=" + coreCount +
                ", weightGrams=" + weightGrams +
                ", batteryCapacityMah=" + batteryCapacityMah +
                ", costEuro=" + costEuro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Mobile m) {
            return Objects.equals(this.serialNumber, m.getSerialNumber());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}
