package com.example.tea_loufu.entity;

// Import các thư viện cần thiết cho JPA
import jakarta.persistence.*;
import java.math.BigDecimal; // Sử dụng BigDecimal để xử lý số thập phân chính xác, phù hợp với giá tiền.
import java.time.LocalDateTime; // Sử dụng LocalDateTime để xử lý các thông tin liên quan đến thời gian.

@Entity // Đánh dấu lớp này là một thực thể (entity) ánh xạ tới bảng trong cơ sở dữ liệu.
@Table(name = "Product") // Tên bảng trong cơ sở dữ liệu sẽ là "Product".
public class Product {

    @Id // Đánh dấu ProductID là khóa chính (primary key) của bảng Product.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính sẽ tự động tăng giá trị (auto_increment).
    private int productID; // Khóa chính của bảng Product.

    @Column(nullable = false, length = 100) // Thuộc tính ProductName không thể null và giới hạn độ dài tối đa là 100 ký tự.
    private String productName; // Tên của sản phẩm.

    @Column(columnDefinition = "TEXT") // Cột này lưu trữ nội dung dạng TEXT cho mô tả sản phẩm.
    private String description; // Mô tả chi tiết về sản phẩm.

    @Column(nullable = false, precision = 10, scale = 2) // Thuộc tính price có độ chính xác cao với 10 số và 2 chữ số thập phân.
    private BigDecimal price; // Giá sản phẩm.

    @Column(nullable = false) // Thuộc tính stock không thể null.
    private int stock; // Số lượng tồn kho của sản phẩm.

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    // Thời điểm tạo sản phẩm được tự động thêm vào lúc sản phẩm được tạo (DEFAULT CURRENT_TIMESTAMP), không cho phép chỉnh sửa sau khi tạo.
    private LocalDateTime createdAt; // Ngày giờ sản phẩm được tạo.

    @Lob // Đánh dấu thuộc tính imageData là kiểu LOB (Large Object Binary), dùng để lưu trữ dữ liệu lớn như hình ảnh.
    @Column(name = "image_data", columnDefinition = "LONGBLOB") // Cột này lưu trữ ảnh dưới dạng LONGBLOB trong MySQL.
    private byte[] imageData; // Dữ liệu ảnh của sản phẩm (lưu dưới dạng mảng byte).

    // Phương thức getter và setter - để truy cập và thay đổi giá trị của các thuộc tính.

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Không cần setter cho createdAt vì giá trị này được tự động tạo và không thể thay đổi.

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
