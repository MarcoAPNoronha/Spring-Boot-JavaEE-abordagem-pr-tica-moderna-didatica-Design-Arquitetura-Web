package com.marconoronha.spring_boot_java_ee_arquit_web.service;

import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PersonService { //Orientada a classe de negócio

    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);

    Boolean existsById(Long id);

    Person saveOrUpdate(Person person);

    boolean deleteById(Long id);

    List<Person> findPersonByFirstNameOrderByUserNameAsc(@Param("firstName") String firstName);

    List<Person> findPersonByLastNameOrderByUserNameAsc(@Param("lastName") String lastName);

    List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName);

    List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName);

}
