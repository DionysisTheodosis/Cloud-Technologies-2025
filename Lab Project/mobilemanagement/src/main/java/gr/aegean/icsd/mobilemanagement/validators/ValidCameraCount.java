package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CameraCountValidator.class)
@Documented
public @interface ValidCameraCount {
    String message() default "Ο αριθμός καμερών πρέπει να είναι μεταξύ 1 και 3";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

