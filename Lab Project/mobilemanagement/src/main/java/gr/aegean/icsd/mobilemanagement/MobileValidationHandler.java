package gr.aegean.icsd.mobilemanagement;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Set;
@Component
@RepositoryEventHandler
public class MobileValidationHandler {

    private final MobileRepository mobileRepository;
    private final Validator validator;

    public MobileValidationHandler(MobileRepository mobileRepository, Validator validator) {
        this.mobileRepository = mobileRepository;
        this.validator = validator;
    }

    @HandleBeforeCreate
    public void validateBeforeCreate(Mobile mobile) {
        // Βασικός validation με Bean Validation annotations
        Set<ConstraintViolation<Mobile>> violations = validator.validate(mobile);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        // Μοναδικότητα serialNumber και imei
        if (mobileRepository.existsBySerialNumber(mobile.getSerialNumber())) {
            throw new AlreadyExistException("Το κινητό με σειριακό αριθμό " + mobile.getSerialNumber() + " υπάρχει ήδη.");
        }
        if (mobileRepository.existsByImei(mobile.getImei())) {
            throw new AlreadyExistException("Το κινητό με IMEI " + mobile.getImei() + " υπάρχει ήδη");
        }

    }

    @HandleBeforeSave
    public void validateBeforeSave(Mobile mobile) {
        Set<ConstraintViolation<Mobile>> violations = validator.validate(mobile);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @HandleBeforeDelete
    public void validateBeforeDelete(Mobile mobile) {
        if (!mobileRepository.existsBySerialNumber(mobile.getSerialNumber())) {
            throw new MobileNotFoundException("Το κινητό με τον σειριακό αριθμό:  " + mobile.getSerialNumber() + " δεν υπάρχει.");
        }
    }

}
