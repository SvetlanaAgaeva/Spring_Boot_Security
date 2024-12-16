package ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.model.Role;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.model.User;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.repository.RoleRepository;
import ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
//    @Autowired
//    private RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //@Transactional
    @RequestMapping
    public String ListOfPeople(Model model) {
//
        List<User> allUsers = userService.getAllUsers();
        User userAuth = userService.getAuthUser();
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("users", allUsers);
        model.addAttribute("user", userAuth);
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("newUser", new User());
        return "new";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


//
//@RequestMapping(method = RequestMethod.POST, value = "/create")
//public String create(@ModelAttribute User user) {
//    userService.saveUser(user); // Сохраняем нового пользователя
//    return "redirect:/admin";
//}
//    @RequestMapping(method = RequestMethod.POST, value = "/edit/{id}")
//    public String editUser(@ModelAttribute User user) {
//        userService.updateUser(user); // Обновляем пользователя
//        return "redirect:/admin"; // Перенаправляем на страницу админа
//    }

//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") Long id, Model model) {
//        try {
//            User user = userService.getUserById(id);
//            Hibernate.initialize(user.getRoles());
//            List<Role> allRoles = roleRepository.findAll();
//            model.addAttribute("user", user);
//            model.addAttribute("allRoles", allRoles);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "error";
//        }
//        return "edit";
//    }

//    @PostMapping("/edit/{id}")
//    public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }


//    @PostMapping("/edit/{id}")
//    public String updateUser(@ModelAttribute User user, @RequestParam("roles") List<Long> roleIds) {
//        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds)); // Получаем роли по их ID
//        user.setRoles(roles); // Устанавливаем роли пользователю
//        userService.updateUser(user); // Сохраняем пользователя
//        return "redirect:/admin";
//    }



}