package springbootstarter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import springbootstarter.entities.JwtProperties;
import springbootstarter.entities.User;
import springbootstarter.entities.UserPrincipal;
import springbootstarter.repositories.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
   private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //get authorization header containing the jwt token
        String header=request.getHeader(JwtProperties.HEADER_STRING);
        //if header not present delegate it to spring and exit
            if (header==null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
                chain.doFilter(request,response);
                return;
            }
            //if header is present ,grab the user and perform authorization
            Authentication authentication= getUsernamePasswordAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);


    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token=request.getHeader(JwtProperties.HEADER_STRING);
        if (token!=null){
            //get the token and validate it
            String username= JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(token.replace(JwtProperties.TOKEN_PREFIX,""))
                    .getSubject();

            //search the db by the toke subject
            //then grab the user details and create spring token
            if (username!=null){
                User user=userRepository.findUserByUsername(username);
                UserPrincipal userPrincipal=new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(username,null,userPrincipal.getAuthorities());
            return auth;
            }
        }

        return null;
    }
}
