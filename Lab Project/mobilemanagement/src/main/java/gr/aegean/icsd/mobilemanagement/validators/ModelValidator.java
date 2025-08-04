package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class ModelValidator implements ConstraintValidator<ValidModel, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[a-zA-Z0-9]{2,}");
    }
}
