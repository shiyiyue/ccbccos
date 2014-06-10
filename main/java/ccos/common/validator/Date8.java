package pams.common.validator;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-14
 * Time: 16:52:49
 * To change this template use File | Settings | File Templates.
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Date8ConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Date8 {
    String message() default "{����У�����}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
