package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SerialNumberValidator.class)
@Documented
public @interface ValidSerialNumber {
    String message() default "Ο σειριακός αριθμός πρέπει να είναι ακριβώς 11 αλφαριθμητικοί χαρακτήρες";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
