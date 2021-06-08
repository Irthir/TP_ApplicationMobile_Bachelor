package com.example.lapersistancedesdonnees4bddmysql;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String sUsername;
    private String sEmail;
    private String sLocalite;
    private String sDDN;

    public User() {
    }

    protected User(Parcel in) {
        sUsername = in.readString();
        sEmail = in.readString();
        sLocalite = in.readString();
        sDDN = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //Les Assesseurs
    public String getUsername(){ return sUsername;}
    public String getEmail(){ return sEmail;}
    public String getLocalite(){ return sLocalite;}
    public String getDDN(){ return sDDN;}


    //Les Mutateurs
    public void setUsername(String Username) { this.sUsername = Username;}
    public void setEmail(String Email) { this.sEmail = Email;}
    public void setLocalite(String Localite) { this.sLocalite = Localite;}
    public void setDDN(String DDN) { this.sDDN = DDN;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(sUsername);
        dest.writeString(sEmail);
        dest.writeString(sLocalite);
        dest.writeString(sDDN);
    }

}