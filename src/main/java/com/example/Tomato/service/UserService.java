package com.example.Tomato.service;

import com.example.Tomato.dto.Userdto;
import com.example.Tomato.exception.allreadyexists.AllReadyExists;
import com.example.Tomato.exception.notfound.NotFoundException;
import com.example.Tomato.exception.savefailed.SavedFailed;
import com.example.Tomato.model.User;
import com.example.Tomato.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public Page<Userdto> getAllUsers(int page, int size) {

        Page<User> productsPage = userRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
        return modelMapper.map(productsPage , new TypeToken<Page<Userdto>>(){}.getType());
    }

    public Userdto addUser(Userdto user) {

        Optional<User> userOptional=userRepo.findUserByEmail(user.getEmail());

        if(userOptional.isPresent()){
            throw new AllReadyExists(user.getEmail());
        }

        User savedUser;
        try{
            savedUser = userRepo.save(modelMapper.map(user, User.class));
        }
        catch (Exception e){
            throw new SavedFailed();
        }
        return modelMapper.map(savedUser,Userdto.class);

    }

    public Userdto getUserByEmail(String id){
        User user= userRepo.findUserByEmail(id)
                .orElseThrow(() -> new NotFoundException(id));

        return modelMapper.map(user, Userdto.class);
    }

    public Userdto getUserById(String id){

        User user= userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return modelMapper.map(user, Userdto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByEmail(username).orElseThrow(() -> new NotFoundException(username));
    }
}
