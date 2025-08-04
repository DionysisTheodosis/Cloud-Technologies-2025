package gr.aegean.icsd.mobilemanagement.validators;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidator;
public class CameraCountValidator implements ConstraintValidator<ValidCameraCount, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= 1 && value <= 3;
    }
}
