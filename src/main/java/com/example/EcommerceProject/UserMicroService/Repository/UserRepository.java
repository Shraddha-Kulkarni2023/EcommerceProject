package com.example.EcommerceProject.UserMicroService.Repository;

import com.example.EcommerceProject.UserMicroService.Model.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface UserRepository extends JpaRepository<ProjectUser,Long> {

    @Query("SELECT p FROM ProjectUser p WHERE p.username = :username")
    ProjectUser findByUsername(@Param("username") String username);
    boolean existsByUsername(String username);
}



