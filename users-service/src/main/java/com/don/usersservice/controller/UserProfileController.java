package com.don.usersservice.controller;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;

/**
 * @author Donald Veizi
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController {

    private final UserService userService;

/*    @Operation(summary = "Get profile details of user")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Successfully got the profile details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })*/
    @GetMapping("/{userId}/profile")
    public UserDTO getProfile(@PathVariable @Min(1) Long userId) {
        return userService.getUserProfile(userId);
    }

}

