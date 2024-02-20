package com.burhan.userorg.Controller;

import com.burhan.userorg.Entity.JwtAuthRequestEntity;
import com.burhan.userorg.Entity.JwtAuthResponseEntity;
import com.burhan.userorg.Exception.ApiException;
import com.burhan.userorg.Security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseEntity> createToken(@RequestBody JwtAuthRequestEntity request) throws Exception {
        System.out.println("username in controller : " + request.getUserName());
        System.out.println("password in controller : " + request.getPassword());
        this.authenticate(request.getUserName(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponseEntity response = new JwtAuthResponseEntity();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponseEntity>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid username or password !!");
        }

    }
}
