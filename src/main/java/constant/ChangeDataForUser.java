package constant;

public class ChangeDataForUser {
    private String email;
    private String name;

    public ChangeDataForUser(String email, String name){
        this.email = email;
        this.name = name;
    }

    public ChangeDataForUser(){
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
