package com.angermannalaget.angermann.auth;

import com.angermannalaget.angermann.repository.RoleRepo;
import com.angermannalaget.angermann.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        super();
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException("Username not present: " + username);
        }
        List<Role> roles = this.roleRepo.findByUsername(username);
        return new UserPrincipal(user, roles);
    }

    public void addUser(@RequestBody User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void addRole(@RequestBody Role role){
        roleRepo.save(role);
    }

    public List<Role> getAllRoles(){
        return roleRepo.findAll();
    }

    public Role getRoleById(String roleId) {
        return roleRepo.findById(roleId).get();
    }

    public void alterRole(String id, String role) {
        Optional<Role> role1 = roleRepo.findById(id);
        if (role1.isPresent()){
            Role r = role1.get();
            r.setRole(role);
            roleRepo.save(r);
        }
    }

    public User getUserByName(String user) {
        return userRepo.findByUserName(user);
    }

    public void deleteUserById(String id) {
        userRepo.deleteById(id);
    }

    public void deleteRoleById(String id){
        roleRepo.deleteById(id);
    }

    public boolean existsByUserName(String userName){
        return userRepo.existsByUserName(userName);
    }

    public boolean existsByRoleId(String roleId) {
        return roleRepo.existsById(roleId);
    }
}
