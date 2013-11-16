package concurrency.stm;

/**
 * @author mishadoff
 */
public final class STM {
    private STM() {}

    public static final Object commitLock = new Object();

    public static <T> T transaction(TransactionBlock block, T value) {
        boolean committed = false;
	    T retval = null;
        while (!committed) {
            Transaction tx = new Transaction();
            block.setTx(tx);
            block.run();
            committed = tx.commit();
	        tx.setValue(value);
	        retval = (T)tx.getValue();
        }
	    return retval;
    }

}
