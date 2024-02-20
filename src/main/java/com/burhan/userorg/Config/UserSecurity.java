package com.burhan.userorg.Config;

import com.burhan.userorg.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
	
	@Autowired
	UsersRepository userRepo;
	
	public boolean hasUserId(Authentication authentication, Long userId) {
		
		Long userID=userRepo.findByUserName(authentication.getName()).getId();
//		System.out.println(userId+"  "+userID);
		if (userID != null && userID.equals(userId)) {
			return true;
		}
            return false;
       
    }
}
