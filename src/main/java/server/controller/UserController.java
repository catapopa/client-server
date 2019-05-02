package server.controller;

import domain.User;
import server.service.UserService;
import utils.Response;

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Response login(String username, String password) {
        User user = userService.findOne(username, password);
        return new Response(Response.Type.LOGIN, user);
    }
}