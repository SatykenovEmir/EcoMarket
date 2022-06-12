package com.example.academics.controller;


import com.example.academics.model.Product;
import com.example.academics.model.Role;
import com.example.academics.model.User;
import com.example.academics.model.repo.ProductRepository;
import com.example.academics.model.repo.UserRepository;
import com.example.academics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @GetMapping(
            produces = MediaType.IMAGE_JPEG_VALUE,
            path = "course-img/{courseId}")
    @ResponseBody
    public byte[] getImage(@PathVariable("courseId") Long blogId,HttpServletResponse response) {
        return productRepository.findById(blogId).orElseThrow().getPhoto();
    }

    @GetMapping("courses")
    public String home(Model model) {
        model.addAttribute("courses", productRepository.findAll());
        return "courses";
    }
    @GetMapping("users")
    public String user(Model model) {
        model.addAttribute("users",userRepository.findAll());
        return "users";
    }


    @GetMapping("course/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute(product);

        return "post";
    }

    @GetMapping("addCourse")
    public String createPost(Model model) {
        Product newProduct = new Product().setCreatedDate(LocalDateTime.now());
        model.addAttribute("newCourse", newProduct);
        return "addCourse";
    }

    @GetMapping("editCourse/{id}")
    public String editPost(Model model,@PathVariable long id) {
        Product newProduct = productRepository.findById(id).orElseThrow();
        model.addAttribute("newCourse", newProduct);
        return "addCourse";
    }
    @GetMapping("deleteCourse/{id}")
    public String deletePost(@PathVariable long id) {
        productRepository.deleteById(id);
        return "redirect:/courses";
    }

    @PostMapping(value = "savecourse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String savePost(
            @ModelAttribute Product product,
            @RequestPart("photofile") MultipartFile photo,
            Principal principal
    ) {
        productRepository.save(product);
        try {
            product.setPhoto(photo.getBytes());
            productRepository.save(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/courses";
    }

    @GetMapping("adduser")
    public String createUser(Model model) {
        User newUser = new User();
        model.addAttribute("newUser",newUser);
        return "adduser";
    }

    @PostMapping(value = "saveuser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveUser(
            @ModelAttribute User user,
            Principal principal
    ) {
        Role role_user = new Role("ROLE_USER");
        user.setRoles(Set.of(role_user));
        userService.saveUser(user);
        return "redirect:/";
    }


//    @GetMapping("updateCourse/delete/{id}")
//    public String deleteCourse(@PathVariable int id) {
//        Service.removeProductById(id);
//        return "redirect:courses";
//    }

//    @GetMapping("/admin/product/update/{id}")
//    public String updateProductGet(@PathVariable long id, Model model) {
//        Product product = productService.getProductById(id).get();
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setId(product.getId());
//        productDTO.setName(product.getName());
//        productDTO.setCategoryId((product.getCategory().getId()));
//        productDTO.setPrice(product.getPrice());
//        productDTO.setWeight((product.getWeight()));
//        productDTO.setDescription(product.getDescription());
//        productDTO.setImageName(product.getImageName());
//
//        model.addAttribute("categories",categoryService.getAllCategory());
//        model.addAttribute("productDTO",productDTO);
//
//
//        return "productsAdd";
//    }

    @GetMapping("admin")
    @ResponseBody
    public String adminPanel() {
        return "admin";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }


    @GetMapping("contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("admissions")
    public String admissions() {
        return "admissions";
    }

}
