package com.ldc.spring.event.practice;

import com.ldc.spring.core.enums.EventStatus;
import com.ldc.spring.core.enums.ProductType;
import com.ldc.spring.model.Customer;
import org.springframework.context.ApplicationEvent;

/**
 * created by liudacheng on 2018/9/7.
 */
public class CreditSuccessBizEvent extends ApplicationEvent{

    private Customer customer;
    private ProductType productType;
    private EventStatus eventStatus;


    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CreditSuccessBizEvent(Object source) {
        super(source);
    }

    public CreditSuccessBizEvent(Object source, Customer customer, ProductType productType, EventStatus eventStatus) {
        super(source);
        this.customer = customer;
        this.productType = productType;
        this.eventStatus = eventStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}
