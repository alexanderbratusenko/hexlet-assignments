package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::map).toList();
    }

    public BookDTO findBookById(Long id) {
        return bookRepository.findById(id).map(bookMapper::map).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public BookDTO create(BookCreateDTO dto) {
        Book book = bookMapper.map(dto);
        try {
            book = bookRepository.save(book);
        } catch (Exception e) {
            throw new ConstraintViolationException(Collections.emptySet());
        }
        return bookMapper.map(book);
    }

    public BookDTO update(Long id, BookUpdateDTO dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookMapper.update(dto, book);
        book = bookRepository.save(book);
        return bookMapper.map(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
