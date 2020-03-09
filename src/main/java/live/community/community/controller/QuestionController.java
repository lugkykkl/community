package live.community.community.controller;


import live.community.community.dto.CommentCreateDTO;
import live.community.community.dto.CommentDTO;
import live.community.community.dto.QuestionDTO;
import live.community.community.service.CommentService;
import live.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired(required = false)
    private QuestionService questionService;

    @Autowired(required = false)
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public  String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
