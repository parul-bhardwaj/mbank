package annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class RoleValidator implements ConstraintValidator<Roles, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {


        return false;
    }
}
