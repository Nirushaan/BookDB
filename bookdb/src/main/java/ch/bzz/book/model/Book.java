package ch.bzz.book.model;

import ch.bzz.book.model.Publisher;

import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * a book in the shelf
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter (Ghwalin), Nirushaan Sureshkumar
 */
public class Book {

    private String bookUUID;

    @FormParam("title")
    //@Size(min=5, max=40)
    private String title;

    @FormParam("author")
    //@Size(min=3, max=40)
    private String author;

    private Publisher publisher;

    @FormParam("price")
    //@DecimalMin(value="0.05")
    //@DecimalMax(value="199.95")
    private BigDecimal price;

    @FormParam("isbn")
    //@Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private String isbn;

    /**
     * default constructor
     */
    public Book() {

    }

    /**
     * @return the bookUUID
     */
    public String getBookUUID() {
        return bookUUID;
    }

    /**
     * sets the bookUUID if valid
     * @param bookUUID the bookUUID to be set
     */
    public void setBookUUID(String bookUUID) {
        this.bookUUID = bookUUID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the publisher
     *
     * @return value of publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher
     *
     * @param publisher the value to set
     */

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}