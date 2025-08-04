package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NetworkTechnologyValidator.class)
@Documented
public @interface ValidNetworkTechnology {
    String message() default "Η δικτυακή τεχνολογία πρέπει να είναι μία ή περισσότερες από: GSM, HSPA, LTE, 3G, 4G, 5G";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
