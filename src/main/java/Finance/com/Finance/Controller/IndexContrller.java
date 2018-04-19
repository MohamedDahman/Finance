/**************************************************
 * Finance
 *
 *
 * code written by: Mohamed Dahman
 *
 * This program for buy and sell shares .
 *
 ***************************************************/

package Finance.com.Finance.Controller;

import Finance.com.Finance.persistence.*;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Controller
public class IndexContrller {

    @Autowired
    HttpSession session;

    @Autowired
    LoginService loginService;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    Lookup lookup;

    /*public IndexContrller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
*/
    @GetMapping("/")
    String PageIndex(Model model){
        if (loginService.checkIsanyOneLogin() == true){
            model.addAttribute("islogin","true");
            return  "index";
        }
        else
        {
            model.addAttribute("islogin","false");
            return  login(model);
        }
    }

    @GetMapping("/index")
    String Page(Model model){
        if (loginService.checkIsanyOneLogin() == true){
            Object user = session.getAttribute("user");
            Users user1 = (Users) user;
            model.addAttribute("islogin","true");
            Set<Portfolio> portfolioSet = portfolioRepository.findAll()
                    .stream()
                    .filter(e -> e.getUser().getId() == user1.getId())
                    .collect(Collectors.toSet());
            portfolioSet.stream().forEach(e->e.setPrice(Double.parseDouble(lookup.getPrice(e.getSymbol()))));
            model.addAttribute("shares",portfolioSet);
            model.addAttribute("cash",user1.getCash());
            Optional<Double> reduce = portfolioSet.stream().map(e -> e.getPrice() * e.getQuntity()).reduce((e1, e2) -> e1 + e2);
            if (reduce.isPresent()){
                model.addAttribute("total",reduce.get()+user1.getCash());
            }
            else{
                model.addAttribute("total",user1.getCash());

            }
            return  "index";
        }
        else
        {
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
            loginService.chnageLogin(firstUser.get());
            //session.setAttribute("user",);

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
