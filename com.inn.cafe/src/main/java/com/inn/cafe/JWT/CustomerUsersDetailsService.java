package com.inn.cafe.JWT;

import com.inn.cafe.dao.UserDao;
import com.inn.cafe.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private User userDetail;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Add PasswordEncoder bean

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", username);
        userDetail = userDao.findByEmailId(username);
        if (!Objects.isNull(userDetail)) {
            // Hash the password if not already hashed
            String hashedPassword = passwordEncoder.encode(userDetail.getPassword());
            return org.springframework.security.core.userdetails.User
                    .withUsername(userDetail.getEmail())
                    .password(hashedPassword)
                    .authorities(new ArrayList<>())
                    .build();
        } else {
            throw new UsernameNotFoundException("User Not Found.");
        }
    }

    public User getUserDetail() {
        return userDetail;
    }
}
