package org.example.controller;

import org.example.model.Book;
import org.example.model.BorrowRecord;
import org.example.service.BookService;
import org.example.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowRecordService borrowRecordService;

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "error";
        }
        model.addAttribute("book", book);
        return "borrow";
    }

    @PostMapping("/books/borrow")
    public String borrowBook(@RequestParam Long bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        if (book.getQuantity() <= 0) {
            return "error";
        }
        book.setQuantity(book.getQuantity() - 1);
        bookService.save(book);
        String borrowCode = borrowRecordService.borrowBook(bookId);
        model.addAttribute("message", "Book borrowed successfully with code: " + borrowCode);
        return "success";
    }

    @GetMapping("/books/return")
    public String returnBookForm() {
        return "return";
    }

    @PostMapping("/books/return")
    public String returnBook(@RequestParam String borrowCode, Model model) {
        Optional<BorrowRecord> recordOpt = borrowRecordService.findByBorrowCode(borrowCode);
        if (!recordOpt.isPresent()) {
            return "error";
        }
        BorrowRecord record = recordOpt.get();
        Book book = bookService.getBookById(record.getBookId());
        book.setQuantity(book.getQuantity() + 1);
        bookService.save(book);
        model.addAttribute("message", "Book returned successfully");
        return "success";
    }
}