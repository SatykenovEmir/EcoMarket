package com.example.academics;

import com.example.academics.model.Product;
import com.example.academics.model.Role;
import com.example.academics.model.User;
import com.example.academics.model.repo.ProductRepository;
import com.example.academics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;

@SpringBootApplication
public class EcoMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoMarketApplication.class, args);
    }

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserService userService;

    @Bean
    public CommandLineRunner bootstrap() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                File userPhotoMan = new File("./src/main/resources/static/img/comment-1.jpg");

                Role role_admin = new Role("ROLE_ADMIN");
                Role role_user = new Role("ROLE_USER");

                User admin = saveUser("Emir", "Satykenov", "123", role_admin);

                User user = saveUser("Yasirov", "Suleyman", "222", role_user);


                File img01 = new File("./src/main/resources/static/images/paper-cups.jpg");
                File img02 = new File("./src/main/resources/static/images/eco_2.jpg");
                File img03 = new File("./src/main/resources/static/images/eco_tarelka.jpg");

                String description = "Remember that every alternative to the usual things can save the world. Thank you for choosing us.";
                String content = """
                            This is a description of the video post. You can also have an image instead of the video. You can free download Xtra Blog Template from TemplateMo website. Phasellus maximus quis est sit amet maximus.""";

                String title1 = "Eco Paper Cup";
                String title2 = "Eco Products - Euro Cloth - Margarita's Cleaning";
                String title3 = "Eco Tarelka";

                String category = "cddcdcdcdcd";
                String price = "7.5";

                saveCourse(title1,description,price,img01);
                saveCourse(title2,description,price,img02);
                saveCourse(title3,description,price,img03);
            }

            private User saveUser(String firstname, String lastname, String username_password, Role role_user) throws IOException {
                    User user = new User().setFirstName(firstname)
                            .setLastName(lastname)
                            .setUsername(username_password)
                            .setPassword(username_password)
                            .setRoles(Set.of(role_user));

                    userService.saveUser(user);
                    return user;

            }


            private void saveCourse(String title,String description, String price,File img01) throws IOException {
                Product blog1;
                try (FileInputStream fileInputStream = new FileInputStream(img01)) {

                    blog1 = new Product()
                            .setTitle(title)
                            .setDescription(description)
                            .setPrice(price)
                            .setCreatedDate(LocalDateTime.now())
                            .setPhoto(fileInputStream.readAllBytes());
                }
                productRepository.save(blog1);
            }
        };
    }

    @Bean("base64encoder")
    public Base64EncoderToString base64() {
        return bytes -> Base64.getEncoder().encodeToString(bytes);
    }

}
interface Base64EncoderToString{
    String encode(byte[] bytes);
}