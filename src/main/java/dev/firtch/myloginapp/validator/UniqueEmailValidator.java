package dev.firtch.myloginapp.validator;

import dev.firtch.myloginapp.service.ProfileService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

   private final ProfileService profileService;

   public UniqueEmailValidator(ProfileService profileService) {
      this.profileService = profileService;
   }

   public void initialize(UniqueEmail constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
      return !profileService.findByEmail(obj).isPresent();
   }
}
