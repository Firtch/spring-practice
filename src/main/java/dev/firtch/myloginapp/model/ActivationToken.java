package dev.firtch.myloginapp.model;

public class ActivationToken {

    private Long id;
    private String token;
    private String profile;

    public ActivationToken() {
    }

    public ActivationToken(String token, String profile) {
        this.token = token;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
