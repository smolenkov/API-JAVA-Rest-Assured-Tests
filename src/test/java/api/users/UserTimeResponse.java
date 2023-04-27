package api.users;

public class UserTimeResponse extends UserTime{
    private String updatedAt;

    public UserTimeResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public UserTimeResponse(String name, String job) {
        super(name, job);
    }
    public UserTimeResponse() {
        super();
    }


    public String getUpdatedAt() {
        return updatedAt;
    }
}
