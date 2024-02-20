package com.burhan.userorg.Repository;
import com.burhan.userorg.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<UsersEntity,Long> {
//    UsersEntity findByName(String name);
}
