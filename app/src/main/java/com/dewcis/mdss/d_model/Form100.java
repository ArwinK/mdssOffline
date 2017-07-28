package com.dewcis.mdss.d_model;


public class Form100 {
    private String client_rand;
    private String client_name;
    private  String client_phone;
    private String client_subLocation;
    private String client_communityUnit;
    private String client_gender;
    private String client_age;
    private long client_status;
    private long hSublocation;
    private long hCommunityunit;
    private long hFacility;
    private String action;
    private String comments;
    private String reasons;
    private String general, PP_services, PP_familyplanning,
            delivery, start_ANC,followup_ANC, immunization, growthMonitoring,GM_lowWeight;


    public String getClient_phone() {
        return client_phone;
    }

    public String getClient_subLocation() {
        return client_subLocation;
    }

    public String getClient_communityUnit() {
        return client_communityUnit;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getClient_gender() {
        return client_gender;
    }

    public String getClient_age() {
        return client_age;
    }

    public long getClient_status() {
        return client_status;
    }

    public long gethSublocation() {
        return hSublocation;
    }

    public long gethCommunityunit() {
        return hCommunityunit;
    }

    public long gethFacility() {
        return hFacility;
    }

    public String getAction() {
        return action;
    }

    public String getComments() {
        return comments;
    }

    public String getReasons() {
        return reasons;
    }

    public String getGeneral() {
        return general;
    }

    public String getPP_services() {
        return PP_services;
    }

    public String getPP_familyplanning() {
        return PP_familyplanning;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getStart_ANC() {
        return start_ANC;
    }

    public String getFollowup_ANC() {
        return followup_ANC;
    }

    public String getImmunization() {
        return immunization;
    }

    public String getGrowthMonitoring() {
        return growthMonitoring;
    }

    public String getGM_lowWeight() {
        return GM_lowWeight;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public void setClient_subLocation(String client_subLocation) {
        this.client_subLocation = client_subLocation;
    }

    public void setClient_communityUnit(String client_communityUnit) {
        this.client_communityUnit = client_communityUnit;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void setClient_gender(String client_gender) {
        this.client_gender = client_gender;
    }

    public void setClient_age(String client_age) {
        this.client_age = client_age;
    }

    public void setClient_status(long client_status) {
        this.client_status = client_status;
    }

    public void sethSublocation(long hSublocation) {
        this.hSublocation = hSublocation;
    }

    public void sethCommunityunit(long hCommunityunit) {
        this.hCommunityunit = hCommunityunit;
    }

    public void sethFacility(long hFacility) {
        this.hFacility = hFacility;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public void setPP_services(String PP_services) {
        this.PP_services = PP_services;
    }

    public void setPP_familyplanning(String PP_familyplanning) {
        this.PP_familyplanning = PP_familyplanning;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public void setStart_ANC(String start_ANC) {
        this.start_ANC = start_ANC;
    }

    public void setFollowup_ANC(String followup_ANC) {
        this.followup_ANC = followup_ANC;
    }

    public void setImmunization(String immunization) {
        this.immunization = immunization;
    }

    public void setGrowthMonitoring(String growthMonitoring) {
        this.growthMonitoring = growthMonitoring;
    }

    public void setGM_lowWeight(String GM_lowWeight) {
        this.GM_lowWeight = GM_lowWeight;
    }

    @Override
    public String toString() {
        return "BabyModel{"
                + "client_rand='" + client_rand + '\'' +
                ", client_phone='" + client_phone + '\'' +
                ", client_name='" + client_name + '\'' +
                ", client_subLocation='" + client_subLocation + '\'' +
                ", client_communityUnit='" + client_communityUnit + '\'' +
                ", client_gender='" + client_gender + '\'' +
                ", client_status='" + client_status + '\'' +
                ", client_age='" + client_age + '\'' +
                ", hSublocation='" + hSublocation + '\'' +
                ", hCommunityunit='" + hCommunityunit + '\'' +
                ", hFacility='" + hFacility + '\'' +
                ", action='" + action + '\'' +
                ", comments='" + comments + '\'' +
                ", reasons='" + reasons + '\'' +
                ", general='" + general + '\'' +
                ", PP_services='" + PP_services + '\'' +
                ", PP_familyplanning='" + PP_familyplanning + '\'' +
                ", delivery='" + delivery + '\'' +
                ", start_ANC='" + start_ANC + '\'' +
                ", followup_ANC='" + followup_ANC + '\'' +
                ", immunization='" + immunization + '\'' +
                ", growthMonitoring='" + growthMonitoring + '\'' +
                ", GM_lowWeight='" + GM_lowWeight + '\'' +
                '}';
    }
}
