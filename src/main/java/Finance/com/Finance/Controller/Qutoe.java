package Finance.com.Finance.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.io.IOException;

@Controller
//@RequestMapping("/quote")
public class Qutoe {

    private String shareprice;

    @GetMapping("/quote")
    String Page(){
        return "quote";
    }

    @PostMapping("/qutoe")
    String qutoSubmit(@Valid String shareName, Model model) {
        System.out.println(shareName);
        shareprice = "0";
        Lookup lookup = new Lookup();
        lookup.setShareName(shareName);
        shareprice = lookup.getPrice();
         ///lookup.getSharePrice();
        System.out.println(shareprice);
        return "redirect:"+getPrice(model);
    }

    @GetMapping("/quoted")
    String getPrice(Model model){
        model.addAttribute("sharePrice",shareprice);
        return "quoted";
    }
}
