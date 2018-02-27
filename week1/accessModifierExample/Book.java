package accessModifierExample;

import java.util.*;

public class Book {
	private String name;
	private Author[] authors;
	private double price;
	private int quant;
	private int numAuthors;

	Book(String name, Author[] authors, double price, int quant) {
		this.authors = new Author[100];
		this.name = name;
		//this.authors = authors;
		this.numAuthors = authors.length;
		for(int i = 0; i < numAuthors; i++) {
			this.authors[i] = authors[i];
		}
		this.price = price;
		this.quant = quant;
	}

	Book(String name, Author author, double price, int quant) {
		this.authors = new Author[100];
		this.name = name;
		this.numAuthors = 1;
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
		for(int i = 0; i < numAuthors; i++) {
			result.append(String.format("author %d - ", (int)(i + 1)));
			result.append(authors[i].toDisplay());
			result.append(", ");
		}
		result.append("\nPrice: " + price);
		result.append("\nQuantity in stock: " + quant);
		return result.toString();
	}

	public void printAuthors() {
		System.out.println("The authors of this book are - ");
		for(int i = 0; i < numAuthors; i++) {
			System.out.print(authors[i].toDisplay());
			if(i != numAuthors - 1) {
				System.out.print(",\n");
			}
		}
	}

	public void addAuthor() {
		System.out.println("Enter the details of the author - ");
		Scanner s = new Scanner(System.in);
		s.useDelimiter("\n");
		System.out.print("Name: "); String name = s.next();
		System.out.print("Email: "); String email = s.next();
		System.out.print("Gender: "); char gender = s.next().charAt(0);
		Author a = new Author(name, email, gender);
		authors[numAuthors] = a;
		numAuthors++;
	}

	public static void main(String[] args) {
		int numBooks = 5;
		Scanner s = new Scanner(System.in);
		s.useDelimiter("\n");
		Book[] bookList = new Book[numBooks];
		for(int i = 0; i < numBooks; i++) {
			System.out.println("\nBook " + (int)(i + 1) + " - ");
			bookList[i] = Util.addBook(s);
		}
		System.out.println("\nNumber of books: " + numBooks);
		System.out.println("1. Add an author for a particular book");
		System.out.println("2. Display the authors of a particular book");
		System.out.println("3. Print the details of all the available books");
		System.out.println("0. Exit");
		int opt;
		do {
			System.out.print("\nEnter your option: "); opt = s.nextInt();
			switch(opt) {
				case 1: {
					System.out.print("Enter the book number (1 - " + numBooks + ") : ");
					int bookNumber = s.nextInt();
					System.out.println("Details of the book - \n" + bookList[bookNumber - 1].toDisplay());
					bookList[bookNumber - 1].addAuthor();
				}
				break;
				case 2: {
					System.out.print("Enter the book number (1 - " + numBooks + ") : ");
					int bookNumber = s.nextInt();
					bookList[bookNumber - 1].printAuthors();
				}
				break;
				case 3: {
					for(int i = 0; i < numBooks; i++) {
						System.out.println((int)(i + 1) + ".\n" + bookList[i].toDisplay());
					}
				}
				break;
			}
		}
		while(opt != 0);
	}
}