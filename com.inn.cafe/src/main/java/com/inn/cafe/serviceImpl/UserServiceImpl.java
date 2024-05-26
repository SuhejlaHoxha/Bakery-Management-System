package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.CustomerUsersDetailsService;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.JWT.JwtUtil;
import com.inn.cafe.model.User;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;

import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Slf4j

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            // Extract user details from requestMap
            String name = requestMap.get("name");
            String contactNumber = requestMap.get("contactNumber");
            String email = requestMap.get("email");
            String password = requestMap.get("password");

            // Hash the password
            String hashedPassword = passwordEncoder.encode(password);

            // Check if the request map is valid
            if (validateSignUpMap(requestMap)) {
                // Check if the email already exists
                User existingUser = userDao.findByEmail(email);
                if (existingUser == null) {
                    // Create a new User object
                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setContactNumber(contactNumber);
                    newUser.setEmail(email);
                    newUser.setPassword(hashedPassword);
                    newUser.setRole("user");
                    newUser.setStatus("false"); // Assuming status should be set to false initially

                    // Save the user
                    userDao.save(newUser);

                    // Return success response
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    // Return error response if email already exists
                    return CafeUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                // Return error response if request data is invalid
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // Log any exceptions and return internal server error response
            e.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login method");

        try {
            String email = requestMap.get("email");
            String rawPassword = requestMap.get("password");

            log.info("Attempting to authenticate user: {}", email);

            if (email == null || rawPassword == null || email.isEmpty() || rawPassword.isEmpty()) {
                return new ResponseEntity<>("{\"message\":\"Email and password cannot be empty.\"}", HttpStatus.BAD_REQUEST);
            }

            // Fetch user details
            User user = userDao.findByEmail(email);

            if (user == null) {
                log.warn("User not found with email: {}", email);
                return new ResponseEntity<>("{\"message\":\"User does not exist.\"}", HttpStatus.BAD_REQUEST);
            }

            // Check if password matches
            log.debug("Encoded password from DB: {}", user.getPassword());
            if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
                log.warn("Invalid password for user: {}", email);
                return new ResponseEntity<>("{\"message\":\"Incorrect password.\"}", HttpStatus.UNAUTHORIZED);
            }

            if (!"true".equalsIgnoreCase(user.getStatus())) {
                log.warn("User account not approved: {}", email);
                return new ResponseEntity<>("{\"message\":\"Wait for admin approval.\"}", HttpStatus.FORBIDDEN);
            }

            // Authenticate the user using Spring Security Authentication Manager
            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, rawPassword)
                );

                if (auth.isAuthenticated()) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    String jwtToken = jwtUtil.generateToken(String.valueOf(userDetails), user.getRole());

                    log.info("User authenticated successfully: {}", email);
                    return new ResponseEntity<>("{\"token\":\"" + jwtToken + "\"}", HttpStatus.OK);
                } else {
                    log.warn("Authentication failed for user: {}", email);
                    return new ResponseEntity<>("{\"message\":\"Bad credentials.\"}", HttpStatus.UNAUTHORIZED);
                }
            } catch (BadCredentialsException ex) {
                log.error("Invalid credentials for user: {}", email, ex);
                return new ResponseEntity<>("{\"message\":\"Invalid credentials.\"}", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            log.error("Exception during login", ex);
            return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
// <<<<<<< 20-update-api-of-user

     @Override
     public ResponseEntity<String> update(Map<String, String> requestMap) {
         try {
             if(jwtFilter.isAdmin()){
               Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
               if (!optional.isEmpty()) {
                   userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                   sendMailtoAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                   return CafeUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
               } else {
                   return CafeUtils.getResponseEntity("User id doesn't not exist", HttpStatus.OK);
               }
             } else {
                 return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
             }

         }catch (Exception ex){
             ex.printStackTrace();
         }
         return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
     }



    private void sendMailtoAllAdmin(String status, String user, List<String> allAdmin) {
         allAdmin.remove(jwtFilter.getCurrentUser());
         if(status!= null && status.equalsIgnoreCase("true")) {
             emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:- "+user+" \n is approved by \nADMIN:-"+ jwtFilter.getCurrentUser(), allAdmin);

         }else {
             emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "USER:- "+user+" \n is disabled by \nADMIN:-"+ jwtFilter.getCurrentUser(), allAdmin);

         }
     }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return CafeUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User user = userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Management System", user.getPassword());
            return CafeUtils.getResponseEntity("Check your email for Credentials", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
// =======
// }
// >>>>>>> develop
