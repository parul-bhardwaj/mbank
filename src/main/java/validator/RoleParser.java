package validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

@Service
public class RoleParser {
    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:file.json")
    Resource resource;

    Role role;

    public RoleParser() throws IOException {
        role = objectMapper.readValue(resource.getInputStream(), Role.class);
    }

    private Role fetchRole(Role currentRole, String roleName){
        if(currentRole == null)
            return null;
        if(currentRole.getName().equals(roleName)){
            return role;
        }
        if(!CollectionUtils.isEmpty(currentRole.getChildrenRoles())){
            for(Role childRole : currentRole.getChildrenRoles()){
                fetchRole(childRole, roleName);
            }
        }
        return  null;
    }

    public boolean isWalletUser(String roleName){
        Role role = fetchRole(this.role, roleName);
        return role.getName().equals("WalletUser");
    }



}
