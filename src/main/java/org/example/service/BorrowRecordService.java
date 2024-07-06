package org.example.service;

import org.example.model.BorrowRecord;
import org.example.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    public String borrowBook(Long bookId) {
        String borrowCode = generateBorrowCode();
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBookId(bookId);
        borrowRecord.setBorrowCode(borrowCode);
        borrowRecordRepository.save(borrowRecord);
        return borrowCode;
    }

    public Optional<BorrowRecord> findByBorrowCode(String borrowCode) {
        return borrowRecordRepository.findByBorrowCode(borrowCode);
    }

    private String generateBorrowCode() {
        Random random = new Random();
        return String.format("%05d", random.nextInt(100000));
    }
}