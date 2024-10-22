package com.medison.mysql.user.controller;

import com.medison.mysql.user.dto.UserRequestDto;
import com.medison.mysql.user.dto.UserResponseDto;
import com.medison.mysql.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    @ResponseBody
    @PostMapping("/user/join")
    public boolean save(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        if (userResponseDto.getId() == null) {
            return false;
        }
        return true;
    }

    @GetMapping("/admin/manage")
    public String userManagePage(){
        return "user/userManage";
    }

    @ResponseBody
    @PostMapping("/admin/manage")
    public List<UserResponseDto> getUsers(){
        List<UserResponseDto> userResponseDtos = userService.getUsers();
        return userResponseDtos;
    }


    @GetMapping("/user/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/user/login")
    public RedirectView login(@ModelAttribute UserRequestDto userRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserResponseDto result = userService.login(userRequestDto);
        RedirectView redirectView = new RedirectView();
        if (result != null) {
            session.setAttribute("user", result);
            redirectView.setUrl("/admin");
            return redirectView;
        } else{
            session.setAttribute("loginError", "loginError");
            redirectView.setUrl("/user/login");
            return redirectView;
        }
    }

    @ResponseBody
    @PostMapping("/user/logout")
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
    @DeleteMapping("/user/delete")
    public boolean delete(@RequestBody UserRequestDto userRequestDto) {
        return userService.delete(userRequestDto);
    }

    @GetMapping("/user/update/me")
    public String updateByTheUser() {
        return "user/userEditPage";
    }


    @ResponseBody
    @PutMapping("/user/update/me")
    public boolean updateByTheUser(@RequestBody JSONObject reqObj,HttpServletRequest request) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(reqObj.get("id").toString());
        userRequestDto.setPassword(reqObj.get("password").toString());
        boolean result = userService.updateByTheUser(userRequestDto, reqObj.get("currentPassword").toString());
        if(result){
            HttpSession session = request.getSession();
            session.removeAttribute("user");
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/user/update/admin/{id}")
    public ModelAndView updateAdmin(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("user/adminEditPage");
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(id);
        UserResponseDto result = userService.getUserForUpdateByAdmin(userRequestDto);
        modelAndView.addObject("userInfo", result);
        return modelAndView;
    }

    @ResponseBody
    @PutMapping("/user/update/admin")
    public boolean updateByTheAdmin(@RequestBody UserRequestDto userRequestDto) {
        return userService.updateByTheAdmin(userRequestDto);
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String admin() {
        return "user/adminPage";
    }

}
