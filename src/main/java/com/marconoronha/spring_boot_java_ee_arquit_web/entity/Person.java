package com.marconoronha.spring_boot_java_ee_arquit_web.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data //Substitui todos os Getters e Setters
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERSON")
@XmlRootElement //JSON é enviado por padrão, pra enviar XML precisa da annotation
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @Id
    @Column(value = "ID")
    @NotNull
    @XmlElement //Define quais atributos serão mapeados para o XML
    private Long id;


    @Column(value = "FIRST_NAME")
    @NotBlank
    @XmlElement
    private String firstName;


    @Column(value = "LAST_NAME")
    @NotBlank
    @XmlElement
    private String lastName;


    @Column(value = "USER_NAME")
    @Size(message = "Username must have more than 6 chars and less than 19 chars.", min = 6, max = 19)
    @XmlElement
    private String userName;


    @Column(value = "PASSWORD")
    @Size(message = "Username must have exactly 8 chars.", min = 8, max = 8)
    @XmlElement
    private String password;


    @Email //Validação do dado não no banco (sintaxe do email válida ou não, porém não verifica se já está em uso)
    @Column(value = "EMAIL")
    @XmlElement
    private String email;


}
