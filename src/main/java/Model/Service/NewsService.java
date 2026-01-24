package Model.Service;

import Model.DAO.NewsDAO;
import Model.Object.News;

import java.util.List;

public class NewsService {

    private final NewsDAO newsDAO = new NewsDAO();

    // Trang chủ: load tin mới
    public List<News> getLatestNews() {
        return newsDAO.getLatestNews(5); // lấy 5 tin
    }

    // Search header
    public List<News> search(String keyword) {
        return newsDAO.searchByKeyword(keyword);
    }
}
