import Models.TransactionItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionItemTest {

    @Test
    void setProductID() {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setProductID(101);
        assertEquals(101, transactionItem.getProductID());
    }

    @Test
    void setQuantity() {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setQuantity(5);
        assertEquals(5, transactionItem.getQuantity());
    }

    @Test
    void getTransactionID() {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setTransactionID(1);
        assertEquals(1, transactionItem.getTransactionID());
    }

    @Test
    void setTransactionID() {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setTransactionID(2);
        assertEquals(2, transactionItem.getTransactionID());
    }

    @Test
    void getProductID() {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setProductID(101);
        assertEquals(101, transactionItem.getProductID());
    }

    @Test
    void getQuantity() {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setQuantity(5);
        assertEquals(5, transactionItem.getQuantity());
    }

}
