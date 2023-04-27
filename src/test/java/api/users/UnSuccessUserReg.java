package api.users;

public class UnSuccessUserReg extends Register{
    private String error;

    public UnSuccessUserReg(String error,
                            String email,
                            String password) {
        super(email,password);
        this.error = error;
    }

    public UnSuccessUserReg() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error){
        this.error = error;

    }

    public String getEmail(){
        return super.getEmail();
    }

    public void setEmail(String email){
        super.setEmail(email);
    }

    public String getPassword(){
        return super.getPassword();
    }

    public void setPassword(String password){
        super.setPassword(password);
    }
}
