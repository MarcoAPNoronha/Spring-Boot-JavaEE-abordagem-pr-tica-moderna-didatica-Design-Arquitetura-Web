package com.marconoronha.spring_boot_java_ee_arquit_web.repository;

import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends ListCrudRepository<Person, Long>, //Tipo do objeto da entidade e tipo do ID, não retorna Paginable
                                          PagingAndSortingRepository<Person, Long>, //Retorna Pageable
                                          PersonCustomRepository {

    @Query(value = "SELECT * FROM PERSON where FIRST_NAME= :firstName ORDER BY USER_NAME ASC")
    List<Person> findPersonByFirstNameOrderByUserNameAsc(@Param("firstName") String firstName);


    @Query(value = "SELECT * FROM PERSON p where p.LAST_NAME= :firstName ORDER BY USER_NAME ASC")
    List<Person> findPersonByLastNameOrderByUserNameAsc(@Param("lastName") String lastName);

    //Query Method: não é necessário o uso de @Query
    List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName); //"Deduz" a implementação pelo nome do mtd



}
