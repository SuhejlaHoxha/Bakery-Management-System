
package com.inn.cafe.JWT;

import com.inn.cafe.dao.UserDao;
import com.inn.cafe.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private com.inn.cafe.model.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", username);
        User userDetail = userDao.findByEmail(username);
        if (userDetail != null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(userDetail.getEmail())
                    .password(userDetail.getPassword())
                    .roles("USER") // Assuming all users have a "USER" role
                    .build();
            return userDetails;
        } else {
            throw new UsernameNotFoundException("User Not Found.");
        }
    }






    public com.inn.cafe.model.User getUserDetail(){
        return userDetail;
    }

}
