package top.dadagum.lowsec.domain;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 22:57
 **/
public enum Roles {

    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

    private String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}