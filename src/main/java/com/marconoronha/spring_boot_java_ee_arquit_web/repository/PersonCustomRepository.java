package com.marconoronha.spring_boot_java_ee_arquit_web.repository;

import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import java.util.List;


public interface PersonCustomRepository {

    public Person saveOrUpdate(Person person);

    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc (String firstName, String lastName);

}
