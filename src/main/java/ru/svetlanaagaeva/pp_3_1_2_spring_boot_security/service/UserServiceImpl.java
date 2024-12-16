package ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.service;

//import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.model.Role;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.model.User;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.repository.RoleRepository;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Transactional
//@Service
//public class UserServiceImpl implements UserService {
//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsernameIgnoreCase(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Incorrect username or password");
//
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), user.getAuthorities());
//    }
//
//    //@Transactional
//    @Override
//    public List<User> getAllUsers() {
//       // return userRepository.findAll();
//        List<User> users = userRepository.findAll();
//        users.forEach(user -> Hibernate.initialize(user.getRoles()));
//        return  users;
//    }
//
//    @Override
//    public List<Role> getAllRoles() {
//        return roleRepository.findAll();
//    }
//
//
//    @Override
//    public User getUserById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User with such ID doesn't exist."));
//
//    }
//
//
//    @Override
//    public void saveUser(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        if (user.getRoles() == null || user.getRoles().isEmpty()) {
//            Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
//            if (userRoleOptional.isPresent()) {
//                user.setRoles(Collections.singleton(userRoleOptional.get()));
//            } else {
//                throw new RuntimeException("Role USER not found");
//            }
//        }
//        userRepository.save(user);
//    }
//
//
//    @Override
//    public void updateUser(User user) {
//
//        User existingUser = getUserById(user.getId());
//        existingUser.setName(user.getName());
//        existingUser.setSurname(user.getSurname());
//        existingUser.setUsername(user.getUsername());
//        if (!existingUser.getPassword().isEmpty()) {
//            // Проверяем, изменился ли пароль
//            if (!bCryptPasswordEncoder.matches(existingUser.getPassword(), user.getPassword())) {
//                user.setPassword(bCryptPasswordEncoder.encode(existingUser.getPassword()));
//            }    //updatedUser.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
//            existingUser.setRoles(user.getRoles());
//            userRepository.save(existingUser);
//
//        }
//    }
//
//    @Override
//    public void deleteUserById(Long id) {
//        User user = userRepository.findById(id).get();
//        user.setRoles(null);
//        userRepository.save(user);
//        userRepository.deleteById(id);
//
//    }
//
//    @Override
//    public User getAuthUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return userRepository.findByUsernameIgnoreCase(auth.getName());
//    }
//}

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException("Incorrect username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }


//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Инициализация ролей пользователей, если они лениво загружаются
        users.forEach(user -> Hibernate.initialize(user.getRoles()));
        return users;
    }


    //  метод кторый работал !
//    @Override
//    public List<Role> getAllRoles() {
//        return roleRepository.findAll();
//    }


         // предложил гпт !!
         @Override
         public List<Role> getAllRoles() {
             List<Role> roles = roleRepository.findAll();
             roles.forEach(role -> System.out.println("Role name: " + role.getName()));
             return roles;
         }

          /// мой метод, который работал !
//    @Override
//    public User getUserById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User with such ID doesn't exist."));
//
//    }

    // гпт сказал
    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with such ID doesn't exist."));

        // Инициализация ролей
        Hibernate.initialize(user.getRoles());

        return user;
    }

    @Override
    public void saveUser(User user) {
        // Хешируем пароль перед сохранением
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Проверяем, есть ли у пользователя роли
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
            if (userRoleOptional.isPresent()) {
                user.setRoles(Collections.singleton(userRoleOptional.get()));
            } else {
                throw new RuntimeException("Role USER not found");
            }
        }

        // Сохраняем пользователя
        userRepository.save(user);
    }
             // метод который меняет пароль
//    @Override
//    public void updateUser(User user) {
//        // Ищем существующего пользователя
//        User existingUser = getUserById(user.getId());
//
//        // Обновляем поля
//        existingUser.setName(user.getName());
//        existingUser.setSurname(user.getSurname());
//        existingUser.setUsername(user.getUsername());
//
//        // Проверяем, если пароль изменился
//        if (!bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
//            // Если пароль изменился, то хешируем новый пароль
//            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        }
//
//        // Обновляем роли пользователя
//        existingUser.setRoles(user.getRoles());
//
//        // Сохраняем обновленного пользователя
//        userRepository.save(existingUser);
//    }

           // метод который по мдее не должен менять пароль
           @Override
           public void updateUser(User user) {

               User existingUser = getUserById(user.getId());
               existingUser.setName(user.getName());
               existingUser.setSurname(user.getSurname());
               existingUser.setUsername(user.getUsername());
                    // гпт сказал добавить
              //existingUser.setRoles(user.getRoles());
                    // гпт сказал добавить
               if (user.getRoles() == null || user.getRoles().isEmpty()) {
                   user.setRoles(existingUser.getRoles());  // Сохраняем роли, если они не переданы
               }
                 // дальше мой код
               if (!existingUser.getPassword().isEmpty()) {
                   // Проверяем, изменился ли пароль
                   if (!bCryptPasswordEncoder.matches(existingUser.getPassword(), user.getPassword())) {
                       user.setPassword(bCryptPasswordEncoder.encode(existingUser.getPassword()));
                   }    //updatedUser.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
                   existingUser.setRoles(user.getRoles());
                   userRepository.save(existingUser);

               }
           }

    @Override
    public void deleteUserById(Long id) {
        // Ищем пользователя по ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with such ID doesn't exist."));

        // Удаляем связи с ролями (если требуется)
        user.setRoles(null);

        // Сохраняем обновленного пользователя перед удалением
        userRepository.save(user);

        // Удаляем пользователя из базы данных
        userRepository.deleteById(id);
    }

    @Override
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsernameIgnoreCase(auth.getName());
    }
}
