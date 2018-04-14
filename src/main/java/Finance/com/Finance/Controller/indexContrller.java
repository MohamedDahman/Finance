package Finance.com.Finance.Controller;

import Finance.com.Finance.persistence.UserRepository;
import Finance.com.Finance.persistence.Users;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Optional;


@Controller
public class indexContrller {

    @Autowired
    HttpSession session;

    @Autowired
    private  UserRepository userRepository;

    /*public indexContrller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
*/
    @GetMapping("/index")
    String Page(Model model){
        Object user_id = session.getAttribute("user");
        if (user_id != null) {
            model.addAttribute("islogin","true");
            return  "index";
        }
        else{
            model.addAttribute("islogin","false");
            return  login(model);
        }
    }

    @GetMapping("/logout")
    String logout(Model model){
        session.removeAttribute("user");
        return "redirect:" + Page(model);
    }

    @GetMapping("/login")
    String login(Model model){
        model.addAttribute("islogin","false");
        return "login";
    }

    @PostMapping("/login")
    String  dologin(@Valid String userName, @Valid String password ,Model model){
        List<Users> all = userRepository.findAll();
        Optional<Users> firstUser = all.stream().filter(e -> e.getUserName().equals(userName)).findFirst();
        if (firstUser.isPresent()){
            // TODO: 4/10/2018 check the password is correct
            session.setAttribute("user",firstUser.get());
            model.addAttribute("islogin","true");
            return "redirect:" + Page(model);
        }
           return "redirect:" +login(model);
        // TODO: 4/10/2018  change to appear error message
      }


    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("islogin","false");
        return "register";
    }

    @PostMapping("/register")
    String registerUser(@Valid String userName, @Valid String password, @Valid String confirmation,Model model){
        session.removeAttribute("user");
        Users users = new Users();
        users.setUserName(userName);
        users.setCash(10000);
        users.setHash(password);
        System.out.println(userName);

        userRepository.save(users);
        session.setAttribute("user",users);
        return "redirect:" + Page(model);
    }


    //@ModelAttribute("videodata")
 //   String getAllVideo(Model model) {

       /* String shareprice="";
        try {
            Lookup lookup = new Lookup();
            lookup.Lookup("fOOG");
            shareprice=lookup.getSharePrice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(shareprice);
       */
   //    model.addAttribute("islogin", "true");
    //   return "index";
    //}

}
