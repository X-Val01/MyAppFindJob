package KhvalAlexandr.myjob.UsersInfo;

public class User {
    private String email,psw;




    public User(String email, String psw) {
        this.email = email;
        this.psw = psw;

    }
    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }




}

