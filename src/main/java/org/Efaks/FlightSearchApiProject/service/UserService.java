package org.Efaks.FlightSearchApiProject.service;


import org.Efaks.FlightSearchApiProject.dto.request.CreateUserRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.LoginUserRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateUserRequestDto;
import org.Efaks.FlightSearchApiProject.exception.EErrorType;
import org.Efaks.FlightSearchApiProject.exception.UserManagerException;
import org.Efaks.FlightSearchApiProject.mapper.IUserMapper;
import org.Efaks.FlightSearchApiProject.repository.IUserRepository;
import org.Efaks.FlightSearchApiProject.repository.entity.User;
import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;
import org.Efaks.FlightSearchApiProject.utility.JwtTokenProvider;
import org.Efaks.FlightSearchApiProject.utility.ServiceManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManager<User,Long> {
    private  final IUserRepository iUserRepository;
    private final JwtTokenProvider jwtTokenManager;

    public UserService(IUserRepository iUserRepository, JwtTokenProvider jwtTokenManager) {
        super(iUserRepository);
        this.iUserRepository = iUserRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public User createUser(CreateUserRequestDto dto) {
        Optional<User> existUserName = iUserRepository.findByUserName(dto.getUserName());
        if (existUserName.isPresent())
            throw new IllegalStateException(dto.getUserName()+" is used.");
        if(!dto.getPassword().equals(dto.getRePassword()))
            throw new IllegalStateException("Password is unmatch");
        User user = IUserMapper.INSTANCE.toUser(dto);
        save(user);
        return user;
    }
    public User updateUser(UpdateUserRequestDto dto) {
        Optional<User> existUser = iUserRepository.findById(dto.getId());

        if (existUser.isEmpty() || existUser.get().getStatus().equals(EStatus.DELETED))
            throw new UserManagerException(EErrorType.USER_NOT_FOUND);
        if(!dto.getPassword().equals(dto.getRePassword()))
            throw new UserManagerException(EErrorType.USER_PASSWORD_UNMATCHED);
        User updatedUser = IUserMapper.INSTANCE.toUpdateUser(dto);
        updatedUser.setUserName(existUser.get().getUserName());
        updatedUser.setId(existUser.get().getId());
        updatedUser.setCreateDate((updatedUser.getCreateDate()));
        updatedUser.setUpdateDate(System.currentTimeMillis());
        update(updatedUser);
        return updatedUser;
    }

    public User deleteUser(Long id) {
        Optional<User> existUser = iUserRepository.findById(id);
        if (existUser.isEmpty() || existUser.get().getStatus().equals(EStatus.DELETED))
            throw new UserManagerException(EErrorType.USER_NOT_FOUND);
        existUser.get().setStatus(EStatus.DELETED);
        update(existUser.get());
        return existUser.get();
    }
    public String login(LoginUserRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("LoginUserRequestDto cannot be null.");
        }

        Optional<User> userAuth = iUserRepository.findByUserNameAndPassword(dto.getUserName(), dto.getPassword());
        if (userAuth.isEmpty())
            throw new UserManagerException(EErrorType.USER_NOT_FOUND);
        if (userAuth.get().getStatus() == EStatus.DELETED)
            throw new UserManagerException(EErrorType.LOGIN_ERROR_USERNAME_PASSWORD);
        String authStatus = userAuth.get().getStatus().toString();
        String token = jwtTokenManager.createToken(userAuth.get().getId(),userAuth.get().getRole(), userAuth.get().getStatus(),userAuth.get().getUserName())
                .orElseThrow(()->{
                    throw new UserManagerException(EErrorType.TOKEN_NOT_CREATED);
                });
        System.out.println("token==>"+token);
        return token;
    }

    public ResponseEntity<?> validateToken(String token) {
        boolean isValid = jwtTokenManager.verifyToken(token);
        if (isValid) {
            return ResponseEntity.ok().body("Token is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }
    }
}
