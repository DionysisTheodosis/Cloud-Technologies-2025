package gr.aegean.icsd.mobilemanagement.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WeightGramsValidator.class)
@Documented
public @interface ValidWeightGrams {
    String message() default "Το βάρος πρέπει να είναι θετικός ακέραιος αριθμός";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
