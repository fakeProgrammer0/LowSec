package top.dadagum.security.model;

import java.util.Date;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 11:24
 **/
public class User {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 登录名
     *
     */
    private String username;

    /**
     * 显示名, 真实姓名？ 中文名
     */
    private String showname;

    /**
     * 密码-SHA哈希值
     */
    private String password;

    /**
     * 提问-用于丢失密码（暂不适用）
     */
    private String question;

    /**
     * 回答-用于丢失密码（暂不适用）
     */
    private String answer;

    /**
     * 用户类型编码 ROLE_PUB / ROLE_OPR / ROLE_ADMIN
     */
    private String rolecode;

    /**
     * 备注-用户备注
     */
    private String description;

    /**
     * 最后登录时间
     */
    private Date lastlogintime;

    /**
     * 是否启用，“0-否”，“1-是，启用” 默认：1
     *
     * (修改-2014-01-17)
     *
     * isactive -> enabled
     */
    private boolean enabled;

    /**
     * 是否过期，“0-否”，“1-是，未过期” 默认：1
     *
     * (新增-2014-01-17)
     */
    private boolean accountNonExpired;

    /**
     * 密码是否失效，“0-否”，“1-是，未失效” 默认：1
     *
     * (新增-2014-01-17)
     */
    private boolean credentialsNonExpired;

    /**
     * 是否被锁定-“0-否”，“1-是，未被锁定” 默认：1
     *
     * (新增-2014-01-17)
     */
    private boolean accountNonLocked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
}
