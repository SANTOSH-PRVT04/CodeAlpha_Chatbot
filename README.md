# 🤖 CodeAlpha_Chatbot — JavaBot

> A modern, dark-themed Java Chatbot with GUI, built as part of the **CodeAlpha Internship** program.

---

## 📌 About the Project

**JavaBot** is an intelligent chatbot application developed entirely in **Java**, featuring a sleek Swing-based GUI and a modular NLP-inspired backend. It can answer questions about Java programming, OOP concepts, data structures, and more — while also handling casual conversation like greetings, jokes, and compliments.

This project was built as a **CodeAlpha internship task** to demonstrate object-oriented design, event-driven GUI programming, and intent-based response generation.

---

## ✨ Features

- 🎨 **Modern Dark-Themed GUI** — Built with Java Swing featuring custom chat bubbles, rounded components, and a professional color palette
- 🧠 **Intent Classification** — Keyword-based NLP engine that identifies user intent with confidence scoring
- 📚 **Rich Knowledge Base** — Covers Java fundamentals, OOP, data structures, collections, multithreading, streams, lambdas, generics, I/O, and exception handling
- 💬 **Conversational Responses** — Handles greetings, farewells, thanks, jokes, compliments, and small talk
- ⚡ **Quick-Reply Chips** — One-click buttons for common questions
- 🔄 **Typing Animation** — Simulated bot "thinking" delay for a realistic chat experience
- 🗑️ **Clear Chat** — Option to reset the conversation
- ⌨️ **Keyboard Shortcut** — Press `Enter` to send messages
- 🧹 **Text Normalization** — Handles special characters gracefully for cross-platform font compatibility

---

## 🗂️ Project Structure

```
CodeAlpha_Chatbot/
│
├── Main.java               # Entry point — launches the GUI
├── ChatBotGUI.java         # Full Swing GUI (chat bubbles, header, input panel)
├── ChatBotEngine.java      # Core response logic and conversation history
├── IntentClassifier.java   # Keyword-based intent detection with scoring
├── KnowledgeBase.java      # Response repository for all supported topics
├── Tokenizer.java          # Text tokenization and preprocessing utility
├── .gitignore
└── README.md
```

---

## 🧩 Architecture Overview

```
User Input
    │
    ▼
Tokenizer           ← Cleans and tokenizes input text
    │
    ▼
IntentClassifier    ← Matches keywords → assigns Intent enum + confidence score
    │
    ▼
ChatBotEngine       ← Looks up response from KnowledgeBase, manages history
    │
    ▼
ChatBotGUI          ← Renders response as a styled chat bubble in the UI
```

---

## 🎯 Supported Intent Categories

| Category | Example Queries |
|---|---|
| `GREETING` | "Hello", "Hi", "Hey", "Good morning" |
| `FAREWELL` | "Bye", "See you", "Exit", "Quit" |
| `THANKS` | "Thank you", "Thanks", "Appreciate it" |
| `HELP` | "Help", "How to use", "Guide me" |
| `ABOUT_BOT` | "Who are you?", "Are you a bot?" |
| `ABOUT_CODEALPHA` | "Who made you?", "What is CodeAlpha?" |
| `JAVA_QUESTION` | "What is Java?", "Tell me about JVM" |
| `OOP_QUESTION` | "Explain OOP", "What is inheritance?" |
| `DATA_STRUCTURES` | "What is a linked list?", "Explain trees" |
| `EXCEPTION_HANDLING` | "What is try-catch?", "Checked exceptions" |
| `MULTITHREADING` | "What is a thread?", "Explain deadlock" |
| `COLLECTIONS` | "Java collections", "What is HashMap?" |
| `JAVA_IO` | "File handling in Java", "What is a stream?" |
| `GENERICS` | "What are generics?", "Wildcard in Java" |
| `LAMBDA` | "Lambda expressions", "Functional interface" |
| `STREAMS` | "Stream API", "filter and map in Java" |
| `TIME_QUESTION` | "What time is it?", "What's today's date?" |
| `JOKE` | "Tell me a joke", "Make me laugh" |
| `COMPLIMENT` | "Good bot!", "You're awesome" |
| `WHAT_CAN_YOU_DO` | "What can you do?", "Your capabilities" |

---

## 🖥️ GUI Highlights

- **Window Title:** `JavaBot — AI Chatbot | CodeAlpha Project`
- **Window Size:** 820 × 680 px (resizable, min 600 × 500)
- **Color Scheme:**
  - Background: `#12121E` (deep dark blue)
  - Bot Bubbles: `#282841`
  - User Bubbles: `#3864B4` (blue)
  - Accent: `#63B3ED` (light blue)
  - Online Status: `#48C78E` (green)
- **Fonts:** Segoe UI (primary), Consolas (code/mono)
- **Scrollbar:** Custom-styled, minimal dark scrollbar

---

## 🚀 Getting Started

### Prerequisites

- **Java JDK 8 or higher**
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code with Java extension) or terminal

### Clone the Repository

```bash
git clone https://github.com/SANTOSH-PRVT04/CodeAlpha_Chatbot.git
cd CodeAlpha_Chatbot
```

### Compile

```bash
javac *.java
```

### Run

```bash
java Main
```

The JavaBot GUI window will open immediately.

---

## 💡 How It Works

1. The user types a message in the input field and presses **Send** or hits **Enter**.
2. The `Tokenizer` preprocesses the input (lowercasing, cleaning).
3. The `IntentClassifier` scans for matching keyword patterns across all intent categories. Longer, more specific patterns carry higher scores.
4. The best-matched intent (along with a confidence score) is passed to `ChatBotEngine`.
5. `ChatBotEngine` retrieves an appropriate response from `KnowledgeBase` and maintains conversation history.
6. The GUI renders the bot's reply as a styled bubble with a timestamp after a short simulated "typing" delay (400–700 ms).

---

## 📸 UI Components

| Component | Description |
|---|---|
| **Header** | Bot avatar (circular), name ("JavaBot"), online status, message count, Clear Chat button |
| **Chat Area** | Scrollable panel with alternating left (bot) / right (user) bubbles |
| **Quick Chips** | Pre-filled buttons: "What is Java?", "Explain OOP", "Tell a joke", "What can you do?" |
| **Input Field** | Dark-styled text field with placeholder text |
| **Send Button** | Rounded primary button with hover effect |

---

## 🔧 Key Classes

### `IntentClassifier.java`
- Defines an `Intent` enum with 25+ intent types
- Uses a `LinkedHashMap<Intent, List<String>>` for ordered keyword matching
- Scoring: each matched keyword is weighted by word count (longer phrases = higher specificity)
- Provides `classify(String)` and `getConfidence(String, Intent)` methods

### `ChatBotGUI.java`
- Full Swing UI built from scratch (no external UI libraries)
- Custom-painted rounded bubbles using `RoundRectangle2D`
- Asynchronous response rendering via `javax.swing.Timer`
- Text normalization strips Unicode characters for cross-platform safety

---

## 👨‍💻 Author

**Santosh** — CodeAlpha Internship  
GitHub: [@SANTOSH-PRVT04](https://github.com/SANTOSH-PRVT04)

---

## 🏢 Organization

Built as part of the **[CodeAlpha](https://codealpha.tech)** Internship Program.

---

## 📄 License

This project is open source and available for educational purposes.

---

## 🙌 Acknowledgements

- CodeAlpha for the internship opportunity and project brief
- Java Swing documentation for GUI components
- The open-source Java community for inspiration
