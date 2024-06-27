package com.medison.mysql.user.service;

import com.medison.mysql.user.ActionType;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.domain.UserRepository;
import com.medison.mysql.user.dto.UserRequestDto;
import com.medison.mysql.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> responseDtos = new ArrayList<>();
        for (User user : users) {
            responseDtos.add(new UserResponseDto(user, ActionType.VIEW));
        }
        return responseDtos;
    }

    public UserResponseDto save(UserRequestDto userRequestDto) {
        User user = new User();
        String id = "doc";
        String code = makeCode(userRequestDto);
        String year = getNowYear();
        String departmentCode = userRequestDto.getDepartmentCode() / 10 == 0 ? "0" + userRequestDto.getDepartmentCode() : "" + userRequestDto.getDepartmentCode();
        id += year + departmentCode + code;
        String initialPassword = id;
        userRequestDto.setPassword(initialPassword);
        userRequestDto.setId(id);
        user.create(userRequestDto);
        userRepository.save(user);
        return new UserResponseDto(user, ActionType.SAVE);
    }

    private String makeCode(UserRequestDto userRequestDto) {
        List<User> users = userRepository.findByDepartmentCodeOrderByRegDateDesc(userRequestDto.getDepartmentCode());
        User user;
        String lastIdByDepartmentCode;
        for (int i = 0; i < users.size(); i++) {
            System.out.println("index" + i + " : " + users.get(i).getId());
        }
        if (users.size() < 1)
            lastIdByDepartmentCode = "doc0000000";
        else {
            user = users.get(0);
            lastIdByDepartmentCode = user.getId();
        }
        int lastCodeByDepatmentCode = Integer.parseInt(lastIdByDepartmentCode.substring(7, lastIdByDepartmentCode.length()));
        int targetCode = lastCodeByDepatmentCode + 1;
        String code = "";
        if (targetCode / 100 == 0) {
            code += "0";
        }
        if (targetCode / 10 == 0) {
            code += "0";
        }
        code += "" + targetCode;
        System.out.println("make code : " + code);
        return code;
    }

    private String getNowYear() {
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        // 포맷 적용
        String year = now.format(formatter);
        return year;
    }

    public UserResponseDto login(UserRequestDto userRequestDto) {
        Optional<User> optionalUser = userRepository.findById(userRequestDto.getId());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.getPassword().equals(userRequestDto.getPassword()))
                return new UserResponseDto(user, ActionType.LOGIN);
            else
                return null;
        } else {
            return null;
        }
    }

    @Transactional
    public boolean delete(UserRequestDto userRequestDto) {
        if (userRepository.existsById(userRequestDto.getId())) {
            userRepository.deleteById(userRequestDto.getId());
            return true;
        } else
            return false;
    }

    @Transactional
    public boolean updateByTheUser(UserRequestDto userRequestDto, String currentPassword) {
        Optional<User> optionalUser = userRepository.findById(userRequestDto.getId());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (!user.getPassword().equals(currentPassword)) {
                return false;
            }
            user.updateByTheUser(userRequestDto);
            return true;
        } else
            return false;
    }

    @Transactional
    public boolean updateByTheAdmin(UserRequestDto userRequestDto) {
        User user = userRepository.findById(userRequestDto.getId()).orElseThrow(
                () -> new RuntimeException("user not found")
        );
        System.out.println("departmentCode : " + userRequestDto.getDepartmentCode());
        System.out.println("position null? : " + userRequestDto.getPosition().isEmpty());
        System.out.println("phone null? : " + userRequestDto.getPhone().isEmpty());
        user.updateByTheAdmin(userRequestDto);
        return true;
    }
}
