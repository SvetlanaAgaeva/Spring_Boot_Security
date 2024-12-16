package ru.svetlanaagaeva.pp_3_1_2_spring_boot_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Pp312SpringBootSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(Pp312SpringBootSecurityApplication.class, args);


                 // Этот код мне нужен для проверки шифрования

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//        String rawPasswordAdmin = "admin";  // пароль,который я проверяю
//        String hashedPasswordAdmin = encoder.encode(rawPasswordAdmin);
//        System.out.println("Hashed password: " + hashedPasswordAdmin); // посмотреть,что получилось

                        // или так
//        String rawPassword = "admin";
//        String encodedPassword = encoder.encode(rawPassword);
//        System.out.println(encodedPassword);

//   String storedPassword = "2a$10$O8fNun70rxth9rGa8ZexfOpc6r2O3IB9OKj48ecsmdLbcc6aU7O0G";  // зашифрованный пароль из базы данных
//
//                     Проверка пароля
//        boolean passwordMatches = encoder.matches(rawPassword, storedPassword);
//        System.out.println("Пароль совпадает: " + passwordMatches);
    }

}
