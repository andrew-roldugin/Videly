//package ru.vsu.csf.group7.validations;
//
//
//import ru.vsu.cs.group7.forum_dba.annotations.PasswordMatches;
//import ru.vsu.cs.group7.forum_dba.payload.request.SignupRequest;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
//
//    @Override
//    public void initialize(PasswordMatches constraintAnnotation) {
//
//    }
//
//    @Override
//    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
//        return true;
//    }
//
////    @Override
////    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
////        SignupRequest userSignupRequest = (SignupRequest) obj;
////        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
////    }
//}
