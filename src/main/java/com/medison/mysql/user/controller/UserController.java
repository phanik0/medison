package com.medison.mysql.user.controller;

import com.medison.mysql.user.dto.UserRequestDto;
import com.medison.mysql.user.dto.UserResponseDto;
import com.medison.mysql.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @ResponseBody
    @PostMapping("/join")
    public ModelAndView save(@ModelAttribute UserRequestDto userRequestDto) {
        System.out.println("de : " + userRequestDto.getDepartmentCode());
        ModelAndView modelAndView = new ModelAndView("");
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        JSONObject response = new JSONObject();
        boolean status = true;
        String message = "User registration is success.";
        if (userResponseDto.getId() == null) {
            status = false;
            message = "User registration is fail.";
        }
        response.put("status", status);
        response.put("message", message);
        response.put("id", userResponseDto.getId());
        response.put("password", userResponseDto.getPassword());
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/manage")
    public List<UserResponseDto> getUsers() {
        return userService.getUsers();
    }

    @ResponseBody
    @PostMapping("/login")
    public boolean login(@ModelAttribute UserRequestDto userRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserResponseDto result = userService.login(userRequestDto);
        if (result != null) {
            session.setAttribute("user", result);
            return true;
        } else
            return false;
    }

    @ResponseBody
    @PostMapping("/logout")
    public boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        if (session.getAttribute("user") != null) {
            return false;
        } else {
            return true;
        }
    }

    @ResponseBody
    @DeleteMapping("/delete")
    public boolean delete(@ModelAttribute UserRequestDto userRequestDto) {
        return userService.delete(userRequestDto);
    }

    @ResponseBody
    @PutMapping("/update/me")
    public boolean updateByTheUser(@RequestParam Map<String, String> reqObj) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(reqObj.get("id").toString());
        userRequestDto.setPassword(reqObj.get("password").toString());
        return userService.updateByTheUser(userRequestDto, reqObj.get("currentPassword").toString());
    }

    @ResponseBody
    @PutMapping("/update/admin")
    public boolean updateByTheAdmin(@ModelAttribute UserRequestDto userRequestDto) {
        return userService.updateByTheAdmin(userRequestDto);
    }

}
