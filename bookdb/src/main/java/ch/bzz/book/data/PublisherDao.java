package ch.bzz.book.data;

import ch.bzz.book.model.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * data access for book entity
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter, Nirushaan Sureshkumar
 * @version 1.0
 * @since 2019-10-13
 */

public class PublisherDao implements Dao<Publisher, String>{
    /**
     * reads all publishers in the table "Publisher"
     * @return list of publishers
     */
    @Override
    public List<Publisher> getAll() {

        List<Publisher> publisherList = new ArrayList<>();
        try {
            String sqlQuery =
                    "SELECT publisherUUID, publisher" +
                            "  FROM Publisher" +
                            " ORDER BY publisher";
            ResultSet resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                setValues(resultSet, publisher);
                publisherList.add(publisher);
            }

        } catch (SQLException sqlEx) {
            MySqlDB.printSQLException(sqlEx);
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return publisherList;

    }

    /**
     * sets the values of the attributes from the resultset
     *
     * @param resultSet the resultSet with an entity
     * @param publisher      a publisher object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Publisher publisher)
            throws SQLException{
        publisher.setPublisherUUID(resultSet.getString("publisherUUID"));
        publisher.setPublisher(resultSet.getString("publisher"));
    }

}
