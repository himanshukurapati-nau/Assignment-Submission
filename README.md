
<p align="center">
  <img src="https://img.icons8.com/external-tal-revivo-regular-tal-revivo/96/external-readme-is-a-easy-to-build-a-developer-hub-that-adapts-to-the-user-logo-regular-tal-revivo.png" width="20%" alt="ASSIGNMENT-SUBMISSION.GIT-logo">
</p>
<p align="center">
    <h1 align="center">ASSIGNMENT-SUBMISSION.GIT</h1>
</p>
<p align="center">
    <em><code>❯ This repository contains an Android application designed for managing and submitting academic assignments. It consists of two separate apps: one for students and one for faculty.</code></em>
</p>
<p align="center">
	<!-- Shields.io badges disabled, using skill icons. --></p>
<p align="center">
		<em>Built with the tools and technologies:</em>
</p>
<p align="center">
	<a href="https://skillicons.dev">
		<img src="https://skillicons.dev/icons?i=firebase,github,gradle,java,androidstudio,md&theme=light">
	</a></p>

<br>

##### 🔗 Table of Contents

- [📍 Overview](#-overview)
- [👾 Features](#-features)
- [📂 Repository Structure](#-repository-structure)
- [🧩 Modules](#-modules)
- [🚀 Getting Started](#-getting-started)
    - [🔖 Prerequisites](#-prerequisites)
    - [📦 Installation](#-installation)
    - [🤖 Usage](#-usage)
- [🤝 Contributing](#-contributing)
- [🎗 License](#-license)
- [🙌 Acknowledgments](#-acknowledgments)

---

## 📍 Overview

The Assignment Submission Android Application is designed to streamline the process of managing and submitting academic assignments. It provides two separate applications: one for students and one for faculty members. 
### Student App: 
The student app allows users to log in using their class and section details, view assignments assigned to them, and submit their completed work in PDF format. Each assignment displays important information like the due date and description. The app also offers a notification system that alerts students when a new assignment is created or updated by their faculty. This ensures that students are always up-to-date on upcoming assignments. 
### Faculty App: 
The faculty app is designed for teachers to efficiently create, assign, and manage assignments. After logging in with their faculty credentials, they can create new assignments, provide descriptions, set due dates, and assign them to specific classes and sections. Faculty can also edit or delete assignments as needed. Additionally, the app automatically notifies students in the corresponding class and section when a new assignment is available. The application aims to reduce the overhead of paper-based assignment submissions and simplify the communication between students and faculty. With features like push notifications, class and section-based assignment allocation, and file uploads, this system offers a convenient solution for both parties to manage assignments efficiently.
### Key Benefits:  
-  **Efficient Assignment Management:** Faculty can easily create and assign tasks, while students can view and submit them in one place. 
-  **Automated Notifications:** No more manual reminders. Students are automatically notified about new assignments and upcoming deadlines. 
-  **Class and Section Filtering:** Assignments are tailored to specific classes and sections, ensuring only relevant tasks are displayed. 
-  **Digital Submission:** Students can upload assignments digitally, reducing the need for physical copies and improving organization. This project is aimed at educational institutions seeking to modernize their assignment submission process, enhance communication, and promote efficiency in academic task management.

This project is aimed at educational institutions seeking to modernize their assignment submission process, enhance communication, and promote efficiency in academic task management.

---

## 👾 Features

### Student App  
-  **Login:** Students can log in to the app based on their class and section. 
-  **View Assignments:** Displays a list of assignments with due dates for the logged-in student's class and section. 
-  **Upload Assignment:** Students can upload assignment PDFs directly through the app. 
-  **Notifications:** Students receive notifications when new assignments are created by their faculty. 
### Faculty App  
-  **Login:** Faculty members can log in to access their dashboard. 
-  **Create Assignment:** Faculty can create new assignments, adding a title, description, due date, and assign it to specific classes and sections. 
-  **Manage Assignments:** Faculty can view, edit, or delete assignments they have created. 
-  **Notifications:** Students receive notifications upon assignment creation.</code>

---

## 📂 Repository Structure

```sh
└── Assignment-Submission.git/
    ├── AssignmentCreationFaculty
    │   ├── .gitignore
    │   ├── .idea
    │   ├── APK
    │   ├── app
    │   ├── build.gradle
    │   ├── gradle
    │   ├── gradle.properties
    │   ├── gradlew
    │   ├── gradlew.bat
    │   └── settings.gradle
    └── AssignmentSubmission
        ├── .gitignore
        ├── .idea
        ├── APK
        ├── app
        ├── build.gradle
        ├── gradle
        ├── gradle.properties
        ├── gradlew
        ├── gradlew.bat
        └── settings.gradle
```

---

## 🧩 Modules

<details closed><summary>AssignmentCreationFaculty</summary>

| File | Summary |
| --- | --- |
| [FirstActivity.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/FirstActivity.java) | <code>❯ This activity has options to login or signup</code> |
| [SubmittedAssignments.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/SubmittedAssignments.java) | <code>❯ Displays all the submitted assignments</code> |
| [NewAssignmentCreation.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/NewAssignmentCreation.java) | <code>❯ This activity has options to create new assignment</code> |
| [MainActivity.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/MainActivity.java) | <code>❯ Main page which is loaded when app opens</code> |
| [RegisterActivity.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/RegisterActivity.java) | <code>❯ This activity is used to sign up</code> |
| [LoginActivity.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/LoginActivity.java) | <code>❯ This activity is used to login</code> |
| [PDFViewerActivity.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/PDFViewerActivity.java) | <code>❯ This activity is used to display pdf</code> |
| [AssignmentsPending.java](https://github.com/himanshukurapati/Assignment-Submission.git/blob/main/AssignmentCreationFaculty/app/src/main/java/com/himanshusampath/assignmentcreationfaculty/AssignmentsPending.java) | <code>❯ This activity is used to display pending assignments</code> |

</details>

---

## 🚀 Getting Started

### 🔖 Prerequisites

- Android Studio installed on your machine 
- Minimum Android version: 5.0 (Lollipop)
- Java

### 📦 Installation

Build the project from source:

1. Clone the Assignment-Submission.git repository:
```sh
❯ git clone https://github.com/himanshukurapati/Assignment-Submission.git
```

2. Navigate to the project directory:
```sh
❯ cd Assignment-Submission.git
```

3. Install the required dependencies:
```sh
❯ mvn clean install
```

### 🤖 Usage


### Student App Instructions  
1.  **Install the App** on your Android device by downloading it from the provided APK or Play Store link. 
2.  **Log in** with your class and section credentials. 
3.  **View Assignments:** Once logged in, you will see a list of all assignments for your class and section. 
4.  **Upload Assignment:**  - Tap on an assignment to view details. - Upload your assignment by selecting the PDF file from your device. - Confirm the upload and ensure it's successful. 
5.  **Receive Notifications:** You'll automatically receive push notifications when new assignments are posted by your faculty. 
### Faculty App Instructions  
1.  **Install the App** on your Android device. 
2.  **Log in** using your faculty credentials. 
3.  **Create a New Assignment:**  
- Navigate to the "Create Assignment" section. 
- Fill in the assignment title, description, due date, and attach any relevant files if necessary. 
- Assign the assignment to a specific class and section. 
- Submit the assignment, and students will be notified. 
4.  **Manage Assignments:**  
- View all created assignments in the "Manage Assignments" section. 
- Edit or delete assignments as needed. 
5.  **Monitor Submissions:** Review assignment submissions uploaded by students based on class and section.

---

## 🤝 Contributing


We welcome contributions to improve the project, including bug fixes, feature suggestions, and code improvements. However, please note that contributors are not allowed to use the code for creating derivative projects or for any commercial purposes. By contributing to this project, you agree to abide by the following guidelines: 
### How to Contribute:  
1.  **Report Bugs:** Use the [Issues](https://github.com/himanshukurapati/Assignment-Submission/issues) tab to report bugs. 
2.  **Suggest Features:** Propose new features via the Issues tab or a pull request. 
3.  **Submit Code:** Fork the repository, create a new branch, and submit a pull request with your changes. 
### Contribution Guidelines:  
- Contributions must align with the goals of the project. 
- All code contributions must be submitted under the same license (AGPL-3.0). 
- No commercial use of the code is permitted outside this project. 

By contributing, you agree to these terms and ensure the integrity of the project.

### Contribution Steps:

1. **Fork the Repository**: Start by forking the project repository to your github account.
2. **Clone Locally**: Clone the forked repository to your local machine using a git client.
   ```sh
   git clone https://github.com/himanshukurapati/Assignment-Submission.git
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to github**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.
8. **Review**: Once your PR is reviewed and approved, it will be merged into the main branch. Congratulations on your contribution!
</details>

<details closed>

---

## 🎗 License

This project is protected under the [GNU Affero General Public License v3.0](https://choosealicense.com/licenses) License. For more details, refer to the [LICENSE](https://github.com/himanshukurapati/Assignment-Submission/blob/main/LICENSE) file.

---

## 🙌 Acknowledgments

We would like to thank the following for their contributions and support in the development of this project: 
-  **Android Developers Community** for providing valuable documentation and resources. 
-  **Open-source contributors** who suggested features and helped resolve bugs. 
-  **Faculty and Students** for offering insights and feedback on functionality. 
-  **Firebase/SQLite teams** for their reliable database solutions. Special thanks to everyone who reported bugs, suggested improvements, or contributed to making this project better!

---
