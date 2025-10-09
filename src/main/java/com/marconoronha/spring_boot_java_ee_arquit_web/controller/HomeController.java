package com.marconoronha.spring_boot_java_ee_arquit_web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


//@RestController já define o @ResponseBody automaticamente
@Controller //Para aplicações MVC. Para aplicações REST usar @RestController
@RequestMapping(
    value = {"/", "/home", "/index"},
    produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_PLAIN_VALUE
    }
)
public class HomeController {

    //Referencia uma propriedade em Application.properties (não é instância, só valor)
    //Após os ":" é o valor Default, caso não ache a propriedade
    @Value("${welcome.message}:Bem vindo ao gerenciamento de perfil por API REST!")
    private String WELCOME_MESSAGE;


    @GetMapping("")
    @ResponseBody //Define que será uma HTTP Response, o corpo da resposta
    @ResponseStatus(HttpStatus.OK) //O tipo da resposta da requisição é do tipo "ok"
    String showWelcomeMessage(){
        return WELCOME_MESSAGE; //ViewName, para ser direcionado para a View ou o que será incluido no corpo da resposta
    }







}
