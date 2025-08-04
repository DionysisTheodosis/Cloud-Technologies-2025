package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SerialNumberValidator implements ConstraintValidator<ValidSerialNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Αλφαριθμητικό (γράμματα ή ψηφία), μήκος ακριβώς 11
        return value != null && value.matches("[a-zA-Z0-9]{11}");
    }
}
