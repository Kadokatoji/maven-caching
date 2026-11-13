# maven-caching-demo

Project mẫu cho **Bài 6: CI/CD Pipeline Optimization & Caching** (GitHub Actions + Maven).

## Cấu trúc

```
maven-caching-demo/
├── pom.xml
├── src/main/java/com/example/App.java
├── src/test/java/com/example/AppTest.java
├── .github/workflows/ci.yml        <- workflow đang dùng (ban đầu là bản chưa cache)
└── docs/
    ├── ci-before-cache.yml         <- workflow KHÔNG có cache (mốc "trước")
    ├── ci-with-cache.yml           <- workflow CÓ cache: 'maven'
    └── bao-cao-bai-6.md            <- mẫu báo cáo
```

## Chạy thử ở máy local

```bash
mvn -B package
java -cp target/maven-caching-demo-1.0.0.jar:$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout) com.example.App alice
```

## Các bước làm bài

1. Tạo repository trên GitHub, đẩy project lên nhánh `main`
   (nếu nhánh của bạn là `master`, sửa `main` -> `master` trong workflow).
2. **Mốc trước caching**: `.github/workflows/ci.yml` ban đầu đã là bản chưa cache (= `docs/ci-before-cache.yml`),
   commit và push. Vào tab **Actions**, ghi lại thời gian chạy.
3. **Bật caching**: chép `docs/ci-with-cache.yml` đè lên `.github/workflows/ci.yml`
   (khác biệt duy nhất là dòng `cache: 'maven'`), commit và push -> **Push 1** (cache miss, cache được lưu).
4. **Push 2**: sửa nhẹ một file bất kỳ (ví dụ README, KHÔNG sửa `pom.xml`), commit và push -> **cache hit**.
5. So sánh thời gian 3 lần chạy và phân tích log theo `docs/bao-cao-bai-6.md`.

## Dấu hiệu cần tìm trong log

| Bước | Push 1 (miss) | Push 2 (hit) |
|---|---|---|
| Set up JDK 17 | `Cache not found for input keys` | `Cache restored successfully` |
| Build with Maven | Nhiều dòng `Downloading from central` | Gần như không còn |
| Post Set up JDK 17 | `Cache saved successfully` | `Cache hit occurred on the primary key ..., not saving cache` |
