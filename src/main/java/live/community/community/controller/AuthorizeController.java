package live.community.community.controller;

import live.community.community.dto.AccessTokenDTO;
import live.community.community.dto.GithubUser;
import live.community.community.mapper.UserMapper;
import live.community.community.model.User;
import live.community.community.provide.GithubProvide;
import live.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvide githubProvide;
    //将这几个值存在application.properities,用变量替代
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private  String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private UserService userService;



    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code, @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvide.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvide.getUser(accessToken);
        //System.out.println(githubUser.getName());
        if(githubUser != null &&githubUser.getId() != null){
            User user = new User();
           //System.out.println(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            //user.setGmtCreate(System.currentTimeMillis());
           // user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
           // System.out.println();
            //userMapper.insert(user);
            response.addCookie(new Cookie("token",token));

            //登陆成功，写cookie和session
           // request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        } else{
            //登陆失败，重新登陆
            return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }


}
