# Grocery Bazaar

### Android Grocery Browsing Application

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-DD2C00?logo=firebase&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)

**GitHub Repository:** https://github.com/arenihal/GroceryBazar

## Overview

Grocery Bazaar is an Android grocery browsing application developed using Java and XML in Android Studio.

It allows users to register, authenticate, explore grocery categories, and browse products with images and prices.

## Features

- User registration and login
- Firebase Email/Password Authentication
- Forgot password functionality
- Login attempt protection
- Six grocery categories
- 28 grocery products with images and prices
- User logout

## Grocery Categories

1. Fruits
2. Vegetables
3. Breads
4. Beverages
5. Milk
6. Snacks

## Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| User Interface | XML |
| IDE | Android Studio |
| Authentication | Firebase Authentication |
| Product Display | RecyclerView |
| Build System | Gradle |
| Version Control | Git and GitHub |

## Important Java Files

| File | Purpose |
|---|---|
| MainActivity.java | User login and password reset |
| RegisterActivity.java | User registration |
| HomeActivity.java | Grocery category dashboard |
| ProductListActivity.java | Displays grocery products |
| Item.java | Product data model |
| ItemAdapter.java | RecyclerView product adapter |

## Application Workflow

```text
Register / Login
       |
       v
Home Dashboard
       |
       v
Select Grocery Category
       |
       v
View Products and Prices
       |
       v
Logout
```

## Installation

1. Download the signed APK from an authorized release source.
2. Install it on a compatible Android device.
3. Register or log in.
4. Explore grocery categories and products.

## Security

- Firebase Authentication handles user sign-in.
- Login attempt protection is included.
- Firebase configuration and signing credentials are not included in this repository.
- Never commit private credentials to GitHub.

## Application Version

**Version 1.1**

## Developer

**ARE NIHAL**

## Project Status

Android grocery browsing application.

---

Developed using Java, XML, Android Studio, and Firebase.
