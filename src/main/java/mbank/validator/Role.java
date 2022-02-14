package mbank.validator;

import java.util.List;

public class Role {
    private String name;
    private List<Role> childrenRoles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getChildrenRoles() {
        return childrenRoles;
    }

    public void setChildrenRoles(List<Role> childrenRoles) {
        this.childrenRoles = childrenRoles;
    }
}
