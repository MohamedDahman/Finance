package Finance.com.Finance.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quote")
public class Qutoe {

    @GetMapping
    @ModelAttribute("videodata")
    String Page(Model model){
        model.addAttribute("islogin","true");
        return "quote";
    }






}
