import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract class Book{
    private String isbn;
    private String title;
    private Year year;
    private float price;
    private static int counter = 0;

    Book(String title, Year year, float price){
        this.title = title;
        this.year = year;
        this.price = price;
        counter++;
        this.isbn = Integer.toString(counter);
    }

    String getTitle(){
        return title;
    }

    String getIsbn(){
        return isbn;
    }

    Year getYear(){
        return year;
    }

    float getPrice(){
        return price;
    }

    // check if two books are the same (excluding the isbn)
    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        
        if(!(obj instanceof Book))
            return false;

        Book b = (Book)obj;

        return (this.title.equals(b.getTitle()) && (this.year.getValue() == b.getYear().getValue()) && (Float.compare(price, b.getPrice()) == 0));
    }

    @Override
    public String toString(){
        return "Book {ISBN = " + isbn + ", Title = " + title + ", Publish Year = " + year + ", Price = " + price + "}";
    }

    abstract void UpdateBookAfterPurchase(int quantity, String cutomerEmail, String customerAddress);
}



class PaperBook extends Book{
    private int quantity;

    PaperBook(String title, Year year, float price, int quantity){
        super(title, year, price);
        this.quantity = quantity;
    }

    public void ReduceBookQuantity(int amount){
        if (amount <= quantity) {
            quantity -= amount;
        }

        else if(amount <= 0){
            throw new IllegalArgumentException("Quantity must be positive");
        }

        else{
            throw new IllegalArgumentException("Not enough quantity in stock for book " + getTitle());
        }
    }

    void ShipPaperBook(String recipientAddress){
        System.out.println(getTitle() + " book will be shipped to the following address " + recipientAddress);
    }
    
    @Override
    void UpdateBookAfterPurchase(int quantity, String cutomerEmail, String customerAddress) {
        ReduceBookQuantity(quantity);
        ShipPaperBook(customerAddress);        
    }
}



class EBook extends Book{
    private String filetype;
    
    EBook(String title, Year year, float price, String filetype){
        super(title, year, price);
        this.filetype = filetype;
    }

    void SendEbookByEmail(String recipientEmail){
        System.out.println(getTitle() + " book was sent successfully to this email " + recipientEmail);
    }

    @Override
    void UpdateBookAfterPurchase(int quantity, String cutomerEmail, String customerAddress){
        if (quantity != 1) {
            throw new IllegalArgumentException("You can only purchase 1 quantity of this eBook " + getTitle());
        }
        SendEbookByEmail(customerAddress);
    }
} 



class ShowcaseBook extends Book{
    ShowcaseBook(String title, Year year, float price){
        super(title, year, price);
    }

    @Override
    void UpdateBookAfterPurchase(int quantity, String cutomerEmail, String customerAddress){
        throw new IllegalArgumentException("Showcase books are not for sale");
    }
}


class Inventory{
    private Map<String, Book> books;

    Inventory(){
        books = new HashMap<>();
    }

    // check if book is already in the inventory but searching by its info not isbn because we don't want two books
    // with the same title, year, price to be both in the inventory
    boolean IsBookInInventory(Book book){
        for(Book b : books.values()){
        
            if (book.equals(b)) {   // use the override of the equals function that we made in the book class
                return true;
            }
        }
        return false;
    }

    void AddBook(Book book){
        if (!IsBookInInventory(book)) {
            books.put(book.getIsbn(), book);
        }
        else{
            throw new IllegalStateException(book.getTitle() + " book is already in the inventory");
        }
    }

    void RemoveBook(Book book){
        books.remove(book.getIsbn());
    }

    // return list of outdated books that were removed
    List<Book> RemoveOutdatedBook(){
        List<Book> outdatedBooks = new ArrayList<>();
        // let outdated period be 10 years
        int y = Year.now().getValue() - 10;    

        Iterator<Map.Entry<String, Book>> i = books.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, Book> b = i.next();
            Book book = b.getValue();

            if (book.getYear().getValue() <= y){
                outdatedBooks.add(book);
                i.remove();
            }
        }
        return outdatedBooks;
    }

    void getBooksInInventory(){
        if (books.isEmpty()) {
            System.out.println("Inventory is empty");
        }
        else{
            System.out.println("**Books in inventory**");

            for(Book book : books.values()){
                System.out.println(book.toString());                 
            }
        }
    }

    public Book getBookByIsbn(String isbn){
        if (!books.containsKey(isbn)) {
            throw new BookNotFoundException("No book found with ISBN: " + isbn);
        }
        return books.get(isbn);
    }
}


class BookNotFoundException extends RuntimeException{
    BookNotFoundException(String message){
        super(message);
    }
}


class BookstoreFunctionalities{
    Inventory inventory;
    BookstoreFunctionalities(){
        inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public float Buy(String isbn, int quantity, String customerEmail, String CustomerAddress){
        Book purchasedBook = inventory.getBookByIsbn(isbn);
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        float Totalprice = quantity * purchasedBook.getPrice();

        System.out.println("-------------------------------------");
        System.out.println("**Your Purchase**");
        System.out.println("Book title: " + purchasedBook.getTitle());
        System.out.println("Amount: " + quantity);
        System.out.println("Price/book: " + purchasedBook.getPrice());
        System.out.println("Total Price: " + Totalprice);
        System.out.println("\nYour purchase was success");
    
        purchasedBook.UpdateBookAfterPurchase(quantity, customerEmail, CustomerAddress);

        return Totalprice;
    }
}


// test class
public class Bookstore {
    public static void main(String[] args){
        BookstoreFunctionalities bookstore = new BookstoreFunctionalities();
        Inventory inventory = bookstore.getInventory();

        // Test 1: Add books to inventory
        System.out.println("**Test 1: Adding Books**");
        try {
            inventory.addBook(new PaperBook("Wild Dark Shore", "Charlotte McConaghy", Year.of(2025), 500, 5));
            inventory.addBook(new EBook("Dream Count", "Chimamanda Ngozi Adichie", Year.of(2025), 300, "EPUB"));
            inventory.addBook(new ShowcaseBook("Harry Potter and the Philosopher’s Stone", "J.K. Rowling", Year.of(1997), 200));
            inventory.addBook(new EBook("A Thousand Splendid Suns", "Khaled Hosseini", Year.of(2007), 250, "EPUB"));
            inventory.displayBooksInInventory();
        } catch (Exception e) {
            System.out.println("Quantum book store: Error: " + e.getMessage());
        }

        // Test 2: Add duplicate book
        System.out.println("\nQuantum book store: **Test 2: Adding Duplicate Book**");
        try {
            inventory.addBook(new PaperBook("9789999999999", "Wild Dark Shore", "Charlotte McConaghy", Year.of(2025), 500, 3));
        } catch (IllegalStateException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 3: Remove outdated books (1997 and 2007)
        System.out.println("\nQuantum book store: **Test 3: Remove Outdated Books**");
        try {
            List<Book> removedBooks = inventory.removeOutdatedBooks();
            System.out.println("Quantum book store: Removed books:");
            if (removedBooks.isEmpty()) {
                System.out.println("Quantum book store: No books removed");
            } else {
                for (Book book : removedBooks) {
                    System.out.println("Quantum book store: " + book.toString());
                }
            }
            inventory.displayBooksInInventory();
        } catch (Exception e) {
            System.out.println("Quantum book store: Error: " + e.getMessage());
        }

        // Test 4: Buy a PaperBook
        System.out.println("\nQuantum book store: **Test 4: Buy PaperBook**");
        try {
            float total = bookstore.buy("978125081762", 2, "user@example.com", "123 Main St");
            System.out.println("Quantum book store: Total paid: " + total);
            inventory.displayBooksInInventory();
        } catch (Exception e) {
            System.out.println("Quantum book store: Error: " + e.getMessage());
        }

        // Test 5: Buy an EBook
        System.out.println("\nQuantum book store: **Test 5: Buy EBook**");
        try {
            float total = bookstore.buy("9780593315293", 1, "user@example.com", "123 Main St");
            System.out.println("Quantum book store: Total paid: " + total);
            inventory.displayBooksInInventory();
        } catch (Exception e) {
            System.out.println("Quantum book store: Error: " + e.getMessage());
        }

        // Test 6: Buy a ShowcaseBook (should fail)
        System.out.println("\nQuantum book store: **Test 6: Buy ShowcaseBook**");
        try {
            bookstore.buy("9780747532743", 1, "user@example.com", "123 Main St");
        } catch (IllegalStateException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 7: Buy with invalid ISBN
        System.out.println("\nQuantum book store: **Test 7: Buy with Invalid ISBN**");
        try {
            bookstore.buy("9999999999999", 1, "user@example.com", "123 Main St");
        } catch (BookNotFoundException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 8: Buy with null ISBN
        System.out.println("\nQuantum book store: **Test 8: Buy with Null ISBN**");
        try {
            bookstore.buy(null, 1, "user@example.com", "123 Main St");
        } catch (IllegalArgumentException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 9: Buy with negative quantity
        System.out.println("\nQuantum book store: **Test 9: Buy with Negative Quantity**");
        try {
            bookstore.buy("978125081762", -1, "user@example.com", "123 Main St");
        } catch (IllegalArgumentException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 10: Buy PaperBook with insufficient stock
        System.out.println("\nQuantum book store: **Test 10: Buy PaperBook with Insufficient Stock**");
        try {
            bookstore.buy("978125081762", 10, "user@example.com", "123 Main St");
        } catch (IllegalArgumentException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 11: Buy EBook with quantity > 1
        System.out.println("\nQuantum book store: **Test 11: Buy EBook with Quantity > 1**");
        try {
            bookstore.buy("9780593315293", 2, "user@example.com", "123 Main St");
        } catch (IllegalArgumentException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 12: Operations on empty inventory
        System.out.println("\nQuantum book store: **Test 12: Operations on Empty Inventory**");
        try {
            inventory = new Inventory(); // Reset inventory
            inventory.displayBooksInInventory();
            bookstore.buy("978125081762", 1, "user@example.com", "123 Main St");
        } catch (BookNotFoundException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }

        // Test 13: Add null book
        System.out.println("\nQuantum book store: **Test 13: Add Null Book**");
        try {
            inventory.addBook(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Quantum book store: Expected error: " + e.getMessage());
        }
    }
}
