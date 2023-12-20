package com.example.snsProject.service;

import com.example.snsProject.model.DAO.MemberRepository;
import com.example.snsProject.model.DAO.UserDAO;
import com.example.snsProject.model.DTO.Member;
import com.example.snsProject.model.DTO.MemberLoginDTO;
import com.example.snsProject.model.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final UserDAO userDAO;

    public Optional<Member> findOne(String name) {
        if (name.contains("@")) {
            return repository.findByEmail(name);
        } else if (name.matches("\\d{11}")) {
            return repository.findByPhone(name);
        } else {
            return repository.findByUserName(name);
        }
    }

    public boolean isValidMember(MemberLoginDTO dto) {
        Optional<Member> member = findOne(dto.getName());

        if (member.isPresent()) {
            return member.get().getPassword().equals(dto.getPw());
        }
        return false;
    }

    public UserDTO findUser(String userId) {
        UserDTO user = userDAO.findUser(Long.parseLong(userId));
        if (user.getUrl() == null || user.getUrl().isBlank()) {
            user.setUrl("/img/logo.png");
        }
        return user;
    }

    public List<HashMap<?,?>> SearchUsers(String search_input) {
        return userDAO.SearchUsers(search_input);
    }
    public void updateProfile(String email, String url) {
        userDAO.profileUpdate(url, email);
    }
}

