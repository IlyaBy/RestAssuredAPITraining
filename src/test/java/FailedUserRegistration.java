public class FailedUserRegistration extends UserRegistrationData{
    private String error;

    public FailedUserRegistration(String error) {
        this.error = error;
    }

    public FailedUserRegistration() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error){
        this.error = error;

    }

}
