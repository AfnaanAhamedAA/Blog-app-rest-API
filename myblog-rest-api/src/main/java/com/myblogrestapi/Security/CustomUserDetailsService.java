package com.myblogrestapi.Security;

import com.myblogrestapi.entity.Roles;
import com.myblogrestapi.entity.User;
import com.myblogrestapi.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
  private userRepository userRepo;



    @Override
    public UserDetails loadUserByUsername(String usernameoremail) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameOrEmail(usernameoremail, usernameoremail).orElseThrow(() -> new UsernameNotFoundException("user not found with username or email:" + usernameoremail));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),maptoAuthorities(user.getRoles()));
    }

     private Collection<? extends GrantedAuthority> maptoAuthorities(Set<Roles> roles){
         List<SimpleGrantedAuthority> collectedRoles = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
         return collectedRoles;
     }

}
