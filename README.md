# Web-Ban-Mat-Kinh-Thoi-Trang
Mọi người thống nhất theo cấu trúc này nha :
1) Folder "HTML": Chứa file HTML
2) Folder "Style": Chứa CSS
3) Folder JavaScript: Chứa file js
4) ImagesBan-Mat-Kinh-Thoi-Trang
Mọi người thống nhất theo cấu trúc này nha :
1) Folder "HTML": Chứa file HTML
2) Folder "Style": Chứa CSS
3) Folder JavaScript: Chứa file js
4) Folder Images: Chứa các folder dùng cho từng file HTML( Ví dụ: có 2 ảnh là a và b cho HomePage.html thì tại folder Images/ImageForHomePage để chứa a và b)
Nếu mọi người có và muốn tạo thêm folder gì thì nhớ ghi chú thích giống vầy để mn tuân theo cấu trúc mà làm  

# Tái cấu trúc
1) Toàn bộ folder css, img, js đều nằm ở trong src/webapp
2) Sẽ phải chuyển toàn bộ HTML sang JSP và được đặt trong src/webapp/WEB-INF (Hiện tại mới chuyển vài file và chưa chỉnh hết mn làm đến đâu chỉnh đến đấy nha chứ 1 người chuyển 1 lần hết làm ko nổi)
3) Folder HTML ở ngoài cứ giữ nguyên đừng xóa (để mốt có gì trục trặc thì còn có nó để xem lại)
4) src/Controller: nơi chứa servlet (chỉ chứa servlet ko chứa gì khác)
5) src/Model: nơi chứa DAO, model của từng đối tượng (sẽ phải có folder riêng cho 2 cái này)
6) src/resource: nơi chứa các file cấu hình ví dụ: db.properties (hiện chưa tạo)
   Lưu ý: các vấn đề về cài tomcat, sevlet,... là tự mình cài trước khi mở project lên chứ ko là nó lỗi rồi là tự sửa nha

# Chèn dữ liệu từ file csv vào thẳng my sql (tạm thời để test dữ liệu)
1) chạy web.sql
2) chạy staging.sql
3) vào cmd admin
4) Gõ lần lượt các câu
   - cd "C:\Program Files\MySQL\MySQL Server 8.0\bin"
   - mysql -u root -p --local-infile=1
   - Nhập mật khẩu root
   - USE web;
   - LOAD DATA LOCAL INFILE 'Nơi chứa file csv'
     INTO TABLE product_csv_staging
     CHARACTER SET utf8mb4
     FIELDS
     TERMINATED BY ','
     ENCLOSED BY '"'
     LINES
     TERMINATED BY '\n'
     IGNORE 1 LINES;
   - SELECT COUNT(*) FROM product_csv_staging; (kiểm tra đã chèn vào staging chưa)
5) Chạy transform.sql 
6) Kiểm tra lại bằng câu lệnh select * form products ở sql script khác
* Lưu ý: Cách này hiện tại chỉ hỗ trợ chạy 3 kính là kính cận, kính mát, gọng kính ko có kính áp tròng vì khác category