package com.example.controller;

import com.example.model.User;
import com.example.service.RoleServiceImpl;
import com.example.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user-list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUserByID(id));
        return "user-info";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.findAllRoles());
        return "user-create";
    }

    @PostMapping("/new")
    public String create(@Valid User user,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(value = "rolesNames") String[] rolesNames) {
        if (bindingResult.hasErrors() || "*****".equals(user.getPassword())) {
            model.addAttribute("roles", roleService.findAllRoles());
            return "user-create";
        }
        userService.saveUser(user, rolesNames);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String update(Model model, @PathVariable("id") int id) {
        User user = userService.findUserByID(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        model.addAttribute("roles", roleService.findAllRoles());
        return "user-update";
    }

    @PatchMapping("/edit")
    public String update(@Valid User user,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(value = "rolesNames") String[] rolesNames) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRoles());
            model.addAttribute("userRoles", userService.findUserByID(user.getId()).getRoles());
            return "user-update";
        }
        userService.updateUser(user, rolesNames);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
