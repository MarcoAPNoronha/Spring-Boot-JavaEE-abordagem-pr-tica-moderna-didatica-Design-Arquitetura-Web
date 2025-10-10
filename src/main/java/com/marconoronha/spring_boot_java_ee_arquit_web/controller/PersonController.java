package com.marconoronha.spring_boot_java_ee_arquit_web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marconoronha.spring_boot_java_ee_arquit_web.dto.PersonDTO;
import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import com.marconoronha.spring_boot_java_ee_arquit_web.exception.PersonDeleteByIdFailedException;
import com.marconoronha.spring_boot_java_ee_arquit_web.exception.PersonSaveOrUpdateByIdFailedException;
import com.marconoronha.spring_boot_java_ee_arquit_web.mapper.DTOToObjectMapper;
import com.marconoronha.spring_boot_java_ee_arquit_web.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/persons")
public class PersonController {

    //Instancia automaticamente o atributo, mas a melhor forma é a nível de construtor mesmo
    //Quando há mais de 1 implementação da interface ele não sabe qual injetar exatamente
    //Como só tem 1 implementação, não tem problema
   // @Autowired
    private PersonService personService;
    private final DTOToObjectMapper<PersonDTO, Person> myPersonMapper;
    //private final ObjectMapper myObjectMapper;
    //private final ObjectMapper myXmlMapper;


    //1. DTOToObjectMapper é implementada por 2 classes, ela usa o @Priority(PriorityOrdered.HIGHEST_PRECEDENCE) para definir qual CLASSE será usada
    //2. Se o nome do parâmetro for igual o nome do Bean, ele já identificaria
    //3. Quando há ambiguidade no tipo do parâmetro, ele procura pelo @Primary (nível de MÉTOD)
    //4. @Qualifier("xmlMapper") Quando há ambiguidade no tipo do parâmetro, ele procura o MÉTOD com o nome do qualifier
    public PersonController(PersonService personService,
                            DTOToObjectMapper<PersonDTO, Person> myPersonMapper,
                            ObjectMapper myObjectMapper,
                            @Qualifier("xmlMapper")ObjectMapper myXmlMapper
    ){
        this.personService = personService;
        this.myPersonMapper = myPersonMapper;
        //this.myObjectMapper = myObjectMapper;
        //this.myXmlMapper = myXmlMapper;
    }

    /* --------------------------------------------------------------------
        @Autowired
        public void setPersonService(PersonService personService) {
            this.personService = personService;
        }
     ---> Outra forma de implementar, por setter (nem por @Autowired nem por construtor)
        -------------------------------------------------------------------
     */


    @GetMapping("/byFirstNameOrderByUserNameAsc/{first-name}")
    ResponseEntity<List<Person>> findPersonByFirstNameOrderByUserNameAsc(@PathVariable("first-name") String firstName){
        return ResponseEntity.ok(personService.findPersonByFirstNameOrderByUserNameAsc(firstName));

        //Esse endpoint chama a service/repository pelo méthod custom que busca pelo primeiro nome e retorna uma
        //lista de pessoas no corpo da resposta. Pode trazer nenhuma, 1 ou várias pessoas ordenadas por username.
        //De toda a forma retorna o código 200 de ok.

        //Por padrão, serializa para JSON. Para serializar para XML precisa de anotação.
    }


    @GetMapping("/byLastNameOrderByUserNameAsc/{last-name}")
    ResponseEntity<List<Person>> findPersonByLastNameOrderByUserNameAsc(@PathVariable("last-name") String lastName) {
        return ResponseEntity.ok(personService.findPersonByLastNameOrderByUserNameAsc(lastName));
    }


    @GetMapping("/byLastNameOrderByFirstNameAsc/{last-name}")
    ResponseEntity<List<Person>> findPersonByLastNameOrderByFirstNameAsc(@PathVariable("last-name") String lastName) {
        return ResponseEntity.ok(personService.findPersonByLastNameOrderByFirstNameAsc(lastName));
    }


    @GetMapping("/byFirstNameAndLastNameOrderByUserNameAsc/{first-name}/{last-name}")
    ResponseEntity<List<Person>> findPersonByFirstNameAndLastNameOrderByUserNameAsc(@PathVariable("first-name") String firstName,
                                                                                    @PathVariable("last-name") String lastName) {
        return ResponseEntity.ok(personService.findPersonByFirstNameAndLastNameOrderByUserNameAsc(firstName, lastName));
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Person> deleteById(@PathVariable("id") Long personId){
        boolean result = personService.deleteById(personId);
        if(result){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}") //Anotação genérica, quando não tem tipo específico
    ResponseEntity<Person> existsById(@PathVariable("id") Long personId){
        Boolean result = personService.existsById(personId);
        if(result){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    ResponseEntity<Person> findById(@PathVariable("id") Long personId){
        Optional<Person> result = personService.findById(personId);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    void savePerson(Person person){
        personService.saveOrUpdate(person);
    }


    @GetMapping(value = "",
                produces = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE })
    ResponseEntity<Page> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean asceding
    ){
        Sort sort = asceding ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(personService.findAll(pageable));
    }


    @ExceptionHandler(PersonSaveOrUpdateByIdFailedException.class)
    ResponseEntity<String> personSaveOrUpdateByIdFailedExceptionHandler(PersonSaveOrUpdateByIdFailedException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }


    @ExceptionHandler(PersonDeleteByIdFailedException.class)
    ResponseEntity<String> personDeleteByIdFailedExceptionHandler(PersonDeleteByIdFailedException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }


    @PutMapping(value = "/{id}",
                consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Person> SaveOrUpdateBy(@PathVariable("id") Long personId, @Valid @RequestBody PersonDTO personDTO){
        Person personToBESavedOrUpdated = myPersonMapper.objectDTOToObject(personDTO);
        personToBESavedOrUpdated.setId(personId); //sobrescreve o ID enviado no RequestBody JSON pelo ID pelo ID enviado no PathVariable;
        Person result = personService.saveOrUpdate(personToBESavedOrUpdated);
        return ResponseEntity.ok(result);
    }

}
