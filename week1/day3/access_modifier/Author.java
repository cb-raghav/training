package week1.day3.access_modifier;


public class Author {
	private String name; 
	private String email;
	private char gender;

	Author(String name, String email, char gender) { // parameterized constructor
		this.name = name;
		this.email = email;
		this.gender = gender;
	}

	// Getter functions for data members
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public char getGender() {
		return gender;
	}

	// Setter function for 'email'
	public void setEmail(String newEmail) {
		email = newEmail;
	}

	// Returns a formatted String describing the author
	public String toDisplay() {
		return String.format("%s (%s) at %s", name, gender, email);
	}

	public static void main(String[] args) {
		Author a = new Author("R.K. Narayan", "rknarayan@gmail.com", 'M');
		System.out.println(a.toDisplay());
		a.setEmail("narayanrk@gmail.com");
		System.out.println(a.toDisplay());
	}
}