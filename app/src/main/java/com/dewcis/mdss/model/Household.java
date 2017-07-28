package com.dewcis.mdss.model;

/**
 * Created by Arwin Kish on 12/12/2016.
 */
public class Household {
    private final String key;
    private String name;
    private int num_of_Member;
    private int survey;
    private int type;


    public Household(int survey, String name, int num_of_Member, int type, String key) {
        this.survey = survey;
        this.name = name;
        this.num_of_Member = num_of_Member;
        this.type = type;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfMember() {
        return num_of_Member;
    }

    public int getSurvey() {
        return survey;
    }

    public void setSurvey(int survey) {
        this.survey = survey;
    }

    public int getType(){
        return this.type;
    }
    public void setType(int type){
        this.type = type;
    }
    public String getKey(){
        return key;
    }


    @Override
    public String toString() {
        return "Household{"
                + "survey='" + survey + '\'' +
                ", name='" + name + '\'' +
                ", numOfMember=" + num_of_Member + '\'' +
                ", type='" + type + "" + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
