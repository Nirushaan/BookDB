package ch.bzz.book.service;
import ch.bzz.book.data.Dao;
import ch.bzz.book.data.PublisherDao;
import ch.bzz.book.model.Publisher;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * provides services for the publishers
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter, Nirushaan Sureshkumar
 * @version 1.0
 */
@Path("publisher")
public class PublisherService {
    /**
     * produces a list of all publishers
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listPublisher(
            @CookieParam("token") String token
    ) {
        int httpStatus = 200;
        Dao<Publisher, String> publisherDao = new PublisherDao();
        List<Publisher> publisherList = publisherDao.getAll();
        if (publisherList.isEmpty())
            httpStatus = 404;

        return Response
                .status(httpStatus)
                .entity(publisherList)
                .build();
    }
}
