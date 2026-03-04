package com.in28minutes.rest.webservices.restful_web_services.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public  class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int userCount = 0;

    static {
        users.add(new User(++userCount,"Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++userCount,"Eve", LocalDate.now().minusYears(25)));
        users.add(new User(++userCount,"Jim", LocalDate.now().minusYears(20)));

    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int userId){
        //        for(var user:users){
        //            if(user.getId() == 1) return user;
        //        }
        //        return null;

        return users.stream().filter(user->user.getId()==userId).findFirst().orElse(null);
    }

    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;
    }

    public void deleteUserById(int userId){
        users.removeIf(user->user.getId()==userId);
    }

}