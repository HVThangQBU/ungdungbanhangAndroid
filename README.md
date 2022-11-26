# ungdungbanhangAndroid
<h1 align='center'>
  ungdungbanhangAndroid
</h1>
## Phân tích chức năng của từng đối tượng
 - Khách hàng:
   + Trang chủ
   + Xem sản phẩm theo danh mục
   + Đăng ký tài khoản
   + Đăng nhập tài khoản
   + Giỏ hàng
   + Xem sản phẩm đã mua 
   + Tìm kiếm sản phẩm
   + Xem, sửa thông tin khách hàng
   + Đặt hàng
 - Admin: là thành viên quản trị của hệ thống, có các quyền và chức năng như: 
   + Thêm sản phẩm
   + Sửa sản phẩm
   + Xóa sản phẩm
## Một số chức năng của đối tượng khách hàng
### Xem sản phẩm theo danh mục
 - Description: Cho phép xem các danh mục của sản phẩm.
 - Input: Chọn danh mục.
 - Process: Lấy thông tin sản phẩm từ CSDL thông qua tên danh mục.
 - Output: Hiện thị thông tin sản phẩm.
### Đăng ký tài khoản
 - Desription: Dành cho khách hàng đăng ký để có thêm nhiều chức năng cho việc mua bán sản phẩm, 
 - Input: Khách hang phải đăng nhập đầy đủ các thông tin (*: là thông tin bắt buộc)
            First Name: (*)
            Last name: (*)
            Phone: (*)
            Email: nhập email (*)
            Password (*)
 - Output: Đưa ra thông báo đăng ký thành công hoặc yêu cầu nhập lại nếu thông tin không hợp lệ
### Đăng nhập
 - Description: Cho khách hàng login vào hệ thống.
 - Input: Người dùng nhập vào các thông tin về username, password để login.
 - Process: Kiểm tra username và password của người dùng nhập vào và so  
     sánh  với username và password trong CSDL.
 - Output: nếu đúng cho đăng nhập và hiển thị các chức năng, ngược lại hiển thị thông báo yêu cầu nhập lại nếu thông tin  không chính xác. 
### Giỏ hàng
 - Description: Cho phép khách hàng mua hàng 
 - Input: Chọn sản phẩm khách hàng muốn mua
 - Process: : Cập nhật thông tin sản phẩm được chọn trong CSDL 
 - Output: Hiển thị thông báo đặt hàng thành công.
### Xem sản phẩm đã mua
 - Description: Cho phép khách hàng xem giỏ hàng của mình.
 - Input: Click chọn giỏ hàng trên menu.
 - Process: Gọi trang hiển thị thông tin giỏ hàng đã được lập của khách hàng đó
 - Output: Hiển thị thông tin chi tiết
## Chức năng của đối tượng Admin
### Thêm sản phẩm
 - Description: Giúp Admin có thể thêm sản phẩm mới.
 - Input: Admin nhập vào những thông tin cần thiết của sản phẩm mới.
 - Process: Kiểm tra xem những trường nào không được để trống. Nếu tất cả đều phù hợp thì thêm vào database. Ngược lại thì  không thêm vào database.
 - Output: Load lại danh sách sản phẩm để xem sản phẩm mới đã được thêm vào CSDL.
### Sửa sản phẩm
 - Description: Giúp Admin có thể xoá sản phẩm
 - Input: Chọn sản phẩm cần xoá
 - Process: Xoá trong CSDL
 - Output: Load lại danh sách sản phẩm
### Xóa sản phẩm
 - Description: Giúp Admin có thể xóa sản phẩm ra khỏi CSDL.
 - Input: Chọn sản phẩm cần xóa.
 - Process: Lấy các thông tin của sản phẩm và hiển thị ra màn hình để chắc chắn rằng Admin xóa đúng sản phẩm cần thiết.
 - Output: Load lại danh sách sản phẩm để biết được đã xoá thành công Sản phẩm  ra khỏi CSDL

![image](https://user-images.githubusercontent.com/57425057/204078387-d8fba56e-10db-4371-b5ca-94250f07b4fd.png)

![image](https://user-images.githubusercontent.com/57425057/204078392-152a0627-46ee-48be-be8e-b7fc59a07836.png)
![image](https://user-images.githubusercontent.com/57425057/204078394-40aa9473-088a-436b-a718-aa96eea97a8e.png)
![image](https://user-images.githubusercontent.com/57425057/204078402-394057f3-0371-4c44-beb1-137964b0e97d.png)
![image](https://user-images.githubusercontent.com/57425057/204078404-a9831347-65db-4456-af32-3e52d5746c16.png)
![image](https://user-images.githubusercontent.com/57425057/204078416-2fb4c227-f8d2-46e3-80ed-9d706cd6f9f9.png)
![image](https://user-images.githubusercontent.com/57425057/204078417-74f01280-4a06-4b51-9f18-ee7f15fb31c7.png)
![image](https://user-images.githubusercontent.com/57425057/204078419-413f948e-b540-4303-8bb9-7fd16537e322.png)
![image](https://user-images.githubusercontent.com/57425057/204078422-2f7279b1-59ea-4ae4-af4a-694a57ae10ac.png)

![image](https://user-images.githubusercontent.com/57425057/204078425-a7c42ff2-e646-46f1-a014-e507788dffb7.png)
![image](https://user-images.githubusercontent.com/57425057/204078429-9d3871b6-b979-41eb-a9ee-c097893b6922.png)
![image](https://user-images.githubusercontent.com/57425057/204078434-e26a8b7e-be9c-4fc5-ac80-01b065cf32e9.png)
![image](https://user-images.githubusercontent.com/57425057/204078438-8940c72d-0954-427e-a4c6-a76637e6708c.png)
![image](https://user-images.githubusercontent.com/57425057/204078439-a4051ba8-84bf-455b-88b5-b1bb4ef569e1.png)











 
