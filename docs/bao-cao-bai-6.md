# Bài 6: CI/CD Pipeline Optimization & Caching

## 1. Mục tiêu
Cấu hình dependency caching cho Maven trong GitHub Actions để giảm thời gian tải dependency và rút ngắn thời gian chạy CI.

## 2. Cấu hình
Thêm tham số `cache: 'maven'` vào action `actions/setup-java@v4`:

```yaml
- name: Set up JDK 17
  uses: actions/setup-java@v4
  with:
    distribution: 'temurin'
    java-version: '17'
    cache: 'maven'
```

## 3. Kết quả so sánh thời gian

| Lần chạy | Trạng thái cache | Thời gian thực thi |
|---|---|---|
| Trước khi thêm caching | Không có | ... |
| Push lần 1 (sau khi thêm caching) | Cache miss, lưu cache | ... |
| Push lần 2 | Cache hit | ... |

(Điền số liệu thực tế từ tab Actions.)

## 4. Phân tích log

### Push lần 1 (cache miss)
- Bước `Set up JDK 17`: `Cache not found for input keys: setup-java-Linux-x64-maven-...`
- Bước `Build with Maven`: nhiều dòng `Downloading from central: https://repo.maven.apache.org/...`
- Bước `Post Set up JDK 17`: `Cache saved successfully`

> Chèn ảnh chụp màn hình tại đây.

### Push lần 2 (cache hit)
- Bước `Set up JDK 17`: `Cache hit for: setup-java-Linux-x64-maven-...` và `Cache restored successfully`
- Bước `Build with Maven`: gần như không còn dòng `Downloading from central`
- Bước `Post Set up JDK 17`: `Cache hit occurred on the primary key ..., not saving cache`

> Chèn ảnh chụp màn hình tại đây.

## 5. Kết luận
Dependency caching giúp các dependency được khôi phục từ cache thay vì tải lại từ Maven Central ở mỗi lần chạy, từ đó giảm thời gian thực thi workflow, tiết kiệm băng thông và tăng tốc chu kỳ phát triển.
