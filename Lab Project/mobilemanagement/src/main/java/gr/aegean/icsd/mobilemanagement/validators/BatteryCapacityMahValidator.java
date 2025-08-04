package gr.aegean.icsd.mobilemanagement.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BatteryCapacityMahValidator implements ConstraintValidator<ValidBatteryCapacityMah, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value > 0;
    }
}
