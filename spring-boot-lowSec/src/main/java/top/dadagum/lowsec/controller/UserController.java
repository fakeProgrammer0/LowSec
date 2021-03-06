package top.dadagum.lowsec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.dadagum.lowsec.dao.UserMapper;
import top.dadagum.lowsec.domain.CustomUser;
import top.dadagum.lowsec.domain.Roles;
import top.dadagum.lowsec.domain.User;
import top.dadagum.lowsec.utils.AuthenticationFacade;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.List;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 11:22
 **/
@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String hello(ModelMap modelMap) {
        Authentication info = userInfo.getAuthentication();
        String username = info == null ? "world" : info.getName();
        modelMap.addAttribute("username", username);
        return "hello";
    }

    @Autowired
    private AuthenticationFacade userInfo;

    /**
     * 获取所有用户
     *
     * @param model (Spring MVC会为程序初始化model对象，运行时注入)
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public String listUsers(ModelMap model){
        try{
            // 从数据库获取所有用户
            List<User> users = userMapper.listUsers();
//            System.out.println(users);
            // 将数据model中，该model有Spring MVC框架维护
            // 在整个request生命周前内有效（下文user_list_servlet中有效）
            model.addAttribute("users", users);
            // 对应 /pages/user_list.html.jsp 对应的页面servlet (user_list_servlet)
            // 即当前request执行转至user_list_servlet执行
            // user_list_servlet利用model中的数据，生成html视图，并返回浏览器该html文件
            return "user_list.html";
        }catch(Exception ex){
            // 如果有任何错误，返回系统错误页面 error.jsp
            // 正式系统中，应该返回更详细的错误信息，帮助用户诊断并采取合适措施
            ex.printStackTrace();
            return "/error";
        }
    }

    /**
     * 获取用户详细信息
     * @param id 运行时，spring mvc将从请求的URL中指定部分{id}获取用户提交参数id，并创建与赋值函数参数String id
     * @param model 注释见上文
     * @return
     */
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") String id, ModelMap model){
        Authentication info = userInfo.getAuthentication();
        if (!(info instanceof AnonymousAuthenticationToken)) {
            CustomUser user = (CustomUser) info.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = info.getAuthorities();
            boolean sign = false;
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(Roles.ROLE_ADMIN.getRole())) {
                    sign = true;
                    break;
                }
            }
            id = sign ? id : user.getId() + "";
        }

        try{
            User user = userMapper.getUser(id);
            model.addAttribute("user", user);
            return "user_detail";
        }catch(Exception ex){
            ex.printStackTrace();
            return "/error";
        }
    }
    
}
