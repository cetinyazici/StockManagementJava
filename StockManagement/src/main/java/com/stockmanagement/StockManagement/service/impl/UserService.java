package com.stockmanagement.StockManagement.service.impl;

import com.stockmanagement.StockManagement.dto.CreateUserRequest;
import com.stockmanagement.StockManagement.model.User;
import com.stockmanagement.StockManagement.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(CreateUserRequest request) {
        User newUser = new User();
        newUser.setName(request.name());
        newUser.setUsername(request.username());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setAuthorities(request.authorities());
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);
        newUser.setAccountNonLocked(true);
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = getByUsername(username);
        return user.orElseThrow(EntityExistsException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean updateProfile(User user, String oldPassword, String newPassword) {
        Optional<User> existingUser = getByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            // Eski şifreyi kontrol et
            if (passwordEncoder.matches(oldPassword, updatedUser.getPassword())) {
                updatedUser.setName(user.getName());

                // Yeni şifreyi kontrol et ve şifreleme işlemi yap
                if (newPassword != null && !newPassword.isEmpty()) {
                    updatedUser.setPassword(passwordEncoder.encode(newPassword));
                }
                save(updatedUser);
                return true;
            }
        }
        return false;
    }

}
