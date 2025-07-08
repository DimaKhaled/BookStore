# Bookstore System
# Overview
This Java project is a simple bookstore management system. It handles three types of books: physical books (PaperBook), e-books (EBook), and display-only books (ShowcaseBook). You can add books to an inventory, remove old books, and process purchases. A test class Bookstore runs tests to show how the system works.

# Features

-Add books with unique ISBNs (auto-generated as numbers: “1”, “2”, etc.).<br>
-Prevent adding duplicate books (same title, year, price).<br>
-Remove books older than 10 years (before 2015, as of 2025).<br>
-Buy physical books (updates stock) or e-books (one copy only).<br>
-Block purchases of display-only books.<br>
-Handle errors like invalid ISBNs, negative quantities, or insufficient stock.<br>

# Project Structure

-Book: Abstract class for books with title, year, price, and ISBN.<br>
-PaperBook: Physical books with stock quantity and shipping notice.<br>
-EBook: E-books with file type (e.g., “EPUB”) and email notice.<br>
-ShowcaseBook: Display-only books that can’t be bought.<br>
-Inventory: Stores books and manages adding, removing, and listing.<br>
-BookNotFoundException: Error for invalid ISBNs.<br>
-BookstoreFunctionalities: Handles book purchases.<br>
-Bookstore: Test class with 11 test cases.<br>

# Test Cases
The test class runs 11 tests:

-Add 4 books (physical, e-book, showcase, e-book).<br>
-Try adding a duplicate book (should fail).<br>
-Remove books from 2015 or earlier.<br>
-Buy 2 physical books (updates stock).<br>
-Buy 1 e-book (sends email notice).<br>
-Try buying a showcase book (should fail).<br>
-Try buying with an invalid ISBN (should fail).<br>
-Try buying with negative quantity (should fail).<br>
-Try buying too many physical books (should fail).<br>
-Try buying multiple e-books (should fail).<br>
-Try operations on an empty inventory (should fail).<br>

# Example Output

**Test 1: Adding Books**<br>
**Books in inventory**<br>
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}<br>
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}<br>
Book {ISBN = 3, Title = Harry Potter and the Philosopher?s Stone, Publish Year = 1997, Price = 200.0}<br>
Book {ISBN = 4, Title = A Thousand Splendid Suns, Publish Year = 2007, Price = 250.0}<br>

**Test 2: Adding Duplicate Book**<br>
Expected error: Wild Dark Shore book is already in the inventory<br>

**Test 3: Remove Outdated Books**<br>
Removed books:<br>
Book {ISBN = 3, Title = Harry Potter and the Philosopher?s Stone, Publish Year = 1997, Price = 200.0}<br>
Book {ISBN = 4, Title = A Thousand Splendid Suns, Publish Year = 2007, Price = 250.0}<br>
**Books in inventory**<br>
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}<br>
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}<br>

**Test 4: Buy PaperBook**<br>
Shipping Notice: Wild Dark Shore book will be shipped to the following address 123 Main St <br>
**Your Purchase** <br>
Book title: Wild Dark Shore<br>
Amount: 2<br>
Price/book: 500.0<br>
Total Price: 1000.0<br>
Your purchase was success<br>
Total paid: 1000.0<br>
**Books in inventory**<br>
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}<br>
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}<br>

**Test 5: Buy EBook**<br>
Email Notice: Dream Count book will be sent to this email user@example.com<br>
**Your Purchase**<br>
Book title: Dream Count<br>
Amount: 1<br>
Price/book: 300.0<br>
Total Price: 300.0<br>
Your purchase was success<br>
Total paid: 300.0<br>
**Books in inventory**<br>
Book {ISBN = 1, Title = Wild Dark Shore, Publish Year = 2025, Price = 500.0}<br>
Book {ISBN = 2, Title = Dream Count, Publish Year = 2025, Price = 300.0}<br>

**Test 6: Buy ShowcaseBook**<br>
Expected error: Showcase books are not for sale<br>

**Test 7: Buy with Invalid ISBN**<br>
Expected error: No book found with ISBN: 99999<br>

**Test 8: Buy with Negative Quantity**<br>
Expected error: Quantity must be positive<br>

**Test 9: Buy PaperBook with Insufficient Stock**<br>
Expected error: Not enough quantity in stock for book Wild Dark Shore<br>

**Test 10: Buy EBook with Quantity > 1**<br>
Expected error: You can only purchase 1 quantity of this eBook Dream Count<br>

**Test 11: Operations on Empty Inventory**<br>
Inventory is empty<br>
Expected error: No book found with ISBN: 1<br>
