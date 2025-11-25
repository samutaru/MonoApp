package com.MonoApp.MonoApp.dto;


public class UserRegisterDto {
private String name;
private String mail;
private String password;
private Float cigPrice;
private Integer cigInitial;
private java.sql.Date registerDate;
// getters/setters
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getMail() {
    return mail;
}
public void setMail(String mail) {
    this.mail = mail;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public Float getCigPrice() {
    return cigPrice;
}
public void setCigPrice(Float cigPrice) {
    this.cigPrice = cigPrice;
}
public Integer getCigInitial() {
    return cigInitial;
}
public void setCigInitial(Integer cigInitial) {
    this.cigInitial = cigInitial;
}
public java.sql.Date getRegisterDate() {
    return registerDate;
}
public void setRegisterDate(java.sql.Date registerDate) {
    this.registerDate = registerDate;}

}