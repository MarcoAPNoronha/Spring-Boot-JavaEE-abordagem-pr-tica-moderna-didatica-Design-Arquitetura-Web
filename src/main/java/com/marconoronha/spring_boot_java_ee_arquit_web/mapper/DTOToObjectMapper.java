package com.marconoronha.spring_boot_java_ee_arquit_web.mapper;

import com.marconoronha.spring_boot_java_ee_arquit_web.dto.ObjectDTO;
import org.springframework.stereotype.Component;

@Component
public interface DTOToObjectMapper<S extends ObjectDTO, T> {

    public S objectToObjectDTO(T object); //de Entidade noj parâmentro para retorno de S que extende DTO

    public T objectDTOToObject(S objectDTO); //de DTO no parâmentro para retorno de Entidade

}
