package ch.bzz.book.model;

import javax.ws.rs.FormParam;
/**
 * a book publisher
 * @author Marcel Suter (Ghwalin), Nirushaan Sureshkumar
 */
public class Publisher {
    @FormParam("publisherUUID")
    private String publisherUUID;
    private String publisher;

    /**
     * Gets the publisherUUID
     *
     * @return value of publisherUUID
     */
    public String getPublisherUUID() {
        return publisherUUID;
    }

    /**
     * Sets the publisherUUID
     *
     * @param publisherUUID the value to set
     */

    public void setPublisherUUID(String publisherUUID) {
        this.publisherUUID = publisherUUID;
    }

    /**
     * Gets the publisher
     *
     * @return value of publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher
     *
     * @param publisher the value to set
     */

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
