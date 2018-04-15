package Finance.com.Finance.Controller;


import Finance.com.Finance.persistence.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class LoginService {


    @Autowired
    HttpSession session;


    Boolean checkIsanyOneLogin() {
        Boolean login = false;
        Object user_id = session.getAttribute("user");
        if (user_id != null) {
            login = true;
        }
        return login;
    }

    void chnageLogin(Users user){
        session.setAttribute("user",user);
    }




    }
