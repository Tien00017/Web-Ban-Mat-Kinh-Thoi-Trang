function showAttributes() {
    let type = document.getElementById("productType").value;
    let box = document.getElementById("attributes");

    let html = "";

    if (type === "kinhcan") {
        html = `
            <h2 class="sub-title">Điền thông số kính cận</h2>

            <div class="form-group">
                <label>Chất liệu</label>
                <input type="text" placeholder="chất liệu gọng kính">
            </div>
            <div class="form-group">
                <label>Màu sắc</label>
                <input type="text" placeholder="màu sắc gọng kính">
            </div>

            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" placeholder="thương hiệu kính">
            </div>
            
            <div class="form-group">
                <label>Xuất xứ</label>
                <input type="text" placeholder="nước sản xuất">
            </div>
                        
            <div class="form-group">
                <label>Độ cận</label>
                <input type="text" placeholder="đơn bị Diop">
            </div>
            
            <div class="form-group">
                <label>Giới tính</label>
                <select class="productType">
                    <option>Chọn giới tính</option>
                    <option>Nam</option>
                    <option>Nữ</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Kiểu dáng</label>
                <select class="productType" >
                    <option>Chọn kiểu dáng kính</option>
                    <option>Vuông</option>
                    <option>Tròn</option>
                    <option>Oval</option>
                    <option>Mắt mèo</option>
                </select>
            </div>

            <h2 class="sub-title">Kích thước chi tiết</h2>
            
            <div class="form-group">
                <label>Khối lượng</label>
                <input type="text" placeholder="đơn vị g">
            </div>

            <div class="form-group">
                <label>Chiều rộng tổng</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều rộng ống kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều cao ống kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều dài càng kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group full">
                <label>Hình minh họa thông số</label>
                <input type="file">
            </div>
        `;
    }

    if (type === "kinhmat") {
        html = `
            <h2 class="sub-title">Điền thông số kính mát</h2>

            <div class="form-group">
                <label>Chất liệu</label>
                <input type="text" placeholder="chất liệu gọng kính">
            </div>
            <div class="form-group">
                <label>Màu sắc mắt kính</label>
                <input type="text" placeholder="màu sắc mắt kính">
            </div>

            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" placeholder="thương hiệu kính">
            </div>
            
            <div class="form-group">
                <label>Xuất xứ</label>
                <input type="text" placeholder="nơi sản xuất">
            </div>
            
            <div class="form-group">
                <label>Lứa tuổi</label>
                <input type="text" placeholder="sản phẩm dành cho lứa tuổi nào?">
            </div>
            
            <div class="form-group">
                <label>Giới tính</label>
                <select class="productType">
                    <option>Chọn giới tính</option>
                    <option>Nam</option>
                    <option>Nữ</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Loại tròng kính</label>
                <select class="productType" >
                    <option value="">-- Chọn loại tròng </option>
                    <option>Polarized (Chống chói)</option>
                    <option>Photochromic (Đổi màu)</option>
                    <option>Gradient</option>
                    <option>Standard</option>
                    <option>Mirror (Gương)</option>
                </select>
            </div>
                       
            <h2 class="sub-title">Kích thước chi tiết</h2>
            
            <div class="form-group">
                <label>Khối lượng</label>
                <input type="text" placeholder="đơn vị g">
            </div>
            
            <div class="form-group">
                <label>Chiều rộng tổng</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều rộng mắt kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều cao mắt kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều dài càng kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group full">
                <label>Hình minh họa thông số</label>
                <input type="file">
            </div>
        `;
    }

    if (type === "kinhaptrong") {
        html = `
            <h2 class="sub-title">Điền thông số kính áp tròng</h2>

            <div class="form-group">
                <label>Chất liệu</label>
                <input type="text" placeholder="chất liệu mắt kính">
            </div>
            <div class="form-group">
                <label>Màu sắc</label>
                <input type="text" placeholder="màu sắc của kính">
            </div>

            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" placeholder="thương hiệu kính">
            </div>
                        
            <div class="form-group">
                <label>Xuất xứ</label>
                <input type="text" placeholder="nơi sản xuất">
            </div>
            
            <div class="form-group">
                <label>Hạn sử dụng</label>
                <input type="text" placeholder="Hạn sử dụng sau khi mở hộp">
            </div>
                  
            <div class="form-group">
                <label>Loại mắt kính</label>
                <select class="productType" id="typeGlasses"  onchange="showTypeKAT()">
                    <option>Chọn loại mắt kính</option>
                    <option value="can">Cận</option>
                    <option value="loan">Loạn</option>
                </select>
            </div>
            <!--thay đổi theo loại mắt kính-->
            <div id="typeKAT" class="add-form"></div>
                        
            <div class="form-group full">
                <label>Hình minh họa thông số</label>
                <input type="file">
            </div>
        `;
    }
    if (type === "gongkinh") {
        html =  `
            <h2 class="sub-title">Điền thông số gọng kính</h2>

            <div class="form-group">
                <label>Chất liệu</label>
                <input type="text" placeholder="chất liệu gọng kính">
            </div>
            <div class="form-group">
                <label>Màu sắc</label>
                <input type="text" placeholder="màu sắc gọng kính">
            </div>

            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" placeholder="thương hiệu gọng kính">
            </div>
            
            <div class="form-group">
                <label>Xuất xứ</label>
                <input type="text" placeholder="nơi sản xuất">
            </div>
            
            <div class="form-group">
                <label>Kiểu dáng</label>
                <select class="productType" id="productType">
                    <option>Chọn kiểu dáng gọng</option>
                    <option>Vuông</option>
                    <option>Tròn</option>
                    <option>Oval</option>
                    <option>Mắt mèo</option>
                </select>
            </div>

            <h2 class="sub-title">Kích thước chi tiết</h2>
            
            <div class="form-group">
                <label>Khối lượng</label>
                <input type="text" placeholder="đơn vị g">
            </div>

            <div class="form-group">
                <label>Chiều rộng tổng</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều rộng ống kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều cao ống kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Chiều dài càng kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group full">
                <label>Hình minh họa thông số</label>
                <input type="file">
            </div>
        `;
    }

    box.innerHTML = html;
}

function showTypeKAT() {
    let type = document.getElementById("typeGlasses").value;
    let box = document.getElementById("typeKAT");

    let html = "";

    if (type === "can") {
        html = `

            <div class="form-group">
                <label>Độ cận</label>
                <input type="text" placeholder="đơn vị diop">
            </div>
            <div class="form-group">
                <label>Đường kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

            <div class="form-group">
                <label>Độ cong</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

        `;
    }
    if (type === "loan") {
        html = `

            <div class="form-group">
                <label>Độ loạn</label>
                <input type="text" placeholder="độ loạn mắt kính">
            </div>
            <div class="form-group">
                <label>Trục loạn</label>
                <input type="text" placeholder="từ 0 độ đến 180 độ">
            </div>

            <div class="form-group">
                <label>Độ cong</label>
                <input type="text" placeholder="đơn vị mm">
            </div>
            
            <div class="form-group">
                <label>Đường kính</label>
                <input type="text" placeholder="đơn vị mm">
            </div>

        `;
    }

    box.innerHTML = html;
}