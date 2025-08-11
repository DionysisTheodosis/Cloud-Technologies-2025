package gr.aegean.icsd.mobilemanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = MobileProjection.class)
public interface MobileRepository extends
        PagingAndSortingRepository<Mobile, String>,
        CrudRepository<Mobile, String> {

    @RestResource(rel = "by-serial-number")
    Optional<Mobile> findBySerialNumber(@Param("serialNumber") String serialNumber);

    @RestResource(rel = "by-brand")
    Page<Mobile> findByBrand(@Param("brand") String brand, Pageable pageable);

    @RestResource(rel = "by-model")
    Page<Mobile> findByModel(@Param("model") String model, Pageable pageable);

    @RestResource(rel = "by-imei")
    Optional<Mobile> findByImei(@Param("imei") String imei);

    @RestResource(rel = "by-network-technology")
    Page<Mobile> findByNetworkTechnologiesIn(Collection<Set<NetworkTechnology>> networkTechnologies, Pageable pageable);

    @RestResource(rel = "by-camera-count")
    Page<Mobile> findByCameraCount(@Param("cameraCount") int cameraCount, Pageable pageable);

    @RestResource(rel = "by-core-count")
    Page<Mobile> findByCoreCount(@Param("coreCount") int coreCount, Pageable pageable);

    @RestResource(rel = "by-weight-grams")
    Page<Mobile> findByWeightGrams(@Param("weightGrams") int weightGrams, Pageable pageable);

    @RestResource(rel = "by-battery-capacity-mah")
    Page<Mobile> findByBatteryCapacityMah(@Param("batteryCapacityMah") int batteryCapacityMah, Pageable pageable);

    @RestResource(rel = "by-cost-euro")
    Page<Mobile> findByCostEuro(@Param("costEuro") double costEuro, Pageable pageable);

    @RestResource(exported = false)
    boolean existsBySerialNumber(String serialNumber);

    @RestResource(exported = false)
    boolean existsByImei(String imei);

    void deleteBySerialNumber(String serialNumber);
}