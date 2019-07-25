package com.lambdaschool.tempEC.models;

import javax.persistence.*;

@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long conversationid;

    private String survivornumber;
    private String ffname;
    private String ffnumber;

    public Conversation() {
    }

    public Conversation(String survivornumber, String ffname, String ffnumber) {
        this.survivornumber = survivornumber;
        this.ffname = ffname;
        this.ffnumber = ffnumber;
    }

    public long getConversationid() {
        return conversationid;
    }

    public String getSurvivornumber() {
        return survivornumber;
    }

    public void setSurvivornumber(String survivornumber) {
        this.survivornumber = survivornumber;
    }

    public String getFfname() {
        return ffname;
    }

    public void setFfname(String ffname) {
        this.ffname = ffname;
    }

    public String getFfnumber() {
        return ffnumber;
    }

    public void setFfnumber(String ffnumber) {
        this.ffnumber = ffnumber;
    }

}
