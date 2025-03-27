package com.example.UserService.service;

import com.example.UserService.entity.UserInfo;
import com.example.UserService.model.dto.UserInfoDTO;
import com.example.UserService.repository.UserInfoRepository;
import com.example.UserService.repository.specification.UserInfoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Mono<List<UserInfo>> findUsers(String firstName, String lastName, Boolean isActive, Boolean marriedStatus) {
        Specification<UserInfo> spec = Specification.where(UserInfoSpecification.hasFirstName(firstName))
                .and(UserInfoSpecification.hasLastName(lastName))
                .and(UserInfoSpecification.isActive(isActive))
                .and(UserInfoSpecification.isMarried(marriedStatus));
        return Mono.fromCallable(() -> {
            //add delay to see result is delay or not
            Thread.sleep(2000);
            return userInfoRepository.findAll(spec);
        });
    }

    public UserInfo createUserInfo(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setTitle(userInfoDTO.getTitle());
        userInfo.setFirstName(userInfoDTO.getFirstName());
        userInfo.setLastName(userInfoDTO.getLastName());
        userInfo.setMiddleName(userInfoDTO.getMiddleName());
        userInfo.setAge(userInfoDTO.getAge());
        userInfo.setBirthdate(userInfoDTO.getBirthdate());
        userInfo.setDisableStatus(userInfoDTO.getDisableStatus());
        userInfo.setMarriedStatus(userInfoDTO.getMarriedStatus());

        userInfo.setCreateDate(new Date());
        userInfo.setUpdateDate(new Date());

        userInfo.setStatusActive(true);

        return userInfoRepository.save(userInfo);
    }

    public UserInfo updateUserInfo(Long id, UserInfoDTO userInfoDTO) {
        Optional<UserInfo> existingUserInfo = userInfoRepository.findById(id);

        if (existingUserInfo.isPresent()) {
            UserInfo userInfo = existingUserInfo.get();

            userInfo.setTitle(userInfoDTO.getTitle());
            userInfo.setFirstName(userInfoDTO.getFirstName());
            userInfo.setLastName(userInfoDTO.getLastName());
            userInfo.setMiddleName(userInfoDTO.getMiddleName());
            userInfo.setAge(userInfoDTO.getAge());
            userInfo.setBirthdate(userInfoDTO.getBirthdate());
            userInfo.setDisableStatus(userInfoDTO.getDisableStatus());
            userInfo.setMarriedStatus(userInfoDTO.getMarriedStatus());
            userInfo.setUpdateDate(new Date());
            return userInfoRepository.save(userInfo);
        } else {
            throw new RuntimeException("UserInfo with ID " + id + " not found.");
        }
    }

    public UserInfo softDeleteUserInfo(Long id) {
        Optional<UserInfo> existingUserInfo = userInfoRepository.findById(id);

        if (existingUserInfo.isPresent()) {
            UserInfo userInfo = existingUserInfo.get();
            userInfo.setStatusActive(false); // Set statusActive to false (soft delete)
            userInfo.setUpdateDate(new Date()); // Set the current date as updateDate

            return userInfoRepository.save(userInfo);
        } else {
            throw new RuntimeException("UserInfo with ID " + id + " not found.");
        }
    }
}
