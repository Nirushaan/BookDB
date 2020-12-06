package ch.bzz.book.data;

import ch.bzz.book.model.Book;
import ch.bzz.book.model.Publisher;
import ch.bzz.book.util.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * data access for book entity
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter, Nirushaan Sureshkumar
 * @version 1.0
 * @since 2019-10-13
 */
public class BookDao implements Dao<Book, String> {

    /**
     * reads all books in the table "Book"
     *
     * @return list of books
     */
    @Override
    public List<Book> getAll() {
        List<Book> bookList = new ArrayList<>();
        String sqlQuery =
                "SELECT b.bookUUID, b.title, b.author, b.price, b.isbn," +
                        "       p.publisherUUID, p.publisher" +
                        "  FROM Book AS b JOIN Publisher AS p USING (publisherUUID)" +
                        " ORDER BY title";
        try {
            ResultSet resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Book book = new Book();
                setValues(resultSet, book);
                bookList.add(book);
            }
        } catch (SQLException sqlEx) {
            MySqlDB.printSQLException(sqlEx);
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }
        return bookList;

    }

    /**
     * reads a book from the table "Book" identified by the bookUUID
     *
     * @param bookUUID the primary key
     * @return book object
     */
    @Override
    public Book getEntity(String bookUUID) {
        Book book = new Book();

        String sqlQuery =
                "SELECT b.bookUUID, b.title, b.author, b.price, b.isbn, " +
                        "       p.publisherUUID, p.publisher" +
                        "  FROM Book AS b JOIN Publisher AS p USING (publisherUUID)" +
                        " WHERE bookUUID=?";
        Map<Integer, String> values = new HashMap<>();
        values.put(1, bookUUID);
        try {
            ResultSet resultSet = MySqlDB.sqlSelect(sqlQuery, values);
            if (resultSet.next()) {
                setValues(resultSet, book);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return book;

    }

    /**
     * saves a book in the table "Book"
     *
     * @param book the book object
     * @return Result code
     */
    @Override
    public Result save(Book book) {
        Map<Integer, String> values = new HashMap<>();
        String sqlQuery;
        if (book.getBookUUID() == null) {
            book.setBookUUID(UUID.randomUUID().toString());
            sqlQuery = "INSERT INTO Book";
        } else {
            sqlQuery = "REPLACE Book";
        }
        sqlQuery += " SET bookUUID=?," +
                " title=?," +
                " author=?," +
                " publisherUUID=?," +
                " price=?," +
                " isbn=?";

        values.put(1, book.getBookUUID());
        values.put(2, book.getTitle());
        values.put(3, book.getAuthor());
        values.put(4, book.getPublisher().getPublisherUUID());
        values.put(5, book.getPrice().toString());
        values.put(6, book.getIsbn());

        try {
            return MySqlDB.sqlUpdate(sqlQuery, values);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * daletes a book in the table "Book" identified by the bookUUID
     *
     * @param bookUUID the primary key
     * @return Result code
     */
    @Override
    public Result delete(String bookUUID) {
        String sqlQuery =
                "DELETE FROM Book" +
                        " WHERE bookUUID=?";
        Map<Integer, String> values = new HashMap<>();
        values.put(1, bookUUID);

        try {
            return MySqlDB.sqlUpdate(sqlQuery, values);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * count all books in table Book
     *
     * @return number of books
     */
    public Integer count() {

        String sqlQuery =
                "SELECT COUNT(bookUUID)" +
                        "   FROM Book";
        int bookCount = -1;

        try {
            ResultSet resultSet = MySqlDB.sqlSelect(sqlQuery);
            if (resultSet.next()) {
                bookCount = resultSet.getInt(1);
            }
        } catch (SQLException sqlEx) {
            MySqlDB.printSQLException(sqlEx);
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }

        return bookCount;
    }

    /**
     * sets the values of the attributes from the resultset
     *
     * @param resultSet the resultSet with an entity
     * @param book      a book object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Book book) throws SQLException {
        book.setBookUUID(resultSet.getString("bookUUID"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublisher(new Publisher());
        book.getPublisher().setPublisherUUID(resultSet.getString("publisherUUID"));
        book.getPublisher().setPublisher(resultSet.getString("publisher"));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setIsbn(resultSet.getString("isbn"));
    }

}
