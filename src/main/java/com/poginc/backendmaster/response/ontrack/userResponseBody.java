package com.poginc.backendmaster.response.ontrack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userResponseBody {

    private int pid;

    private long chatid;

    private String username;

    private double currentbankbal;

    private double currentcash;

    private double budget;

    private double budgetbal;

    private double budgetbalpct;

    private double budgetmthpct;

    public userResponseBody() {

    }

    public userResponseBody(int pid, long chatid, String username, double currentbankbal, double currentcash, double budget, double budgetbal, double budgetbalpct, double budgetmthpct) {
        super();
        this.pid = pid;
        this.chatid = chatid;
        this.username = username;
        this.currentbankbal = currentbankbal;
        this.currentcash = currentcash;
        this.budget = budget;
        this.budgetbal = budgetbal;
        this.budgetbalpct = budgetbalpct;
        this.budgetmthpct = budgetmthpct;
    }
}
