package com.example.snsProject.service;


import com.example.snsProject.model.DAO.UserDAO;
import com.example.snsProject.model.DTO.RegisterUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMemberService {
    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;


    public void registerUser(String userName, String password, String name, String phoneOrEmail) {
        RegisterUserDTO user = new RegisterUserDTO();
        user.createUser(userName, passwordEncoder.encode(password), name, phoneOrEmail);
        userDAO.registerUser(user);
    }

    public int duplicateUser(String phoneOrEmail) {
        int countAllMember = 0;
        try {
            countAllMember = userDAO.duplicateUser(phoneOrEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countAllMember;
    }

}
