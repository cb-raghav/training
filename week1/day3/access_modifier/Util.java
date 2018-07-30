package week1.day3.access_modifier;

import java.util.*;

public class Util {
	/*
		utility funtion to add a book to the list of books
		Parameters: 's' (Scanner object)
	*/
	public static Book addBook(Scanner s) {
		// Accept the details of the book to be added
		System.out.print("Name of book: "); String bookName = s.next();
		System.out.print("Price of book: "); double bookPrice = s.nextDouble();
		System.out.print("Quantity in stock: "); int bookQuantity = s.nextInt();
		System.out.print("Number of authors: "); int numAuthors = s.nextInt();
		Author[] authors = new Author[numAuthors];
		for(int i = 0; i < numAuthors; i++) { // Accept the details of all the authors of the book
			System.out.print("Name of author " + (int)(i + 1) + ": "); 
			String authorName = s.next();
			System.out.print("Email ID of author " + (int)(i + 1) + ": "); 
			String authorEmail = s.next();
			System.out.print("Gender of author " + (int)(i + 1) + ": "); 
			char authorGender = s.next().charAt(0);
			authors[i] = new Author(authorName, authorEmail, authorGender);
		}
		Book book = new Book(bookName, authors, bookPrice, bookQuantity); // Create a 'Book' object with the details accepted
		return book;
	}
}