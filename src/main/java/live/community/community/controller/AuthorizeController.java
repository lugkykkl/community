package live.community.community.controller;

import live.community.community.dto.AccessTokenDTO;
import live.community.community.dto.GithubUser;
import live.community.community.provide.GithubProvide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state,
                            HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvide.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvide.getUser(accessToken);
        //System.out.println(user.getName());
        if(user != null){
            //登陆成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        } else{
            //登陆失败，重新登陆
            return "redirect:/";
        }

    }


}
