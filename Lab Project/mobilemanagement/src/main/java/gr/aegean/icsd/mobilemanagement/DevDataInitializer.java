package gr.aegean.icsd.mobilemanagement;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
@Profile("dev") // Εκτελείται μόνο αν το active profile είναι "dev"
public class DevDataInitializer {

    private final MobileRepository mobileRepository;
    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);

    public DevDataInitializer(MobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    @PostConstruct
    public void initData() {
        if (mobileRepository.count() > 0) {
            return;
        }

        Mobile[] mobiles = new Mobile[]{
                new Mobile("ABC123DEF45",
                        "123456789012345",
                        "XR12",
                        "Samsung",
                        Set.of(NetworkTechnology.FOUR_G, NetworkTechnology.FIVE_G),
                        2,
                        8,
                        180,
                        4000,
                        699.99),

                new Mobile("XYZ789GHJ67",
                        "234567890123456",
                        "Note20",
                        "Apple",
                        Set.of(NetworkTechnology.FOUR_G, NetworkTechnology.THREE_G),
                        3,
                        6,
                        190,
                        4500,
                        999.00),

                new Mobile("LMN456KLO89",
                        "345678901234567",
                        "A32",
                        "Nokia",
                        Set.of(NetworkTechnology.GSM, NetworkTechnology.THREE_G),
                        1,
                        4,
                        170,
                        3000,
                        299.99),

                new Mobile("JKL321MNB56",
                        "456789012345678",
                        "S22",
                        "Oneplus",
                        Set.of(NetworkTechnology.LTE, NetworkTechnology.FIVE_G),
                        2,
                        8,
                        175,
                        4200,
                        749.50),

                new Mobile("GHJ654TRE32",
                        "567890123456789",
                        "MotoX",
                        "Motorola",
                        Set.of(NetworkTechnology.GSM, NetworkTechnology.HSPA),
                        1,
                        6,
                        160,
                        3100,
                        259.00),

                new Mobile("POI098YHN43",
                        "678901234567890",
                        "ZFold",
                        "Realme",
                        Set.of(NetworkTechnology.FOUR_G, NetworkTechnology.FIVE_G),
                        3,
                        8,
                        200,
                        5000,
                        1200.00),

                new Mobile("BVC741UJH22",
                        "789012345678901",
                        "P50",
                        "Huawei",
                        Set.of(NetworkTechnology.LTE, NetworkTechnology.FOUR_G),
                        2,
                        6,
                        185,
                        4100,
                        680.00),

                new Mobile("ERT852VBN11",
                        "890123456789012",
                        "A53",
                        "Asus",
                        Set.of(NetworkTechnology.HSPA, NetworkTechnology.LTE),
                        1,
                        4,
                        165,
                        3050,
                        320.00),

                new Mobile("QWE963ZXC00",
                        "901234567890123",
                        "XperiaZ",
                        "Sony",
                        Set.of(NetworkTechnology.THREE_G, NetworkTechnology.FOUR_G, NetworkTechnology.FIVE_G),
                        2,
                        6,
                        175,
                        3800,
                        710.00),

                new Mobile("TYU147MKL33",
                        "012345678901234",
                        "G8X",
                        "LG",
                        Set.of(NetworkTechnology.GSM, NetworkTechnology.FOUR_G),
                        2,
                        4,
                        178,
                        4000,
                        450.00),

        };

        mobileRepository.saveAll(Arrays.asList(mobiles));
        log.info(" Δημιουργήθηκαν και αποθηκεύτηκαν 10 εγγραφές κινητών στην βάση για το προφίλ DEV.");
    }

}
