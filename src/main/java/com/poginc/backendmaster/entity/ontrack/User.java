package com.poginc.backendmaster.entity.ontrack;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ot_users")
@Getter
@Setter
public class User {

    @ApiModelProperty(hidden = true)
    @Id
    private int pid;

    @NotNull
    @Column(name = "chat_id", unique=true)
    private long chatid;

    @Column(name = "username")
    private String username;

    @Column(name="currentbankbal")
    private double currentbankbal;

    @Column(name="currentcash")
    private double currentcash;

    @Column(name="mth_budget")
    private double budget;

    public User() {

    }

    public User(long chatid, String username, double currentbankbal, double currentcash, double budget) {
        super();
        this.chatid = chatid;
        this.username = username;
        this.currentbankbal = currentbankbal;
        this.currentcash=  currentcash;
        this.budget = budget;
    }
}