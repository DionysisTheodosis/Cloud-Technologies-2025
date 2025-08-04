package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class ImeiValidator implements ConstraintValidator<ValidImei, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("\\d{15}");
    }
}
