public class Customer extends Person {  // Requirement 6: Inheritance

    private String email;  // Requirement 2: Variables

    private String phoneNumber;  // Requirement 2: Variables

    public Customer(String name, String email, String phoneNumber){  // Requirement 16: Constructor
        super(name);  // Requirement 17: Super Keyword
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
