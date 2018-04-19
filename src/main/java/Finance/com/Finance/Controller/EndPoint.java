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
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class EndPoint {

    private String shareprice;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    IndexContrller indexContrller;

    @Autowired
    HttpSession session;

    @Autowired
    LoginService loginService;

    @Autowired
    Lookup lookup;

  /*
    @Before("/quote")
    String dosomething(Model model){
        if (loginService.checkIsanyOneLogin()){
            return "redirect:"+indexContrller.login(model);
        }
        else
        {
            return " err";
        }

    }
*/



    @GetMapping("/quote")
    String Page(Model model){
        if (loginService.checkIsanyOneLogin()){
            model.addAttribute("islogin","true");
            return "quote";
        }
        else
        {
            return "redirect:"+indexContrller.login(model);
        }
    }

    @PostMapping("/qutoe")
    String qutoSubmit(@Valid String shareName, Model model) {
        if (loginService.checkIsanyOneLogin() == true){
            model.addAttribute("islogin","true");
            shareprice = lookup.getPrice(shareName);
            return "redirect:"+getPrice(model);
        }
        else{
            model.addAttribute("islogin","false");
            IndexContrller indexContrller = new IndexContrller();
            return  indexContrller.login(model);
        }

   /*     Object user_id = session.getAttribute("user");
        if (user_id != null) {
            model.addAttribute("islogin","true");
            shareprice = "0";
            Lookup lookup = new Lookup();
            lookup.setShareName(shareName);
            shareprice = lookup.getPrice();
            ///lookup.getSharePrice();
            System.out.println(shareprice);
            return "redirect:"+getPrice(model);
            }
        else{
            model.addAttribute("islogin","false");
            IndexContrller indexContrller = new IndexContrller();
            return  indexContrller.login(model);
        }
*/
    }

    @GetMapping("/quoted")
    String getPrice(Model model){
        model.addAttribute("islogin","true");
        model.addAttribute("sharePrice",shareprice);
        return "quoted";
    }

    @GetMapping("/buy")
    String showbuyShare(Model model){
        if (loginService.checkIsanyOneLogin()){
            model.addAttribute("islogin","true");
            return "buy";
        }
        else
        {
            return "redirect:"+indexContrller.login(model);
        }

    }
    @GetMapping("/sell")
    String showsellShare(Model model){
/*
        Set<String> collect = shareRepository.findAll().stream().map(e -> e.getName()).collect(Collectors.toSet());
        model.addAttribute("shares",collect );
*/

        if (loginService.checkIsanyOneLogin()){
            model.addAttribute("islogin","true");
            return "sell";
        }
        else
        {
            return "redirect:"+indexContrller.login(model);
        }

    }

    @PostMapping("/buy")
    String buyShare(Model model,@Valid String symbol, @Valid String shares){
        String price = lookup.getPrice(symbol);
        System.out.println(price);
        Object user = session.getAttribute("user");
        Users user1 = (Users) user;
        Optional<Users> byId = userRepository.findById(user1.getId());
        if (byId.isPresent()){
            System.out.println(byId.get().getCash());
            double total = Double.parseDouble(price) * Integer.parseInt(shares);

            double cash = user1.getCash();
            double newcash = cash-total;
            user1.setCash(newcash);
            userRepository.save(user1) ;

            Share newshare = new Share();
            newshare.setMoveType(MoveType.buying);
            newshare.setName(symbol);
            newshare.setPrice(Double.parseDouble(price));
            newshare.setQuntity(Integer.parseInt(shares));
            newshare.setSymbol(symbol);
            newshare.setUsers(byId.get());
            shareRepository.save(newshare);


            Optional<Portfolio> first = portfolioRepository.findAll()
                    .stream()
                    .filter(e -> e.getUser().getId() == user1.getId())
                    .filter(e -> e.getSymbol().toString().equalsIgnoreCase(symbol))
                    .findFirst();
            if (first.isPresent()){
                Portfolio portfolio = first.get();
                long currentquntity = portfolio.getQuntity();
                currentquntity += Long.parseLong(shares);
                portfolio.setQuntity(currentquntity);
                portfolioRepository.save(portfolio);
            }
            else
            {
                Portfolio portfolio = new Portfolio();
                portfolio.setName(symbol);
                portfolio.setSymbol(symbol);
                portfolio.setQuntity(Long.parseLong(shares));
                portfolio.setUser(byId.get());
                portfolioRepository.save(portfolio);
            }

        }
        return "redirect:"+indexContrller.Page(model);
        //return "index";
    }

    @PostMapping("/sell")
    String sellShare(Model model,@Valid String symbol, @Valid String quntity){

        String price = lookup.getPrice(symbol);
        System.out.println(price);
        Object user = session.getAttribute("user");
        Users user1 = (Users) user;
        Optional<Users> byId = userRepository.findById(user1.getId());
        if (byId.isPresent()){
            System.out.println(byId.get().getCash());

            double total = Double.parseDouble(price) * Integer.parseInt(quntity);

            double cash = user1.getCash();
            double newcash = cash-total;
            user1.setCash(newcash);
            userRepository.save(user1) ;

            Share newshare = new Share();
            newshare.setMoveType(MoveType.selling);
            newshare.setName(symbol);
            newshare.setPrice(Double.parseDouble(price));
            newshare.setQuntity(Integer.parseInt(quntity));
            newshare.setSymbol(symbol);
            newshare.setUsers(byId.get());
            shareRepository.save(newshare);


            Optional<Portfolio> first = portfolioRepository.findAll()
                    .stream()
                    .filter(e -> e.getUser().getId() == user1.getId())
                    .filter(e -> e.getSymbol().toString().equalsIgnoreCase(symbol))
                    .findFirst();
            if (first.isPresent()){
                Portfolio portfolio = first.get();
                long currentquntity = portfolio.getQuntity();
                currentquntity -= Long.parseLong(quntity);
                portfolio.setQuntity(currentquntity);
                portfolioRepository.save(portfolio);
            }
            else
            {
                Portfolio portfolio = new Portfolio();
                portfolio.setName(symbol);
                portfolio.setSymbol(symbol);
                portfolio.setQuntity(Long.parseLong(quntity));
                portfolio.setUser(byId.get());
                portfolioRepository.save(portfolio);
            }

        }

        return "redirect:"+indexContrller.Page(model);

//        return "index";
    }

}
