package com.example.service;

import com.example.dto.UserReqDTO;
import com.example.dto.UserRespDTO;

import java.util.List;

public interface UserService {

    boolean saveUser(UserReqDTO userReqDTO);

    UserRespDTO findUserByID(int id);

    boolean updateUserByID(int id, UserReqDTO userReqDTO);

    boolean deleteUserById(int id);

    List<UserRespDTO> findAllUsers();
}
