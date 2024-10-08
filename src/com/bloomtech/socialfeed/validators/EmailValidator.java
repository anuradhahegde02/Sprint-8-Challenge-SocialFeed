package com.bloomtech.socialfeed.validators;

import com.bloomtech.socialfeed.exceptions.EmailValidationException;
import com.bloomtech.socialfeed.exceptions.UserValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    public EmailValidator() {
    }

    private boolean isValidEmail(String email) {
        /*TODO: Validate that email begins with a letter or number, contains only letters, numbers, "." and "_", and
         *that it follows the pattern of name@domain.identifier
         */
        if (email != null) {
            String emailRegEx = "^[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            Pattern emailPattern = Pattern.compile(emailRegEx);
            Matcher emailMatcher = emailPattern.matcher(email);
            if (emailMatcher.find()) {
                return true;
            }

        } else {
            return false;
        }

        return false;
    }

    @Override
    public void validate(Object emailData) {
        String email = (String) emailData;
        if (!isValidEmail(email)) {
            throw new EmailValidationException("Invalid Email: Email address must include '@' before domain and a domain identifier after a '.'!");
        }
    }
}
