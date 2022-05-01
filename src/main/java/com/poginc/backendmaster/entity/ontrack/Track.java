package com.poginc.backendmaster.entity.ontrack;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ot_track")
@Getter
@Setter
public class Track {

    @ApiModelProperty(hidden = true)
    @Id
    @Column(name = "pid")
    private int pid;

    @NotNull
    @Column(name = "ref_chat_id")
    private long refchatid;

    @NotNull
    @Column(name = "track_type")
    private String track_type;

    @NotNull
    @Column(name="amount")
    private double amount;

    @NotNull
    @Column(name="pay_mode")
    private String paymode;

    @NotNull
    @Column(name = "cat")
    private String cat;

    @Column(name = "descr")
    private String desc;

    @Column(name = "t_date")
    private Date t_date;

    @ApiModelProperty(hidden = true)
    @Column(name = "updated_on")
    private Instant updated_on;

    public Track() {

    }

    public Track(long chatid, String track_type, double amount, String paymode, String cat, String desc, Date t_date, Instant updated_on) {
        super();
        this.refchatid = chatid;
        this.track_type = track_type;
        this.amount = amount;
        this.paymode =  paymode;
        this.cat = cat;
        this.desc = desc;
        this.t_date = t_date;
        this.updated_on = updated_on;
    }
}