public class UserRegistrationData {
    private String email;
    private String password;

    public UserRegistrationData() {
        super();
    }

    public UserRegistrationData(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public UserRegistrationData(String email) {
        this.email = email;

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
