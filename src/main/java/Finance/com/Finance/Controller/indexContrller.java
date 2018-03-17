package Finance.com.Finance.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/index")
public class indexContrller {

    @GetMapping
    String Page(){
        return  "index";
    }

    @ModelAttribute("videodata")
    String getAllVideo(Model model){
        model.addAttribute("islogin","true");
        return "index";
    }

}
