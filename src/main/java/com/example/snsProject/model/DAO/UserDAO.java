package com.example.snsProject.model.DAO;

import com.example.snsProject.model.DTO.FollowDTO;
import com.example.snsProject.model.DTO.RegisterUserDTO;
import com.example.snsProject.model.DTO.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserDAO {
    void registerUser(RegisterUserDTO user);
    int duplicateUser(String phoneOrEmail);
    void updateUser(UserDTO user);
    void deleteUser(long userId);
    boolean profileUpdate(String url, String email);
    List<UserDTO> findUsers();
    UserDTO findUser(long userId);
    List<FollowDTO> findFollowUsers(Long userId);
    List<HashMap<?,?>> SearchUsers(String search_input);
}