package gr.aegean.icsd.mobilemanagement.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CostEuroValidator.class)
@Documented
public @interface ValidCostEuro {
    String message() default "Το κόστος πρέπει να είναι θετικός αριθμός";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
