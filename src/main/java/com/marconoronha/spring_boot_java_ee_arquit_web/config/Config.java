package com.marconoronha.spring_boot_java_ee_arquit_web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//Não pode usar @Componente porque não tem acesso ao src da classe, aí tem que usar o Bean
@Configuration //Classe de configuração que terão beans criados manualmente para o ApplicationContext
public class Config {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Primary //Quando tem 2 beans com o mesmo tipo, quando for injetar um Bean desse tipo, esse será a opção primária
    @Bean("objectMapper") //Se não passar o atributo, o Bean terá o nome do próprio métod
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean("xmlMapper")
    public ObjectMapper xmlMapper(){
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper;
    }


}
