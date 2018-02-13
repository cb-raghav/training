package accessModifierExample;

public class Book {
	private String name;
	private Author[] authors;
	private double price;
	private int quant;

	Book(String name, Author[] authors, double price, int quant) {
		this.authors = new Author[authors.length]
		this.name = name;
		this.authors = authors;
		this.price = price;
		this.quant = quant;
	}

	Book(String name, Author author, double price, int quant) {
		this.authors = new Author[1];
		this.name = name;
		this.authors[0] = author;
		this.price = price;
		this.quant = quant;
	}

	public String getName() {
		return name;
	}

	public Author[] getAuthors() {
		return authors;
	}

	public double getPrice() {
		return price;
	}

	public int getQuant() {
		return quant;
	}

	public void setPrice(double newPrice) {
		price = newPrice;
	}

	public void setQuant(int newQuant) {
		quant = newQuant;
	}

	public String toDisplay() {
		StringBuilder result = new StringBuilder();
		result.append(String.format("%s by ", name));
		for(int i = 0; i < authors.length; i++) {
			result.append(String.format("author %d - ", i));
			result.append(authors[i].toDisplay());
			result.append(", ");
		}
		result.append("\nPrice: " + price);
		result.append("\nQuantity in stock: " + quant);
	}

	public void printAuthors() {
		System.out.print("The authors of this book are: ");
		for(int i = 0; i < authors.length; i++) {
			System.out.print(authors[i].name);
			if(i != authors.length - 1) {
				System.out.print(", ");
			}
		}
	}

	public void addAuthor() {
		
	}

	public static void main(String[] args) {
		
	}
}