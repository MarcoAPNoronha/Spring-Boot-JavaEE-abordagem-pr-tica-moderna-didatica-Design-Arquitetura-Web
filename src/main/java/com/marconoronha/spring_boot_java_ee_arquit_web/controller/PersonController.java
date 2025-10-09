package com.marconoronha.spring_boot_java_ee_arquit_web.controller;

import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import com.marconoronha.spring_boot_java_ee_arquit_web.exception.PersonDeleteByIdFailedException;
import com.marconoronha.spring_boot_java_ee_arquit_web.service.PersonService;
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

    public PersonController(PersonService personService) {
        this.personService = personService;
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


    @RequestMapping(method = RequestMethod.HEAD, value = "/${/id}") //Anotação genérica, quando não tem tipo específico
    ResponseEntity<Person> existsById(@PathVariable("id")Long personId){
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
    )
    {
        Sort sort = asceding ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(personService.findAll(pageable));
    }



    void saveOrUpdate(){

    }


    @ExceptionHandler(PersonDeleteByIdFailedException.class)
    ResponseEntity<String> personDeleteByIdFailedExceptionHandler(PersonDeleteByIdFailedException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

}
