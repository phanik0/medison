package com.medison.mysql.readLog.service;

import com.medison.mysql.readLog.domain.ReadLog;
import com.medison.mysql.readLog.domain.ReadLogRepository;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadLogService {
    private final ReadLogRepository readLogRepository;
    private final UserService userService;

    public boolean save(String userId,int studykey){
        User user = userService.getUserById(userId);
        ReadLog readLog = new ReadLog();
        readLog.save(user,studykey);
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
}
