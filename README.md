# Hệ Thống File Phân Tán Sử Dụng Giao Thức RMI

## Thành Viên
1. **Trịnh Vinh Tuấn Đạt** - B21DCCN031
2. **Nguyễn Hoàng Hiệp** - B21DCCN343
3. **Hoàng Anh Vũ** - B21DCCN795


## Mô Tả Đề Tài
Hệ thống cho phép phân tán và quản lý file hiệu quả thông qua giao thức RMI (Remote Method Invocation).

## Tiến Độ Công Việc

### Tuần 1
- **Chức năng:** Demo upload file đơn giản với kiến trúc Server-Client sử dụng RMI trong Java.
- **Quy trình:**
  - Mỗi file được chia thành các chunk dữ liệu và gửi đi lần lượt, với kích thước chunk cố định là **1MB/1 chunk**.
  - Kiểm tra tính toàn vẹn dữ liệu trước khi ghi.
  - Nếu dữ liệu toàn vẹn, cấp phát id cho client.
  - Client gửi yêu cầu ghi cùng id cho chunkServer, chunkServer thực hiện ghi file.

![Demo Upload File](https://github.com/user-attachments/assets/b0fec398-9cc8-4f84-ae32-447f7d6e7a7a)
