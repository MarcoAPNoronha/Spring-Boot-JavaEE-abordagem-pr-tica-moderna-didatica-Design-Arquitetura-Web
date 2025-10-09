package com.marconoronha.spring_boot_java_ee_arquit_web.service;

import com.marconoronha.spring_boot_java_ee_arquit_web.exception.PersonDeleteByIdFailedException;
import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import com.marconoronha.spring_boot_java_ee_arquit_web.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public Person saveOrUpdate(Person person) {
        return personRepository.saveOrUpdate(person);
        //Se 'person' tiver ID, aplica Updade. Se não tiver, aplica Save.
    }

    @Override
    public boolean deleteById(Long id) {

        if (personRepository.existsById(id)){
            personRepository.deleteById(id);
            if(personRepository.existsById(id)){
                throw new PersonDeleteByIdFailedException("Person "+id+" exists at databese, but was not deleted.");
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Person> findPersonByFirstNameOrderByUserNameAsc(String firstName) {
        return personRepository.findPersonByFirstNameOrderByUserNameAsc(firstName);
    }


    @Override
    public List<Person> findPersonByLastNameOrderByUserNameAsc(String lastName) {
        return personRepository.findPersonByLastNameOrderByUserNameAsc(lastName);
    }


    @Override
    public List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName) {
        return personRepository.findPersonByLastNameOrderByFirstNameAsc(lastName);
    }


    @Override
    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName) {
        return personRepository.findPersonByFirstNameAndLastNameOrderByUserNameAsc(firstName, lastName);
    }
}
