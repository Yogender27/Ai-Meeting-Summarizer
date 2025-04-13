# 🧠 AI-Powered Smart Meeting Summarizer – Backend

This is the **backend service** for the AI-Powered Smart Meeting Summarizer app. It is a Spring Boot application that enables users to:

- Authenticate using JWT
- Create, manage, and delete Zoom meetings
- Automatically store meeting information
- (Coming soon) Transcribe and summarize meeting recordings using AI (Whisper + NLP)

---

## 🔧 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **MySQL**
- **Spring Security (JWT-based)**
- **REST APIs**
- **Zoom API (for meeting integration)**
- **Maven**

---


---

## 🔐 Authentication & Authorization

- All APIs are secured with **JWT**
- On login/signup, the backend returns a token
- Include the token as `Authorization: Bearer <token>` in request headers

---

## 🧑‍💻 APIs Overview

### ✅ Auth APIs

| Method | Endpoint              | Description       |
|--------|-----------------------|-------------------|
| POST   | `/api/auth/signup`    | Register user     |
| POST   | `/api/auth/login`     | Login and get JWT |

---

### 📅 Meeting APIs

| Method | Endpoint                | Description               |
|--------|-------------------------|---------------------------|
| POST   | `/api/meetings/create`  | Create new meeting        |
| GET    | `/api/meetings/all`     | Fetch all meetings        |
| PUT    | `/api/meetings/{id}`    | Update a meeting          |
| DELETE | `/api/meetings/{id}`    | Delete a meeting          |

📌 Each meeting is stored with the authenticated user’s `userId`.

---

## 🔐 Security

- Passwords are hashed using BCrypt
- Only logged-in users can manage their meetings
- Unauthorized access is blocked at controller and service levels

---

## ⚙️ How to Run

1. Clone the repo  
2. Create a MySql database (local or cloud)  
3. Set up `application.properties`





