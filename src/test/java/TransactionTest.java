import Models.Transaction;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void getTransactionID() {
        Transaction transaction = new Transaction(1, 1001, 50.0, new Timestamp(System.currentTimeMillis()), 1, 5);
        assertEquals(1, transaction.getTransactionID());
    }

    @Test
    void getUserID() {
        Transaction transaction = new Transaction(1, 1001, 50.0, new Timestamp(System.currentTimeMillis()), 1, 5);
        assertEquals(1001, transaction.getUserID());
    }

    @Test
    void getTotalCost() {
        Transaction transaction = new Transaction(1, 1001, 50.0, new Timestamp(System.currentTimeMillis()), 1, 5);
        assertEquals(50.0, transaction.getTotalCost());
    }

    @Test
    void getTransactionDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction(1, 1001, 50.0, timestamp, 1, 5);
        assertEquals(timestamp, transaction.getTransactionDate());
    }

    @Test
    void setUserID() {
        Transaction transaction = new Transaction();
        transaction.setUserID(1002);
        assertEquals(1002, transaction.getUserID());
    }

    @Test
    void setTotalCost() {
        Transaction transaction = new Transaction();
        transaction.setTotalCost(75.0);
        assertEquals(75.0, transaction.getTotalCost());
    }

    @Test
    void setTransactionDate() {
        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(newTimestamp);
        assertEquals(newTimestamp, transaction.getTransactionDate());
    }

    @Test
    void setTransactionID() {
        Transaction transaction = new Transaction();
        transaction.setTransactionID(2);
        assertEquals(2, transaction.getTransactionID());
    }
}
