import com.bookStore.controller.BookController;
import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private MyBookListService myBookListService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllBook() {
        List<Book> bookList = new ArrayList<>();
        when(bookService.getAllBook()).thenReturn(bookList);

        ModelAndView modelAndView = bookController.getAllBook();

        assertEquals("bookList", modelAndView.getViewName());
        assertEquals(bookList, modelAndView.getModel().get("book"));
        verify(bookService, times(1)).getAllBook();
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        String redirect = bookController.addBook(book);

        assertEquals("redirect:/available_books", redirect);
        verify(bookService, times(1)).save(book);
    }

    /*@Test
    void testGetMyBooks() {
        List<MyBookList> myBookList = new ArrayList<>();
        when(myBookListService.getAllMyBooks()).thenReturn(myBookList);

        String viewName = bookController.getMyBooks(model);

        assertEquals("myBooks", viewName);
        assertEquals(myBookList, model.getAttribute("book"));
        verify(myBookListService, times(1)).getAllMyBooks();
    }*/

    @Test
    void testGetMyList() {
        int id = 1;
        Book book = new Book(id, "Test Book", "Test Author", "200");
        when(bookService.getBookById(id)).thenReturn(book);

        String redirect = bookController.getMyList(id);

        assertEquals("redirect:/my_books", redirect);
        verify(myBookListService, times(1)).saveMyBooks(any(MyBookList.class));
    }

    /*@Test
    void testEditBook() {
        int id = 1;
        Book book = new Book(id, "Test Book", "Test Author", "200");
        when(bookService.getBookById(id)).thenReturn(book);

        String viewName = bookController.editBook(id, model);

        assertEquals("bookEdit", viewName);
        assertEquals(book, model.getAttribute("book"));
        verify(bookService, times(1)).getBookById(id);
    }*/

    @Test
    void testDeleteBook() {
        int id = 1;
        String redirect = bookController.deleteBook(id);

        assertEquals("redirect:/available_books", redirect);
        verify(bookService, times(1)).deleteById(id);
    }
}
