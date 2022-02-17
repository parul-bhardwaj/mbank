package mbank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import mbank.response.BankResponse;
import mbank.validator.RoleParser;

@RestController
@RequestMapping("/mbank")
public class BankController {

    @Autowired
    RoleParser roleParser;

    @GetMapping("/fetch")
    public BankResponse getData(@RequestAttribute String username, @RequestAttribute String roleName){
        BankResponse bankResponse = new BankResponse();
        bankResponse.setName(username);
        if(roleParser.isWalletUser(roleName))
            bankResponse.setWalletBalance(100.00);
        return bankResponse;
    }
}
