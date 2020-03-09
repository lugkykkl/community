package live.community.community.controller;


import live.community.community.dto.CommentCreateDTO;
import live.community.community.dto.ResultDTO;
import live.community.community.exception.CustomizeErrorCode;
import live.community.community.model.Comment;
import live.community.community.model.User;
import live.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {




    @Autowired(required = false)
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public  Object post(@RequestBody CommentCreateDTO commentDTO, HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorof(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);
        commentService.insert(comment);

        return ResultDTO.okOF();

    }
}
