# Video Storage Service

## Building from source

```bash
./gradlew build

docker-compose up
```

## Features

- [X] 영상 업로드 및 변환 API
- [X] 영상 파일 제공 API
- [X] 영상 상세 정보 조회 API

### API Document

- 실행후 `http://{{host}}/docs/index.html` 로 접근

### 영상 업로드 및 변환 API

#### 요청 예시
```bash
curl --location -g --request POST 'http://{{host}}/video/upload' \
--form 'file=@"/Users/san/Movies/{{파일명.mp4}}"' \
--form 'title="{{영상 제목}}"'
```

#### 응답 예시

```http request
HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 8

{"id":1}
```

### 영상 파일 제공

#### 요청 예시

```bash
curl --location -g --request GET 'http://{{host}}/path/to/{id}'
```

#### 응답 예시

```http request
HTTP/1.1 200 OK
Content-Type: video/mp4
Content-Disposition: attachment; filename=example.mp4
Content-Length: 3
Accept-Ranges: bytes
```


### 영상 상세 정보 조회 API

#### 요청 예시
```bash
curl --location -g --request GET 'http://{{host}}/video/{id}'
```

#### 응답 예시
```http request

HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 236

{
  "id": 1,
  "title": "test_title",
  "original": {
    "fileSize": 17839845,
    "videoUrl": "http://localhost:8080/path/to/sample1.mp4"
  },
  "resized": {
    "fileSize": 541779,
    "videoUrl": "http://localhost:8080/path/to/resized_sample1.mp4"
  },
  "createAt": "2023-02-14T23:17:36.367335176"
}
```
