/*
 <!--
  ~  Copyright (c) 2025
  ~  EXI Limitada
  ~  All rights reserved.
  ~
  ~   Created by Eleuterio Zacarias Mabecuane 
  -->

 */

package mz.sisden.sisden.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.repositories.UserGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    public List<UserGroup> findAll() {
        return userGroupRepository.findAll();
    }

    public Optional<UserGroup> findById(Long id) {
        return userGroupRepository.findById(id);
    }

    public Optional<UserGroup> findByCode(String code) {
        return userGroupRepository.findByCode(code);
    }

    public UserGroup save(UserGroup userGroup) {
        return userGroupRepository.save(userGroup);
    }

    public void deleteById(Long id) {
        userGroupRepository.deleteById(id);
    }

    public boolean existsByCode(String code) {
        return userGroupRepository.existsByCode(code);
    }
} 