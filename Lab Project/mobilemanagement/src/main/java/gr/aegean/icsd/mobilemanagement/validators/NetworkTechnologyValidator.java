package gr.aegean.icsd.mobilemanagement.validators;

import java.util.Set;

import gr.aegean.icsd.mobilemanagement.NetworkTechnology;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class NetworkTechnologyValidator implements ConstraintValidator<ValidNetworkTechnology, Set<NetworkTechnology>> {

    @Override
    public boolean isValid(Set<NetworkTechnology> value, ConstraintValidatorContext context) {
        // Null or empty sets are invalid
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Since the set contains enum values, no need to check validity of each value
        // But if you want to be safe, check against enum values explicitly:
        for (NetworkTechnology tech : value) {
            if (tech == null) {
                return false; // no null elements allowed
            }
        }

        return true;
    }
}
