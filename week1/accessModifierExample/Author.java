package accessModifierExample;


public class Author {
	private String name; 
	private String email;
	private char gender;
	Author(String name, String email, String gender) {
		this.name = name;
		this.email = email;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getGender() {
		return gender;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public String toDisplay() {
		return String.format("%s (%s) at %s", name, gender, email);
	}

	public static void main(String[] args) {
		Author a = new Author("R.K. Narayan", "rknarayan@gmail.com", "M");
		System.out.println(a.toDisplay());
		a.setEmail("narayanrk@gmail.com");
		System.out.println(a.toDisplay());
	}
}