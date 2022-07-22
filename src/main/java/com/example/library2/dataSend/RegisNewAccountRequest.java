package com.example.library2.dataSend;

public class RegisNewAccountRequest extends Request{

    public RegisNewAccountRequest(RequestType type) {
        super(type);
    }
    public String acc;
    public String pass;
    public String phone;
    public String email;

    public RegisNewAccountRequest(String acc, String pass, String phone, String email) {
        super(RequestType.REGIS);
        this.acc = acc;
        this.pass = pass;
        this.phone = phone;
        this.email = email;
    }
}
