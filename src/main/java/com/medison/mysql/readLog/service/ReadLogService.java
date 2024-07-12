package com.medison.mysql.readLog.service;

import com.medison.mysql.department.service.DepartmentService;
import com.medison.mysql.readLog.domain.ReadLog;
import com.medison.mysql.readLog.domain.ReadLogRepository;
import com.medison.mysql.readLog.domain.ReadLogResponseDto;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.service.UserService;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadLogService {
    private final ReadLogRepository readLogRepository;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final StudyService studyService;


    public boolean save(String userId,int studykey){
        User user = userService.getUserById(userId);
        String userName = user.getName();
        String userBirth = user.getBirth();
        String userAddress = user.getAddress();
        String userPhone = user.getPhone();
        String userPosition = parsePosition(user);
        String userDepartment = departmentService.getNameById(user.getDepartmentCode());

        ReadLog readLog = new ReadLog();
        readLog.save(userName,userPhone,userBirth,userAddress,userDepartment,userPosition,studykey);
        ReadLog result = readLogRepository.save(readLog);

        if(result==null){
            return false;
        }
        return true;
    }

    public boolean exist(String userId,int studykey){
        User user = userService.getUserById(userId);
        return readLogRepository.existsByStudykeyAndUserNameAndUserPhoneAndUserBirth(studykey,user.getName(),user.getPhone(),user.getBirth());
    }


    private String parsePosition(User user){
        String userPosition="";
        String temp = user.getPosition();
        if(temp.equals("fellow")){
            userPosition = "펠로우";
        }else if(temp.equals("resident")){
            userPosition = "레지던트";
        }else if(temp.equals("intern")){
            userPosition = "인턴";
        }else if(temp.equals("professor")){
            userPosition = "교수";
        }
        return userPosition;
    }

    public List<ReadLogResponseDto> findAll(){
        List<ReadLog> readLogs = readLogRepository.findAll();
        List<ReadLogResponseDto> readLogResponseDtos = new ArrayList<>();
        for(ReadLog readLog : readLogs){
            ReadLogResponseDto readLogResponseDto = new ReadLogResponseDto(readLog);
            readLogResponseDtos.add(readLogResponseDto);
        }
        return readLogResponseDtos;
    }
}
