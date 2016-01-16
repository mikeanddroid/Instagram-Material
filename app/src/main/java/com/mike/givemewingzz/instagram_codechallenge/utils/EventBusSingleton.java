package com.mike.givemewingzz.instagram_codechallenge.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by GiveMeWingzz on 1/7/2016.
 */
public class EventBusSingleton {

    // Our singleton event bus.
    private static Bus bestBus = new Bus(ThreadEnforcer.MAIN);

    private EventBusSingleton() {
    }

    public static Bus getBus() {
        return bestBus;
    }

    public static void register(Object object) {
        bestBus.register(object);
    }

    public static void unregister(Object object) {
        bestBus.unregister(object);
    }

    public static void post(Object event) {
        bestBus.post(event);
    }

}
