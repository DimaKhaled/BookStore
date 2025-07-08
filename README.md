# Bookstore System
Overview
This Java project is a simple bookstore management system. It handles three types of books: physical books (PaperBook), e-books (EBook), and display-only books (ShowcaseBook). You can add books to an inventory, remove old books, and process purchases. A test class Bookstore runs tests to show how the system works.

Features

-Add books with unique ISBNs (auto-generated as numbers: “1”, “2”, etc.).
-Prevent adding duplicate books (same title, year, price).
-Remove books older than 10 years (before 2015, as of 2025).
-Buy physical books (updates stock) or e-books (one copy only).
-Block purchases of display-only books.
-Handle errors like invalid ISBNs, negative quantities, or insufficient stock.

Project Structure

Book: Abstract class for books with title, year, price, and ISBN.
PaperBook: Physical books with stock quantity and shipping notice.
EBook: E-books with file type (e.g., “EPUB”) and email notice.
ShowcaseBook: Display-only books that can’t be bought.
Inventory: Stores books and manages adding, removing, and listing.
BookNotFoundException: Error for invalid ISBNs.
BookstoreFunctionalities: Handles book purchases.
Bookstore: Test class with 11 test cases.

Test Cases
The test class runs 11 tests:

-Add 4 books (physical, e-book, showcase, e-book).
-Try adding a duplicate book (should fail).
-Remove books from 2015 or earlier.
-Buy 2 physical books (updates stock).
-Buy 1 e-book (sends email notice).
-Try buying a showcase book (should fail).
-Try buying with an invalid ISBN (should fail).
-Try buying with negative quantity (should fail).
-Try buying too many physical books (should fail).
-Try buying multiple e-books (should fail).
-Try operations on an empty inventory (should fail).

Example Output
**Test 1: Adding Books**
**Books in inventory**
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}
Book {ISBN = 3, Title = Harry Potter and the Philosopher?s Stone, Publish Year = 1997, Price = 200.0}
Book {ISBN = 4, Title = A Thousand Splendid Suns, Publish Year = 2007, Price = 250.0}
------------------------------------------

**Test 2: Adding Duplicate Book**
Expected error: Wild Dark Shore book is already in the inventory
------------------------------------------

**Test 3: Remove Outdated Books**
Removed books:
Book {ISBN = 3, Title = Harry Potter and the Philosopher?s Stone, Publish Year = 1997, Price = 200.0}
Book {ISBN = 4, Title = A Thousand Splendid Suns, Publish Year = 2007, Price = 250.0}
**Books in inventory**
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}
------------------------------------------

**Test 4: Buy PaperBook**
Shipping Notice: Wild Dark Shore book will be shipped to the following address 123 Main St
**Your Purchase**
Book title: Wild Dark Shore
Amount: 2
Price/book: 500.0
Total Price: 1000.0
Your purchase was success
Total paid: 1000.0
**Books in inventory**
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}
------------------------------------------

**Test 5: Buy EBook**
Email Notice: Dream Count book will be sent to this email user@example.com
**Your Purchase**
Book title: Dream Count
Amount: 1
Price/book: 300.0
Total Price: 300.0
Your purchase was success
Total paid: 300.0
**Books in inventory**
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}
------------------------------------------

**Test 6: Buy ShowcaseBook**
Expected error: Showcase books are not for sale
------------------------------------------

**Test 7: Buy with Invalid ISBN**
Expected error: No book found with ISBN: 99999
------------------------------------------

**Test 8: Buy with Negative Quantity**
Expected error: Quantity must be positive
------------------------------------------

**Test 9: Buy PaperBook with Insufficient Stock**
Expected error: Not enough quantity in stock for book Wild Dark Shore
------------------------------------------

**Test 10: Buy EBook with Quantity > 1**
Expected error: You can only purchase 1 quantity of this eBook Dream Count
------------------------------------------

**Test 11: Operations on Empty Inventory**
Inventory is empty
Expected error: No book found with ISBN: 1
