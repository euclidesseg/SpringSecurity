package SpringSecurity.SpringSecurity.dto;

import java.io.Serializable;

public class ShowPermissionDTO implements Serializable {

    private Long id;
    private String operation;
    private String module;
    private  String role;
    private String http_method;

    public Long getId() {
        return id;
    }
    public String getHttp_method() {
        return http_method;
    }
    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
