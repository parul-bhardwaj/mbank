package mbank.validator;


import mbank.exception.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${validBankUsers}")
    private List<String> validBankUsers;
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws ServletException, IOException {

            final String requestTokenHeader = request.getHeader("Authorization");

            String jwtToken = null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = StringUtils.substringAfter(requestTokenHeader, "Bearer ");
                Claims allClaims = getAllClaimsFromToken(jwtToken);
                if(validateToken(allClaims)){
                     request.setAttribute("username", allClaims.getSubject());
                     request.setAttribute("roleName", allClaims.get("role"));
                }
                else {
                    throw new ApplicationException("Token is not valid");
                }


            } else {
                throw new ApplicationException("Token is not started with Bearer");
            }

            // Once we get the token validate it.

            chain.doFilter(request, response);
        }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(Date jwtExpdate) {
        return jwtExpdate.before(new Date());
    }

    //validate token
    public Boolean validateToken(Claims allClaims) {
        final String username = allClaims.getSubject();
        return (validBankUsers.contains(username) && !isTokenExpired(allClaims.getExpiration()));
    }

    }

