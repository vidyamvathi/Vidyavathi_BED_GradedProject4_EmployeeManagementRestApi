package com.greatlearning.emrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greatlearning.emrestapi.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}