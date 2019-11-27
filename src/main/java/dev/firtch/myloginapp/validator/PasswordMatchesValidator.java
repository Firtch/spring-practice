package dev.firtch.myloginapp.validator;

import dev.firtch.myloginapp.model.Profile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
   public void initialize(PasswordMatches constraint) {
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      final Profile profile = (Profile) obj;

      boolean isValid = profile.getPassword().equals(profile.getConfirmPassword());
      if (!isValid) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                 .addPropertyNode("confirmPassword").addConstraintViolation();
      }
      return isValid;
   }
}
