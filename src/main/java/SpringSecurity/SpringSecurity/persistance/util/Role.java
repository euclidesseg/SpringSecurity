package SpringSecurity.SpringSecurity.persistance.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    ROLE_ADMINISTRATOR(Arrays.asList(
        RolePermission.READ_ALL_PRODUCTS,
        RolePermission.READ_ONE_PRODUCT,
        RolePermission.CREATE_ONE_PRODUCT,
        RolePermission.UPDATE_ONE_PRODUCT,
        RolePermission.DISABLE_ONE_PRODUCT,

        RolePermission.READ_ALL_CATEGORIES,
        RolePermission.READ_ONE_CATEGORY,
        RolePermission.CREATE_ONE_CATEGORY,
        RolePermission.UPDATE_ONE_CATEGORY,
        RolePermission.DISABLE_ONE_CATEGORY,

        RolePermission.READ_MY_PROFILE
    )),

    ROLE_ASSISTANT_ADMINISTRATOR(Arrays.asList(
        RolePermission.READ_ALL_PRODUCTS,
        RolePermission.READ_ONE_PRODUCT,
        RolePermission.UPDATE_ONE_PRODUCT,

        RolePermission.READ_ALL_CATEGORIES,
        RolePermission.READ_ONE_CATEGORY,
        RolePermission.UPDATE_ONE_CATEGORY,

        RolePermission.READ_MY_PROFILE
    )),
    ROLE_CUSTOMER(Arrays.asList(
        RolePermission.READ_MY_PROFILE
    ));

    private List<RolePermission> permissions;

    /*
     * Este constructor recibe una lista de permisos y la asigna al atributo permissions.
     * Es necesario porque cada constante del enum (ROLE_ADMINISTRATOR,
     * ROLE_ASSISTANT_ADMINISTRATOR y CUSTOMER) está pasando una List<RolePermission>.
     */
    Role (List<RolePermission> permissions){
        this.permissions = permissions;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
