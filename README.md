# monoApp 🐒🚭  
**A friendly, science-backed web app that helps young adults quit smoking — one step (and one monkey cheer) at a time.**

![Status](https://img.shields.io/badge/status-alpha-yellow.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![Stack](https://img.shields.io/badge/stack-React%20%7C%20Spring%20Boot%20%7C%20PostgreSQL%20%7C%20Redis-blue.svg)

---

## 📖 About monoApp
monoApp is a web application designed to support young adults in their journey to quit smoking **gradually and sustainably**.  
Instead of forcing abrupt quitting, monoApp focuses on **progressive reduction**, **psychological support**, and **positive reinforcement**.

By combining daily goals, medical insights, financial motivation, and a friendly Monkey mascot, monoApp turns a difficult habit-breaking process into a structured, encouraging experience.

---

## 🎯 Problem Definition
Smoking remains one of the most common and harmful habits among young adults. Many people attempt to quit several times but fail due to:
- Anxiety and withdrawal symptoms  
- Lack of motivation  
- Absence of a clear, structured plan  

Quitting abruptly often increases stress and relapse rates. monoApp addresses this issue by providing **guided reduction**, **emotional support**, and **clear daily objectives**.

---

## 👥 Target Users
monoApp is designed for:
- **Young adults aged 18–25**
- Individuals who have **tried to quit smoking multiple times**
- Users who are aware of health risks but struggle with self-discipline or anxiety
- People comfortable using mobile/web applications to improve their lifestyle

---

## ✨ Key Features

### 🚬 Cigarettes Counter
- Daily allowed cigarette limit calculated for the user  
- The limit **gradually decreases over time**
- Staying below the limit marks the day as a **success**
- Designed to reduce dependency without extreme stress

### 🧠 Medical Data & Motivational Tips
- Short motivational messages to help maintain streaks
- Medical facts explaining the real impact of smoking
- Keeps users informed, aware, and encouraged during difficult moments

### 💰 Savings Counter
- Real-time calculation of money saved
- Shows the **financial impact** of smoking less
- Reinforces positive behavior through visible rewards

### 🐒 Monkey Mascot
- A friendly companion throughout the quitting process
- Reacts emotionally to the user’s progress:
  - 😢 Sad when the daily limit is exceeded
  - 🎉 Happy and celebratory when goals are met
- Adds empathy, motivation, and personality to the app

---

## 🧭 How It Works
1. The user sets their current smoking habits  
2. monoApp generates a **progressive reduction plan**
3. Each day the user logs their cigarette consumption  
4. The app updates:
   - Daily success or failure
   - Savings counter
   - Motivational content
   - Monkey mascot reactions
5. Over time, the daily limit decreases until it reaches **zero**

---

## 🛠️ Tech Stack

### Frontend
- **React**
  - Component-based UI
  - Fast rendering
  - Easy future migration to React Native or PWA
- **HTML & CSS**
  - Responsive and accessible design

### Backend
- **Spring Boot**
  - Layered architecture (Controller / Service / Repository)
  - RESTful API
  - Built-in security and scalability
  - Enterprise-grade reliability

### Database & Caching
- **PostgreSQL**
  - Primary relational database
  - ACID compliance and data integrity
- **Redis**
  - In-memory cache
  - Fast counters and session handling

### DevOps & Deployment
- **Docker** – containerized development & deployment
- **GitHub** – version control & collaboration
- **Vercel** – recommended frontend hosting (fast, scalable, GitHub-integrated)

---

## ⚙️ Installation (Development)

```bash
# Clone the repository
git clone https://github.com/your-username/monoApp.git
cd monoApp
```
## 🤝 Contributing

Contributions are welcome!

Fork the repository

Create a feature branch

Commit your changes

Open a Pull Request

Please follow existing code style and best practices.

## 👨‍💻 Authors

Alejandro Rondan Garcia

Samuel Tamayo Ruiz

Tomás Cotelo Álvarez

Taima Altalafha
