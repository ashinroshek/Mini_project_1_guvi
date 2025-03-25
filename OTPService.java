package codingtechniques.service;


import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {

    private final Map<String, String> otpStorage = new HashMap<>(); 

  
    public String generateOTP() {
        return RandomStringUtils.randomNumeric(6);
    }

 
    public void storeOTP(String email, String otp) {
        otpStorage.put(email, otp);
    }

   
    public boolean validateOTP(String email, String otp) {
        String storedOTP = otpStorage.get(email);
        return storedOTP != null && storedOTP.equals(otp);
    }
}
