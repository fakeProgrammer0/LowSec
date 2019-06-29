## 业务安全

### spring security 关键配置类

1. 继承 WebSecurityConfigurerAdapter，并重写 configure 方法。
2. 在 configure 方法中，可以配置：
   - url 权限控制，例如一些 web 页面我们可以只允许系统管理员访问，一些页面只允许登陆用户访问，而另一些页面允许没有登陆的用户访问。
   - 指定认证方式，例如基本认证、表单认证等等。
3. 本次项目中，关键配置如下：

```java
@Override
protected void configure(HttpSecurity http) throws Exception
{
    http
        .authorizeRequests()
        .antMatchers("/", "/index").permitAll()
        .antMatchers("/users").hasRole("ADMIN")
        .anyRequest().authenticated()
    .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
    .and()
        .logout()
        .permitAll();
}
```

4. 此外，该项目采用了 thymeleaf 的模板引擎，对于一些特定的 url 例如 "/"，"/index"，"/login"，我们需要返回对应的 html 页面。一般都会在 controller 中编写对应的方法，方法返回一个 View，但是有更简单的配置：

```java
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

}
```

### 获取登陆用户的信息

1. spring security 中将登陆用户的信息交给 SecurityContextHolder 管理，因此可以通过 SecurityContextHolder 来获取登陆用户的信息。

```java
SecurityContextHolder.getContext().getAuthentication();
```

### RBAC 实现

1. 在上一小节中已经实现了“仅管理员可以访问获取系统用户列表的接口”和所有用户都可以访问 index 和 login 页面，其它的所有页面都需要经过验证的步骤。可以看到使用 spring security 非常简单。

```java
http
    .authorizeRequests()
    .antMatchers("/", "/index").permitAll()
    .antMatchers("/users").hasRole("ADMIN")
    .anyRequest().authenticated()
```

​	所有用户都可以访问 index 页面：

![](https://zhongyuhtml.oss-cn-shenzhen.aliyuncs.com/practice-points/images/lianghongda/info/index.PNG)

​	只有管理员可以调用 /users 接口：

![](https://zhongyuhtml.oss-cn-shenzhen.aliyuncs.com/practice-points/images/lianghongda/info/users.PNG)



2. 对于“查看用户详细信息”接口，由于登陆用户都可以访问，只是调用接口可获得数据的集合不一样。例如，对于管理员用户来说，可以访问到任意一个用户的信息；而对于一般用户，只能访问到自己的信息，这一部分就不能直接配置了。为了业务处理的更加灵活，直接在业务层中进行这方面的权限判断。

```java
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") String id, ModelMap model){
        Authentication info = SecurityContextHolder.getContext().getAuthentication();
        if (!(info instanceof AnonymousAuthenticationToken)) {
            CustomUser user = (CustomUser) info.getPrincipal(); // 获取当前登陆用户的信息
            // 获取用户的权限
            Collection<? extends GrantedAuthority> authorities = info.getAuthorities();
            // 判断用户是否为 ROLE_ADMIN 角色
            boolean sign = false;
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(Roles.ROLE_ADMIN.getRole())) {
                    sign = true;
                    break;
                }
            }
            // 如果是 ROLE_ADMIN，根据要求不对查看的用户 id 设限，因此可以保留原来id
            // 如果不是，那么只可以查看自己的信息，因此将自己的 id 覆盖 id 变量
            id = sign ? id : user.getId() + "";
        }
        
        // 根据 id 查找数据库获取用户信息并返回
        // ...
}
```

​	管理员调用 /user/5040 接口（5040 为用户 test 的 id），可以正常返回：

![](https://zhongyuhtml.oss-cn-shenzhen.aliyuncs.com/practice-points/images/lianghongda/info/user_5040.PNG)

​	普通用户 mulan 调用 /user/5040 接口，返回的只是自己的信息：

![](https://zhongyuhtml.oss-cn-shenzhen.aliyuncs.com/practice-points/images/lianghongda/info/mulan_5040.PNG)

### 订制错误页面

1. 我们可以根据实际需求，订制对应的系统错误页面，以本实验为例子，订制简单的 error.html，403.html，404.html 页面，实现也十分简单，只需要实现 ErrorController，在发生错误时通过 Http 状态码来判断跳转页面：

```java
@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.FORBIDDEN.value()) { // 没有权限访问，跳转 403.html
                return "403";
            } else if(statusCode == HttpStatus.NOT_FOUND.value()) { // 页面不存在，跳转 404.html
                return "404";
            }
        }
        return "error"; // 其它错误都默认返回 error.html
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
```

​	订制的 403 页面（普通用户权限不够）：

![](https://zhongyuhtml.oss-cn-shenzhen.aliyuncs.com/practice-points/images/lianghongda/info/users_403.PNG)

​	订制的 404 页面：

![](https://zhongyuhtml.oss-cn-shenzhen.aliyuncs.com/practice-points/images/lianghongda/info/404.PNG)

## sql 注入

1. SQL注入攻击指的是通过构建特殊的输入作为参数传入Web应用程序，而这些输入大都是SQL语法里的一些组合，通过执行SQL语句进而执行攻击者所要的操作。假设在 java 程序中有如下代码：

```java
public User login(String username, String password) {
    // 连接数据库准备
    // ...
    
    // 构造 sql 语句准备执行
    String sql = "select * from `user` where username = " + username + " and password = " + password;
    
    // 执行 sql 语句，返回 User 对象	
}
```

	如果传入的 username 为 "root and 1 = 1 #"，传入 password 为 123456，那么 sql 语句就变成了：

```sql
select * from `user` where username = root and 1 = 1# and password = 123456
```

	由于后面 password 部分被注释了，相当于通过 username 就绕过登陆并获得了 root 的信息。

2. 在本项目中，采用 mybatis 使用 #{} 语法可以防止 sql 注入，#{} 起到了占位符的作用，例如使用 #{} 改写

```sql
select * from `user` where username = #{username} and password = #{password}
```

	还是传入和上面相同的参数，此时 sql 语句会变成：

```sql
select * from `user` where username = "root and 1 = 1#" and password = "123456"
```

	执行该 sql 语句没有危险。