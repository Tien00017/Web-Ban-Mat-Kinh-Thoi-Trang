package Model.Service;

import Model.DAO.ReviewDAO;
import Model.Object.Review;
import Model.Object.User;

import java.util.Map;

public class ReviewService {

    private final ReviewDAO reviewDAO = new ReviewDAO();

    public Map<User, Review> getReviews(int productId) {
        return reviewDAO.getAllReviewsByProductId(productId);
    }

    public int[] getReviewStats(int productId) {
        return reviewDAO.getTotalReviews(productId);
    }

    public void addReview(Review rev) {

        if (rev.getRating() < 1 || rev.getRating() > 5) {
            throw new RuntimeException("Rating không hợp lệ");
        }

        reviewDAO.addReview(rev);
    }
}
