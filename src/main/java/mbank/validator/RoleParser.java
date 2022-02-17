package mbank.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleParser {
    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:file.json")
    private Resource resource;

    Role role;

    private static final String WALLET_USER = "WalletUser";

    @PostConstruct
    public void init() throws IOException {
        role = objectMapper.readValue(resource.getInputStream(), Role.class);
    }

    private List<Role> fetchRole(Role currentRole, String roleName, String walletUser){
        if(currentRole.getName().equals(roleName)){
            List<Role> children = new ArrayList<>();
            if(currentRole.getName().equals(walletUser)){
                children.add(currentRole);
                return children;
            }
            else {
                traverseChildRole(currentRole, children, walletUser);
                return children;
            }
        }
        else{
            if(!CollectionUtils.isEmpty(currentRole.getChildrenRoles())){
                for(Role childRole : currentRole.getChildrenRoles()){
                    List<Role> childRoles = fetchRole(childRole,roleName, walletUser);
                    if(!CollectionUtils.isEmpty(childRoles)){
                        return childRoles;
                    }
                }
            }
        }
        return null;
    }

    private void traverseChildRole(Role role, List<Role> children, String walletUser){

        if(CollectionUtils.isEmpty(role.getChildrenRoles())){
            children.add(role);
        }else {
            for(Role childRole : role.getChildrenRoles()){
                if(childRole.getName().equals(walletUser)){
                    children.add(childRole);
                    break;
                }
                traverseChildRole(childRole, children, walletUser);
            }
        }
    }

    public boolean isWalletUser(String roleName){
        List<Role> role = fetchRole(this.role, roleName, WALLET_USER);
        if(CollectionUtils.isEmpty(role))
            return false;
        return role.stream().filter(r -> r.getName().equalsIgnoreCase(WALLET_USER)).findFirst().isPresent();
    }



}
