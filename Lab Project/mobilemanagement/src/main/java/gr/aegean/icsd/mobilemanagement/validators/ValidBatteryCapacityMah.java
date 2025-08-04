package gr.aegean.icsd.mobilemanagement.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BatteryCapacityMahValidator.class)
@Documented
public @interface ValidBatteryCapacityMah {

    String message() default "Η χωρητικότητα μπαταρίας (mAh) πρέπει να είναι θετικός ακέραιος αριθμός";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}