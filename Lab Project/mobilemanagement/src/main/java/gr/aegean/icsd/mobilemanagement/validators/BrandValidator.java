package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class BrandValidator implements ConstraintValidator<ValidBrand, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[a-zA-Z]{2,}");
    }
}

