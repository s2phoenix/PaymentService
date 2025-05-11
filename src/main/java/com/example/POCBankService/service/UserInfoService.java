package com.example.POCBankService.service;

import com.example.POCBankService.entity.UserInfo;
import com.example.POCBankService.model.enumconstant.UserRole;
import com.example.POCBankService.model.enumconstant.UserStatus;
import com.example.POCBankService.model.request.ApprovalRequest;
import com.example.POCBankService.model.request.CustomerRegisterRequestModel;
import com.example.POCBankService.model.request.RegisterRequestModel;
import com.example.POCBankService.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public String preRegister(RegisterRequestModel request) {
        String account = generateSevenDigitNumber();

        // Check if the account already exists
        Optional<UserInfo> existingUser = userInfoRepository.findByAccount(account);

        if (existingUser.isPresent()) {
            return "Account with this generated number already exists. Please try again.";
        }

        if (!userInfoRepository.findByUserId(request.getCitizenId()).isEmpty()) {
            throw new RuntimeException("Duplicate registration: Citizen already exists.");
        }

        UserInfo user = new UserInfo();
        user.setUserId(request.getCitizenId());
        user.setPassword(request.getPassword());
        user.setThaiName(request.getNameTH());
        user.setEngName(request.getNameEN());
        user.setPin(request.getPin());
        user.setEmail(request.getEmail());
        user.setAccount(account);
        user.setRole(UserRole.PERSON);
        user.setStatus(UserStatus.PENDING_APPROVE);
        user.setCreateDate(LocalDateTime.now());
        user.setCreateBy("SYSTEM");
        userInfoRepository.save(user);

        return "Registration submitted";
    }

    public static String generateSevenDigitNumber() {
        Random random = new Random();
        int number = 1000000 + random.nextInt(9000000);
        return String.format("%07d", number);
    }

    public List<UserInfo> getPendingPersons() {
        return userInfoRepository.findByRoleAndStatus(UserRole.PERSON, UserStatus.PENDING_APPROVE);
    }

    public String updateUserApprovalStatus(ApprovalRequest request) {
        UserInfo user = userInfoRepository.findByAccount(request.getAccount())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getStatus().equals(UserStatus.PENDING_APPROVE)) {
            throw new RuntimeException("User is not in a pending state");
        }

        if ("APPROVED".equalsIgnoreCase(request.getAction())) {
            user.setStatus(UserStatus.APPROVED);
        } else if ("REJECTED".equalsIgnoreCase(request.getAction())) {
            user.setStatus(UserStatus.REJECTED);
        } else {
            throw new IllegalArgumentException("Invalid action");
        }

        user.setUpdateDate(LocalDateTime.now());
        return "User status updated to " + user.getStatus();
    }

    public String customerRegister(CustomerRegisterRequestModel request) {
        List<UserInfo> listCustomerAccount = userInfoRepository.findByUserId(request.getUserId());
        if (listCustomerAccount.isEmpty()) {
            throw new RuntimeException("Customer does not registration: Citizen not exists.");
        }

        String account = generateSevenDigitNumber();

        Optional<UserInfo> existingUser = userInfoRepository.findByAccount(account);

        if (existingUser.isPresent()) {
            return "Account with this generated number already exists. Please try again.";
        }
        UserInfo existingCustomer = listCustomerAccount.getFirst();
        if (!existingCustomer.getRole().equals(UserRole.CUSTOMER)) {
            throw new IllegalArgumentException("Only CUSTOMER role is allowed for prefill");
        }

        UserInfo newCustomer = new UserInfo();
        newCustomer.setThaiName(request.getNameTH());
        newCustomer.setEngName(request.getNameEN());
        newCustomer.setUserId(existingCustomer.getUserId());
        newCustomer.setPassword(existingCustomer.getPassword());
        newCustomer.setPin(existingCustomer.getPin());
        newCustomer.setEmail(existingCustomer.getEmail());
        newCustomer.setRole(UserRole.CUSTOMER);
        newCustomer.setStatus(UserStatus.PENDING_APPROVE);
        newCustomer.setAccount(account);
        newCustomer.setCreateDate(LocalDateTime.now());
        newCustomer.setCreateBy("system");
        userInfoRepository.save(newCustomer);

        return "Registration submitted";
    }
}
