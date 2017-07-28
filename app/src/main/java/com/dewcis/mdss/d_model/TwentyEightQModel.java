package com.dewcis.mdss.d_model;


public class TwentyEightQModel {

    private long feet_blue_2,fever_chills,yellow_skin_2,breathing_difficulties,abdominal,
            not_sucking_2,constipation,urinate,cord_discharge,start_feeding,fed_birth,child_fed;
    private String client_rand;

    public long getFeet_blue_2() {
        return feet_blue_2;
    }

    public long getFever_chills() {
        return fever_chills;
    }

    public long getYellow_skin_2() {
        return yellow_skin_2;
    }

    public long getBreathing_difficulties() {
        return breathing_difficulties;
    }

    public long getAbdominal() {
        return abdominal;
    }

    public long getNot_sucking_2() {
        return not_sucking_2;
    }

    public long getConstipation() {
        return constipation;
    }

    public long getUrinate() {
        return urinate;
    }

    public long getCord_discharge() {
        return cord_discharge;
    }

    public long getStart_feeding() {
        return start_feeding;
    }

    public long getFed_birth() {
        return fed_birth;
    }

    public long getChild_fed() {
        return child_fed;
    }

    public String getClient_rand() {
        return client_rand;
    }

    public void setFeet_blue_2(long feet_blue_2) {
        this.feet_blue_2 = feet_blue_2;
    }

    public void setFever_chills(long fever_chills) {
        this.fever_chills = fever_chills;
    }

    public void setYellow_skin_2(long yellow_skin_2) {
        this.yellow_skin_2 = yellow_skin_2;
    }

    public void setBreathing_difficulties(long breathing_difficulties) {
        this.breathing_difficulties = breathing_difficulties;
    }

    public void setAbdominal(long abdominal) {
        this.abdominal = abdominal;
    }

    public void setNot_sucking_2(long not_sucking_2) {
        this.not_sucking_2 = not_sucking_2;
    }

    public void setConstipation(long constipation) {
        this.constipation = constipation;
    }

    public void setUrinate(long urinate) {
        this.urinate = urinate;
    }

    public void setCord_discharge(long cord_discharge) {
        this.cord_discharge = cord_discharge;
    }

    public void setStart_feeding(long start_feeding) {
        this.start_feeding = start_feeding;
    }

    public void setFed_birth(long fed_birth) {
        this.fed_birth = fed_birth;
    }

    public void setChild_fed(long child_fed) {
        this.child_fed = child_fed;
    }

    public void setClient_rand(String client_rand) {
        this.client_rand = client_rand;
    }

    @Override
    public String toString() {
        return "QuestionsModel{" +
                " client_rand='" + client_rand + '\'' +
                ", feet_blue_2='" + feet_blue_2 + '\'' +
                ", fever_chills='" + fever_chills + '\'' +
                ", yellow_skin_2=" + yellow_skin_2 + '\'' +
                ", breathing_difficulties=" + breathing_difficulties + '\'' +
                ", not_sucking_2=" + not_sucking_2 + '\'' +
                ", constipation='" + constipation + '\'' +
                ", urinate='" + urinate + '\'' +
                ", cord_discharge='" + cord_discharge + '\'' +
                ", start_feeding='" + start_feeding + '\'' +
                ", fed_birth='" + fed_birth + '\'' +
                ", child_fed='" + child_fed + '\'' +

                '}';
    }
}
