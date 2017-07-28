package com.dewcis.mdss.d_model;


public class SevenQModel {
    private long not_breathing, yellow_skin, feet_blue, not_sucking, tiredness, always_sleepy, fast_breathing,
            chest_drawing, loose_weight, yellow_soles, start_feeding, fed_birth, child_fed;
    private String client_rand;

    public long getNot_breathing() {
        return not_breathing;
    }

    public long getYellow_skin() {
        return yellow_skin;
    }

    public long getFeet_blue() {
        return feet_blue;
    }

    public long getNot_sucking() {
        return not_sucking;
    }

    public long getTiredness() {
        return tiredness;
    }

    public long getAlways_sleepy() {
        return always_sleepy;
    }

    public long getFast_breathing() {
        return fast_breathing;
    }

    public long getChest_drawing() {
        return chest_drawing;
    }

    public long getLoose_weight() {
        return loose_weight;
    }

    public long getYellow_soles() {
        return yellow_soles;
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

    public void setNot_breathing(long not_breathing) {
        this.not_breathing = not_breathing;
    }

    public void setYellow_skin(long yellow_skin) {
        this.yellow_skin = yellow_skin;
    }

    public void setFeet_blue(long feet_blue) {
        this.feet_blue = feet_blue;
    }

    public void setNot_sucking(long not_sucking) {
        this.not_sucking = not_sucking;
    }

    public void setTiredness(long tiredness) {
        this.tiredness = tiredness;
    }

    public void setAlways_sleepy(long always_sleepy) {
        this.always_sleepy = always_sleepy;
    }

    public void setFast_breathing(long fast_breathing) {
        this.fast_breathing = fast_breathing;
    }

    public void setChest_drawing(long chest_drawing) {
        this.chest_drawing = chest_drawing;
    }

    public void setLoose_weight(long loose_weight) {
        this.loose_weight = loose_weight;
    }

    public void setYellow_soles(long yellow_soles) {
        this.yellow_soles = yellow_soles;
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
                ", not_breathing='" + not_breathing + '\'' +
                ", yellow_skin='" + yellow_skin + '\'' +
                ", feet_blue=" + feet_blue + '\'' +
                ", not_sucking=" + not_sucking + '\'' +
                ", tiredness=" + tiredness + '\'' +
                ", always_sleepy='" + always_sleepy + '\'' +
                ", fast_breathing='" + fast_breathing + '\'' +
                ", chest_drawing='" + chest_drawing + '\'' +
                ", loose_weight='" + loose_weight + '\'' +
                ", yellow_soles='" + yellow_soles + '\'' +
                ", start_feeding='" + start_feeding + '\'' +
                ", fed_birth='" + fed_birth + '\'' +
                ", child_fed='" + child_fed + '\'' +
                '}';
    }
}
