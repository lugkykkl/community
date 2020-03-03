package live.community.community.controller;

import live.community.community.dto.PaginationDTO;
import live.community.community.mapper.UserMapper;
import live.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;


    @GetMapping("/")
    public String index(HttpServletRequest request ,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){

        PaginationDTO pagination = questionService.list(page,size);
            model.addAttribute("pagination",pagination);
        return "index";

    }

}
