<%--
  Created by IntelliJ IDEA.
  User: 김승우
  Date: 2024-06-24
  Time: 오후 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>검사 세부 정보</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .header {
            background-color: #f8f8f8;
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        .container {
            display: flex;
            padding: 20px;
        }
        .sidebar {
            width: 200px;
            border-right: 1px solid #ddd;
            padding-right: 20px;
        }
        .sidebar img {
            width: 100%;
            height: auto;
            margin-bottom: 10px;
        }
        .content {
            flex: 1;
            padding-left: 20px;
        }
        .content img {
            width: 100%;
            height: auto;
        }
        .info {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>PACSPLUS 검사 세부 정보</h1>
</div>
<div class="container">
    <div class="sidebar">
        <h2>썸네일</h2>
        <!-- Thumbnails -->
        <div class="thumbnail">
            <img src="path/to/thumbnail1.png" alt="Thumbnail 1">
            <p>Series Description : Bone</p>
        </div>
        <div class="thumbnail">
            <img src="path/to/thumbnail2.png" alt="Thumbnail 2">
            <p>Series Description : Normal</p>
        </div>
        <!-- Add more thumbnails as needed -->
    </div>
    <div class="content">
        <div class="info">
            <h2>환자 정보</h2>
            <p>이름: HONG GIL DONG</p>
            <p>생년월일: 1947-11-16</p>
            <p>검사일: 2023-04-04</p>
            <p>검사시간: 10:14:17</p>
            <!-- Add more patient info as needed -->
        </div>
        <div class="images">
            <h2>검사 이미지</h2>
            <!-- Images -->
            <img src="path/to/image1.png" alt="Image 1">
            <img src="path/to/image2.png" alt="Image 2">
            <!-- Add more images as needed -->
        </div>
    </div>
</div>
</body>
</html>
