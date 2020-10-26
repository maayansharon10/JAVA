package filesprocessing;

import filesprocessing.filters.Filter;
import filesprocessing.orders.Order;

/**
 * section object with holds order object, filter object and warning
 */
public class Section {
    // ------------------- data members -------------------
    /**
     * order object
     */
    private Order order;
    /**
     * filter object
     */
    private Filter filter;
    /**
     * boolean if filter has warning
     */
    private boolean shouldPrintFilterWarning = false;
    /**
     * boolean if order has warning
     */
    private boolean shouldPrintOrderWarning = false;
    /**
     * the number of line of the filter warning
     */
    private int filterWarning;
    /**
     * the number of line of the filter warning
     */
    private int orderWarning;
    // ------------------- constructors -------------------


    // ------------------- methods ------------------------

    /**
     * get filter
     * @returnn filter object
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * set filter
     * @param filter filter object to set
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * get order
     * @return order object
     */
    public Order getOrder() {
        return order;
    }

    /**
     * set order
     * @param order order object to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * return filter Warning To Print
     * @return Filter Warning To Print
     */
    public String getFilterWarningToPrint() {
        return "Warning in line " + this.filterWarning;
    }

    /**
     * set filter warning of problematic line
     * @param warning int
     */
    public void setFilterWarning(int warning) {
        setShouldPrintFilterWarning(true);
        this.filterWarning = warning;
    }

    /**
     * return true if should print filter warning, false otherwise
     * @return true if should print filter warning, false otherwise
     */
    public boolean getShouldPrintFilterWarning() {
        return this.shouldPrintFilterWarning ;
    }

    /**
     * set boolean should print filter warning
     * @param shouldPrintFilterWarning given bool
     */
    public void setShouldPrintFilterWarning(boolean shouldPrintFilterWarning) {
        this.shouldPrintFilterWarning = shouldPrintFilterWarning;
    }

    /**
     * set order warning
     * @param orderWarning number of problematic line
     */
    public void setOrderWarning(int orderWarning) {
        setShouldPrintOrderWarning(true);
        this.orderWarning = orderWarning;
    }

    /**
     * return Order Warning To Print
     * @return Order Warning To Print
     */
    public String getOrderWarningToPrint() {
        return "Warning in line " + orderWarning;
    }


    /**
     * boolean Should Print Order Warning
     * @param shouldPrintOrderWarning boolean
     */
    public void setShouldPrintOrderWarning(boolean shouldPrintOrderWarning) {
        this.shouldPrintOrderWarning = shouldPrintOrderWarning;
    }

    /**
     * get Should Print Order Warning
     * @return boolean ShouldPrintOrderWarning
     */
    public boolean getShouldPrintOrderWarning() {
        return this.shouldPrintOrderWarning ;
    }

}
