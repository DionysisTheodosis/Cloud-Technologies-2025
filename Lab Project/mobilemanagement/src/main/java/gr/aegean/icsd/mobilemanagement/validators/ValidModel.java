package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ModelValidator.class)
@Documented
public @interface ValidModel {
    String message() default "Το μοντέλο πρέπει να έχει τουλάχιστον 2 αλφαριθμητικούς χαρακτήρες";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

