public class Customer extends Person {

    private String email;

    private String phoneNumber;

    public Customer(String name, String email, String phoneNumber){
        super(name);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    @Override
    public String toString(){
        return "Customer Name: " + getName() + ", Email: " + email + ", Phone: " + phoneNumber;
    }
}
