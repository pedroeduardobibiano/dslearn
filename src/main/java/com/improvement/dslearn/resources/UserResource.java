package com.improvement.dslearn.resources;

import com.improvement.dslearn.dto.UserDTO;
import com.improvement.dslearn.dto.UserLoggedDTO;
import com.improvement.dslearn.servicies.AuthService;
import com.improvement.dslearn.servicies.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;
    private final AuthService authService;

    public UserResource(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserLoggedDTO> getMe() {
        UserLoggedDTO dto = authService.getMe();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
