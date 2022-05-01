package com.poginc.backendmaster.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pog_mock")
@Getter
@Setter
public class pog_Mock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pid;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    public pog_Mock(){

    }
    public pog_Mock(String name, String email){
        super();
    }


}
