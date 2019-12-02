package parksw.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 * author: sinuki
 * createdAt: 2019/12/02
 **/
@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("called home controller");
        return "home";
    }
}
