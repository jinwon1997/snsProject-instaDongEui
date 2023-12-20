package com.example.snsProject.model.DAO;


import com.example.snsProject.model.DTO.Member;
import com.example.snsProject.model.DTO.SocialUsers;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {

    Optional<Member> findByUserName(String userName);
    SocialUsers findByEmailAndPassword(String email, String password);
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByEmail(String email);
    Optional<SocialUsers> findBySocialEmail(String email);
    boolean saveOrUpdate(SocialUsers user);
}