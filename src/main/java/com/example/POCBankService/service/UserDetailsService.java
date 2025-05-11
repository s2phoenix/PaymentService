package com.example.POCBankService.service;

import com.example.POCBankService.entity.UserInfo;
import com.example.POCBankService.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Map<String, UserDetails> users = new HashMap<>();

    public UserDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        // Manual for teller account.
        users.put("0000000000001", User.builder()
                .username("0000000000001")
                .password(passwordEncoder.encode("P@ssword")) // {noop} means no password encoding for simplicity
                .roles("TELLER")
                .build());

        //load user from db
        for (UserDetails user : loadUser()) {
            users.put(user.getUsername(), user);
        }

    }

    //TODO:: implement later to connect DB. just hardcode for check code concept
    private List<UserDetails>  loadUser() {
        List<UserInfo> userInfos = userInfoRepository.findAll();
        if (!userInfos.isEmpty()) {
            return userInfos.stream()
                    .map(userInfo -> User.builder()
                            .username(userInfo.getUserId())
                            .password(passwordEncoder.encode(userInfo.getPassword()))
                            .roles(String.valueOf(userInfo.getRole()))
                            .build())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Return the user if it exists in the map, otherwise throw an exception
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }


}


