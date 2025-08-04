package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImeiValidator.class)
@Documented
public @interface ValidImei {
    String message() default "Ο αριθμός IMEI πρέπει να είναι 15 ψηφία";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
