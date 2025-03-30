package constant;

public class UserCanLogOn {
    private String email;
    private String password;

    public UserCanLogOn(String email, String password){
        this.email = email;
        this.password = password;
    }

    public UserCanLogOn(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
