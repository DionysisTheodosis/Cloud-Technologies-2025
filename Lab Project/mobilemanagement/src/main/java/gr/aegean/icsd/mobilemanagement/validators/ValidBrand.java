package gr.aegean.icsd.mobilemanagement.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BrandValidator.class)
@Documented
public @interface ValidBrand {
    String message() default "Η μάρκα πρέπει να έχει τουλάχιστον 2 γράμματα και να περιέχει μόνο γράμματα";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
