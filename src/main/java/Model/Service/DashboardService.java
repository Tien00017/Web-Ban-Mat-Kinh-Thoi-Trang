package Model.Service;

import Model.DAO.DashboardDAO;
import Model.Object.Dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class DashboardService {

    private final DashboardDAO dao = new DashboardDAO();

    public Dashboard getDashboardStats() {
        Dashboard s = new Dashboard();

        s.setRevenue(dao.getRevenueTotal());
        s.setTotalOrders(dao.getTotalOrders());
        s.setNewCustomers(dao.getNewCustomers30d());

        s.setRecentOrders(dao.getRecentOrders(8));
        s.setRecentProducts(dao.getRecentProducts(6));

        Map<String, Double> raw = dao.getRevenueLast7Days();
        Map<String, Integer> st = dao.getOrderStatusCounts();

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            String key = d.toString();
            labels.add(key);
            values.add(raw.getOrDefault(key, 0.0));
        }

        s.setRevenueLabels(labels);
        s.setRevenueValues(values);

        s.setOrderStatusLabels(new ArrayList<>(st.keySet()));
        s.setOrderStatusValues(new ArrayList<>(st.values()));

        return s;
    }
}
