package com.example.graduationproject.Modules;


public class PationtsModule {

    private String fullname,email,phone,password,confirmPass;

    public PationtsModule(String fullname,String email, String phone, String password, String confirmPass) {
        this.fullname = fullname;
       this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmPass = confirmPass;
    }
    public  PationtsModule() {

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
