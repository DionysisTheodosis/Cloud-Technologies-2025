package gr.aegean.icsd.mobilemanagement;

import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

//Projection για να γυρίζει και τον σειριακό αριθμό μαζί
@Projection(name = "withSerialNumber", types = Mobile.class)
public interface MobileProjection {
    String getSerialNumber();
    String getImei();
    String getModel();
    String getBrand();
    Set<NetworkTechnology> getNetworkTechnologies();
    int getCameraCount();
    int getCoreCount();
    int getWeightGrams();
    int getBatteryCapacityMah();
    double getCostEuro();
}
