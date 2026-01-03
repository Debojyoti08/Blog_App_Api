# 📝 Blog App API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.9-green?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=for-the-badge&logo=hibernate)

A robust and scalable backend RESTful API for a blogging application. Built with **Spring Boot 3**, this API handles user management, blogging content, comments, and categorization with secure authentication.

---

## 🚀 Features

* **User Authentication & Authorization:** Secure login and registration using **Spring Security** and **JWT (JSON Web Tokens)**.
* **Role-Based Access Control:** Differentiate between Admin and standard User privileges via `user_role` mapping.
* **Post Management:** Create, update, delete, and fetch blog posts.
* **Categorization:** Organize posts into specific categories.
* **Comment System:** Users can comment on posts; comments are mapped to specific users and posts.
* **Data Validation:** Integrated server-side validation using Hibernate Validator.
* **API Documentation:** Interactive API documentation via **Swagger UI / OpenAPI**.
* **DTO Mapping:** Clean data transfer using **ModelMapper**.

---

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.5.9
* **Database:** MySQL
* **ORM:** Spring Data JPA (Hibernate)
* **Security:** Spring Security + JWT (jjwt-api 0.11.5)
* **Build Tool:** Maven
* **Tools:** Lombok, ModelMapper, Spring DevTools

---

## 🗄️ Database Schema

The application uses a relational database named **`blog_app_api`**.

**Core Relationships:**
* **User** `1 : N` **Role** (Managed via `user_role` table)
* **User** `1 : N` **Post** (One user can create multiple posts)
* **Category** `1 : N` **Post** (One category contains multiple posts)
* **Post** `1 : N` **Comment** (One post has multiple comments)
* **User** `1 : N` **Comment** (One user can write multiple comments)

---

## ⚙️ Configuration & Installation

### 1. Prerequisites
* JDK 21 installed
* Maven installed
* MySQL Server installed and running

### 2. Clone the Repository
```bash
git clone [https://github.com/your-username/Blog_App_Api.git](https://github.com/your-username/Blog_App_Api.git)
cd Blog_App_Api
