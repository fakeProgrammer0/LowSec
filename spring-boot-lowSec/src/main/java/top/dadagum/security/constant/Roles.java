package top.dadagum.security.constant;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 22:57
 **/
public enum Roles {

    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

    private String role;

    private Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}