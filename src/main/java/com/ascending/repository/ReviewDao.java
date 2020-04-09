package com.ascending.repository;

import com.ascending.model.Restaurant;
import com.ascending.model.Review;
import java.util.List;

public interface ReviewDao {
    Review save(Review review);
    boolean deleteById(Long id);
    List<Review> getReviews();
    Review getReviewById(Long id);
    List<Review> getReviewsByRestaurantId(Long id);
}
