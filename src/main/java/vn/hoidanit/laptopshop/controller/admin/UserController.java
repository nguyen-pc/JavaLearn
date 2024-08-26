package vn.hoidanit.laptopshop.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class UserController {
    private final UploadService uploadService;

    private final UserService userService;
    private final UserRepository userRepository;
    private final ServletContext servletContext;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository,
            UploadService uploadService,
            ServletContext servletContext) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.uploadService = uploadService;
        this.servletContext = servletContext;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUser = this.userService.getAllUserByEmail("nguyenthcs430@gmail.com");
        System.out.println(arrUser);
        String test = this.userService.handleHello();
        return "hello";
    }

    @RequestMapping("/admin/user/create")
    public String getUserPageCreate(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        // System.out.println("Check user" + users);
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        // System.out.println(user);
        model.addAttribute("user", user);
        model.addAttribute("id", id);

        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdate(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/create1")
    public String createUserPage(Model model, @ModelAttribute("newUser") @Valid User hoidanit,
            BindingResult NewUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = NewUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println("<<<<<" + error.getField() + "-" + error.getDefaultMessage());
        }
        // validate date
        if (NewUserBindingResult.hasErrors()) {
            return "/admin/user/create";
        }

        //
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());
        System.out.println("Run heare" + hoidanit);
        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));
        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    @PostMapping("/admin/user/update")
    public String postUserUpdate(Model model, @ModelAttribute("newUser") User hoidanit) {
        User currentUser = this.userService.getUserById(hoidanit.getId());
        if (currentUser != null) {
            currentUser.setAddress(hoidanit.getAddress());
            currentUser.setFullName(hoidanit.getFullName());
            currentUser.setPhone(hoidanit.getPhone());

            this.userService.handleSaveUser(currentUser);
        }
        // model.addAttribute("newUser", currentUser);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User user) {
        this.userService.deleteUser(user.getId());
        return "redirect:/admin/user";
    }

}

// @RestController

// public class UserController {
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")

// public String getHomePage(){
// return this.userService.handleHello() ;
// }
// }
