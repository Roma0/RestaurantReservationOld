package com.ascending.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RestaurantDaoTest.class,
        UserDaoTest.class,
        ReservationDaoTest.class,
        ReviewDaoTest.class
})

public class TestAll {
}
