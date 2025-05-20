""

# 📚 健康打卡系统接口文档

> 项目名称：**基于安卓的个人健康打卡系统（后端）**
>
> 技术栈：Spring Boot + MyBatis + JWT + MySQL + 阿里云OSS
>
> 接口风格：RESTful JSON API

---

## 🔐 用户模块 `/user`

### 1. 用户注册

- **URL**: `/user/register`
- **方法**: `POST`
- **请求体**:

```json
{
  "username": "string (5~16位非空字符)",
  "password": "string (5~16位非空字符)"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

- **错误码**:
    - `用户名已注册`: 用户名重复
    - `注册失败`: 插入数据库失败

---

### 2. 用户登录

- **URL**: `/user/login`
- **方法**: `POST`
- **请求体**:

```json
{
  "username": "string (5~16位非空字符)",
  "password": "string (5~16位非空字符)"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": "JWT Token"
}
```

- **错误码**:
    - `用户不存在`: 用户名错误
    - `密码错误`: 密码不匹配

---

### 3. 获取当前用户信息

- **URL**: `/user/userInfo`
- **方法**: `GET`
- **Header**:

```http
Authorization: Bearer <token>
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": {
    "userId": 1,
    "username": "testuser",
    "nickname": "昵称",
    "gender": "男",
    "avatarUrl": "https://example.com/avatar.jpg",
    "phone": "12345678901",
    "email": "test@example.com",
    "status": 1,
    "createTime": "2024-04-01T12:00:00",
    "lastLoginTime": "2024-04-02T12:00:00"
  }
}
```

---

### 4. 更新用户信息

- **URL**: `/user/update`
- **方法**: `PUT`
- **Header**:

```http
Content-Type: application/json
Authorization: Bearer <token>
```

- **请求体**:

```json
{
  "userId": 1,
  "nickname": "新昵称",
  "gender": "女",
  "avatarUrl": "https://example.com/new_avatar.jpg",
  "phone": "12345678902",
  "email": "new@example.com"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

---

### 5. 修改密码

- **URL**: `/user/updatePassword`
- **方法**: `PATCH`
- **Header**:

```http
Content-Type: application/json
Authorization: Bearer <token>
```

- **请求体**:

```json
{
  "oldPassword": "旧密码",
  "newPassword": "新密码 (5~16位)"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

- **错误码**:
    - `原密码错误`: 旧密码不匹配

---

### 6. 修改头像

- **URL**: `/user/updateAvatar`
- **方法**: `PATCH`
- **Header**:

```http
Authorization: Bearer <token>
```

- **请求参数**:

```http
?avatarUrl=https://example.com/new_avatar.jpg
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

---

### 7. 删除用户（管理员）

- **URL**: `/user/DeleteUserByName`
- **方法**: `POST`
- **请求参数**:

```http
?username=admin
```

- **响应**:

```json
{
  "code": 1,
  "message": "用户已删除",
  "data": null
}
```

- **错误码**:
    - `不存在此用户！`
    - `删除失败`

---

## 📋 健康记录模块 `/healthRecord`

### 1. 获取所有健康记录

- **URL**: `/healthRecord`
- **方法**: `GET`
- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": [
    {
      "recordId": 1,
      "userId": 1,
      "temperature": 36.5,
      "symptoms": ["头痛", "咳嗽"],
      "remark": "无特别不适",
      "recordTime": "2024-04-01T09:00:00",
      "syncStatus": 1
    }
  ]
}
```

---

### 2. 分页获取健康记录

- **URL**: `/healthRecord/list`
- **方法**: `GET`
- **请求参数**:

```http
?pageNum=1&pageSize=10&userId=1
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": {
    "total": 100,
    "items": [/* 同上 */]
  }
}
```

---

### 3. 新增健康记录

- **URL**: `/healthRecord`
- **方法**: `POST`
- **Header**:

```http
Content-Type: application/json
Authorization: Bearer <token>
```

- **请求体**:

```json
{
  "userId": 1,
  "temperature": 37.2,
  "symptoms": ["乏力"],
  "remark": "有点发烧"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

---

### 4. 修改健康记录

- **URL**: `/healthRecord`
- **方法**: `PUT`
- **Header**:

```http
Content-Type: application/json
Authorization: Bearer <token>
```

- **请求体**:

```json
{
  "recordId": 1,
  "temperature": 36.8,
  "symptoms": ["咳嗽"],
  "remark": "好转中"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

---

### 5. 删除健康记录

- **URL**: `/healthRecord`
- **方法**: `DELETE`
- **Header**:

```http
Content-Type: application/json
Authorization: Bearer <token>
```

- **请求体**:

```json
{
  "recordId": 1
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

---

## 🔐 管理员模块 `/admin`

### 1. 管理员注册

- **URL**: `/admin/register`
- **方法**: `POST`
- **请求体**:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": null
}
```

---

### 2. 管理员登录

- **URL**: `/admin/login`
- **方法**: `POST`
- **请求体**:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": "JWT Token"
}
```

---

### 3. 获取管理员信息

- **URL**: `/admin/adminInfo`
- **方法**: `GET`
- **Header**:

```http
Authorization: Bearer <token>
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": {
    "adminId": 1,
    "username": "admin",
    "password": "加密后的密码",
    "createTime": "2024-04-01T12:00:00",
    "lastLoginTime": "2024-04-02T12:00:00"
  }
}
```

---

### 4. 分页查询操作日志

- **URL**: `/admin`
- **方法**: `GET`
- **请求参数**:

```http
?pageNum=1&pageSize=10&adminId=1
```

- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": {
    "total": 50,
    "items": [
      {
        "logId": 1,
        "adminId": 1,
        "actionType": "用户登录",
        "description": "用户ID: 1",
        "timestamp": "2024-04-02T12:00:00"
      }
    ]
  }
}
```

---

## 📤 文件上传模块 `/upload`

### 1. 文件上传

- **URL**: `/upload`
- **方法**: `POST`
- **Header**:

```http
Content-Type: multipart/form-data
Authorization: Bearer <token>
```

- **请求体**: `file` 字段上传文件
- **响应**:

```json
{
  "code": 1,
  "message": "操作成功",
  "data": "https://oss.example.com/uploaded/file.jpg"
}
```

---

## ✅ 返回结构统一格式

所有接口均遵循如下返回格式：

```json
{
  "code": 1,           // 状态码：1 成功，0 失败
  "message": "操作成功", // 提示信息
  "data": {}            // 数据内容（可为对象、数组、字符串等）
}
```

---

## 📦 附录：实体类字段参考

### [User.java](file://E:\Documents\Project\Android Health Check-In System\Back-end_HealthCheckInSystem\src\main\java\edu\vojago\backend_healthcheckinsystem\pojo\User.java)

| 字段名           | 类型            | 说明         |
|---------------|---------------|------------|
| userId        | Integer       | 用户唯一ID     |
| username      | String        | 用户名        |
| password      | String        | 密码（MD5加密）  |
| nickname      | String        | 昵称         |
| gender        | String        | 性别         |
| avatarUrl     | String        | 头像地址       |
| phone         | String        | 手机号        |
| email         | String        | 邮箱         |
| status        | Integer       | 状态（0禁用1正常） |
| createTime    | LocalDateTime | 注册时间       |
| lastLoginTime | LocalDateTime | 最近登录时间     |

### [HealthRecord.java](file://E:\Documents\Project\Android Health Check-In System\Back-end_HealthCheckInSystem\src\main\java\edu\vojago\backend_healthcheckinsystem\pojo\HealthRecord.java)

| 字段名         | 类型            | 说明      |
|-------------|---------------|---------|
| recordId    | Integer       | 记录ID    |
| userId      | Integer       | 关联用户ID  |
| temperature | Double        | 体温（单位℃） |
| symptoms    | List<String>  | 症状列表    |
| remark      | String        | 备注      |
| recordTime  | LocalDateTime | 记录时间    |
| syncStatus  | Integer       | 同步状态    |