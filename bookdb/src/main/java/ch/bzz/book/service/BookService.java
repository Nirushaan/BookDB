package ch.bzz.book.service;

import ch.bzz.book.data.Dao;
import ch.bzz.book.data.BookDao;
import ch.bzz.book.model.Book;
import ch.bzz.book.model.Publisher;
import ch.bzz.book.util.Result;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * service controller for books
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter (Ghwalin), Nirushaan Sureshkumar
 */
@Path("bookshelf")
public class BookService {

    /**
     * produces the number of books
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)

    public Response countBooks(
            @CookieParam("token") String token
    ) {

        int httpStatus = 200;
        Integer bookCount = new BookDao().count();

        return Response
                .status(httpStatus)
                .entity("{\"bookCount\":" + bookCount + "}")
                .build();
    }

    /**
     * produces a list of all books
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listBooks(
            @CookieParam("token") String token
    ) {

        int httpStatus = 200;
        Dao<Book, String> bookDao = new BookDao();
        List<Book> bookList = bookDao.getAll();
        if (bookList.isEmpty())
            httpStatus = 404;

        return Response
                .status(httpStatus)
                .entity(bookList)
                .build();
    }

    /**
     * reads a single book identified by the bookId
     *
     * @param bookUUID the bookUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readBook(
            @QueryParam("uuid") String bookUUID,
            @CookieParam("token") String token
    ) {
        int httpStatus = 200;
        Dao<Book, String> bookDAO = new BookDao();
        Book book = bookDAO.getEntity(bookUUID);
        if (book.getTitle() == null)
            httpStatus = 404;

        return Response
                .status(httpStatus)
                .entity(book)
                .build();
    }

    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveBook(
            @QueryParam("uuid") String bookUUID,
            @Valid @BeanParam Book book,
            @Valid @BeanParam Publisher publisher,
            @CookieParam("token") String token
    ) {
        int httpStatus;
        String message;
        book.setBookUUID(bookUUID);
        book.setPublisher(publisher);
        Dao<Book, String> bookDao = new BookDao();
        Result result = bookDao.save(book);
        if (result == Result.SUCCESS) {
            message = "Buch gespeichert";
            httpStatus = 200;
        }
        else {
            message = "Fehler beim Speichern des Buchs";
            httpStatus = 500;
        }
        return Response
                .status(httpStatus)
                .entity(message)
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String bookUUID,
            @CookieParam("token") String token
    ) {
        int httpStatus = 200;
        Dao<Book, String> bookDao = new BookDao();
        Result result = bookDao.delete(bookUUID);
        if (result != Result.SUCCESS) httpStatus = 500;
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
