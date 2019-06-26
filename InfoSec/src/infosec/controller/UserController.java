package infosec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import infosec.model.User;
import infosec.dao.UserMapper;

/**
 * Controller 业务接口
 * ‘@RequestMapping("/users")’用于定义资源的路径
 *  比如：http://localhost:8080/infosec/users指向的资源为UserController的listUser函数
 *  
 * @author infosec
 *
 */
@Controller(value = "userController")
public class UserController {
	
	/**
	 *  Spring 控制反转与依赖注入机制
	 */
	@Autowired
	private UserMapper userMapper;

	/**
	 * 获取所有用户
	 * 
	 * @param model (Spring MVC会为程序初始化model对象，运行时注入)
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(ModelMap model){
		
		try{
			// 从数据库获取所有用户
			List<User> users = userMapper.listUsers();
			
			// 将数据model中，该model有Spring MVC框架维护
			// 在整个request生命周前内有效（下文user_list_servlet中有效）
			model.addAttribute("users", users);
			
			// 对应 /pages/user_list.jsp 对应的页面servlet (user_list_servlet)
			// 即当前request执行转至user_list_servlet执行
			// user_list_servlet利用model中的数据，生成html视图，并返回浏览器该html文件
			return "user_list";
			
		}catch(Exception ex){
			// 如果有任何错误，返回系统错误页面 error.jsp
			// 正式系统中，应该返回更详细的错误信息，帮助用户诊断并采取合适措施
			return "error";
		}

	}
	
	/**
	 * 获取用户详细信息
	 * 
	 * @param id 运行时，spring mvc将从请求的URL中指定部分{id}获取用户提交参数id，并创建与赋值函数参数String id
	 * @param model 注释见上文
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable("id") String id, ModelMap model){
		try{
			User user = userMapper.getUser(id);
			
			model.addAttribute("user", user);
			
			return "user_detail";
			
		}catch(Exception ex){
			return "error";
		}

	}
	
	// getter and setters
	
	public UserMapper getUserMapper() {
		return userMapper;
	}


	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
}
