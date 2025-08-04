package gr.aegean.icsd.mobilemanagement.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoreCountValidator.class)
@Documented
public @interface ValidCoreCount {
    String message() default "Ο αριθμός πυρήνων πρέπει να είναι τουλάχιστον 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
