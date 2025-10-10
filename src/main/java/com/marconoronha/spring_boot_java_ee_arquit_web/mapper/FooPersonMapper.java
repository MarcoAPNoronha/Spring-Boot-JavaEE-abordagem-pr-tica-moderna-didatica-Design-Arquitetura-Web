package com.marconoronha.spring_boot_java_ee_arquit_web.mapper;

import com.marconoronha.spring_boot_java_ee_arquit_web.dto.PersonDTO;
import com.marconoronha.spring_boot_java_ee_arquit_web.entity.Person;
import jakarta.annotation.Priority;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

//@Order não define ordem de injeção de Bean ambiguo na Controller
@Component
@Log
@Priority(PriorityOrdered.LOWEST_PRECEDENCE) //Resolve ambiguidade de Beans no @Controller definindo a ordem de prioridade
public class FooPersonMapper implements DTOToObjectMapper<PersonDTO, Person> {

    private final ModelMapper modelMapper;

    public FooPersonMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        log.warning("FooPerson mapper, with highest-precedence order, loaded at application context");
    }

    @Override
    public PersonDTO objectToObjectDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public Person objectDTOToObject(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
