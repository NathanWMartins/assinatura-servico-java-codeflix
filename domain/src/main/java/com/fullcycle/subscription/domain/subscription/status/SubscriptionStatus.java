package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.subscription.Subscription;

public sealed interface SubscriptionStatus permits
        AbstractSubscriptionStatus,
        CanceledSubscriptionStatus,
        IncompleteSubscriptionStatus,
        TrailingSubscriptionStatus {

    String TRAILING = "trailing";
    String INCOMPLETE = "incomplete";
    String ACTIVE = "active";
    String CANCELED = "canceled";

    void trailing();

    void incomplete();

    void active();

    void cancel();

    default String value() {
        if (this instanceof ActiveSubscriptionStatus) {
            return ACTIVE;
        } else if (this instanceof CanceledSubscriptionStatus) {
            return CANCELED;
        } else if (this instanceof IncompleteSubscriptionStatus) {
            return INCOMPLETE;
        } else if (this instanceof TrailingSubscriptionStatus) {
            return TRAILING;
        } else {
            throw new IllegalStateException("Unexpected subscription status: " + this);
        }
    }


    static SubscriptionStatus create(final String status, final Subscription aSubscription) {
        if (aSubscription == null) {
            throw DomainException.with("'subscription' should not be null");
        }

        if (status == null) {
            throw DomainException.with("'status' should not be null");
        }

        switch (status) {
            case ACTIVE:
                return new ActiveSubscriptionStatus(aSubscription);
            case CANCELED:
                return new CanceledSubscriptionStatus(aSubscription);
            case INCOMPLETE:
                return new IncompleteSubscriptionStatus(aSubscription);
            case TRAILING:
                return new TrailingSubscriptionStatus(aSubscription);
            default:
                throw DomainException.with(
                        String.format("Invalid status: %s", status)
                );
        }
    }
}