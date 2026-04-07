

import java.util.*;

/**
 * Knowledge Base - Stores all FAQ responses and domain knowledge.
 * Acts as the "trained" data for the chatbot's rule-based system.
 */
public class KnowledgeBase {

    // Each key = intent name, value = list of possible responses (picked randomly)
    private final Map<String, List<String>> responses = new HashMap<>();

    public KnowledgeBase() {
        loadResponses();
    }

    private void loadResponses() {

        // ── Greetings ──────────────────────────────────────────────────────────
        responses.put("GREETING", Arrays.asList(
            "Hello there! 👋 I'm JavaBot, your Java programming assistant. How can I help you today?",
            "Hi! Great to see you! I'm here to help with all your Java questions. What's on your mind?",
            "Hey! Welcome! I'm JavaBot — ask me anything about Java programming. 🚀",
            "Greetings, coder! Ready to talk Java? Fire away! 💻"
        ));

        // ── Farewell ───────────────────────────────────────────────────────────
        responses.put("FAREWELL", Arrays.asList(
            "Goodbye! Happy coding! 👋 Come back if you have more Java questions!",
            "See you later! Keep coding and stay curious! 🚀",
            "Farewell! Remember: every expert was once a beginner. Keep going! 💪",
            "Bye! May your code compile on the first try! 😄"
        ));

        // ── Thanks ─────────────────────────────────────────────────────────────
        responses.put("THANKS", Arrays.asList(
            "You're welcome! 😊 Feel free to ask if you have more questions!",
            "Happy to help! That's what I'm here for! 🤖",
            "Anytime! Helping you learn Java is my purpose! 🎯",
            "No problem at all! Keep up the great learning! ✨"
        ));

        // ── Help ───────────────────────────────────────────────────────────────
        responses.put("HELP", Arrays.asList(
            "I can help you with:\n• Java fundamentals & syntax\n• OOP concepts\n• Data Structures\n• Exception Handling\n• Multithreading\n• Java Collections\n• Streams & Lambdas\n• Generics & I/O\n\nJust ask me any Java question! 💡"
        ));

        // ── About Bot ──────────────────────────────────────────────────────────
        responses.put("ABOUT_BOT", Arrays.asList(
            "I'm JavaBot 🤖 — an AI-powered chatbot built entirely in Java!\n\nI was created as part of the CodeAlpha Java Internship Project.\n\nI use NLP techniques like tokenization, intent classification, and pattern matching to understand your questions and provide helpful answers about Java programming.",
            "My name is JavaBot! I'm a rule-based + NLP chatbot built in Java using Swing GUI.\n\nI specialize in answering Java programming questions — from basics to advanced topics! 💻"
        ));

        // ── About CodeAlpha ────────────────────────────────────────────────────
        responses.put("ABOUT_CODEAPLHA", Arrays.asList(
            "CodeAlpha is a tech internship and training platform that provides hands-on programming projects to students and developers.\n\nI was created as Task 3 of the Java Programming internship — an AI chatbot project. 🎓",
            "CodeAlpha offers real-world tech internship projects! This chatbot is a Java project built for the CodeAlpha Java Programming track. 💼"
        ));

        // ── Java Basics ────────────────────────────────────────────────────────
        responses.put("JAVA_QUESTION", Arrays.asList(
            "Java is a high-level, class-based, object-oriented programming language developed by James Gosling at Sun Microsystems (now Oracle) in 1995.\n\n🔑 Key features:\n• Platform independent (Write Once, Run Anywhere)\n• Object-Oriented\n• Strongly typed\n• Automatic memory management (Garbage Collection)\n• Multithreaded\n• Robust and secure\n\nJava runs on the JVM (Java Virtual Machine), which makes it platform independent!",
            "Java is one of the world's most popular programming languages!\n\n📌 Java Architecture:\n• Source code (.java) → Compiled to Bytecode (.class) by javac\n• Bytecode runs on JVM (any platform)\n• JRE = JVM + Libraries\n• JDK = JRE + Development Tools\n\nCurrent version: Java 21 (LTS)"
        ));

        // ── OOP ────────────────────────────────────────────────────────────────
        responses.put("OOP_QUESTION", Arrays.asList(
            "Object-Oriented Programming (OOP) in Java has 4 core pillars:\n\n1️⃣ Encapsulation — Wrapping data and methods in a class, using private fields with public getters/setters.\n\n2️⃣ Inheritance — A class inherits properties from another using 'extends'. Promotes code reuse.\n\n3️⃣ Polymorphism — Same method name, different behavior. Method Overloading (compile-time) & Overriding (runtime).\n\n4️⃣ Abstraction — Hiding implementation details using abstract classes or interfaces.\n\nExample:\n```java\nclass Animal {\n  void speak() { System.out.println(\"...\"); }\n}\nclass Dog extends Animal {\n  @Override\n  void speak() { System.out.println(\"Woof!\"); }\n}\n```",
            "Java OOP Quick Reference:\n\n• Class: Blueprint for objects\n• Object: Instance of a class\n• Interface: Contract with abstract methods\n• Abstract Class: Can have both abstract & concrete methods\n• 'extends': Used for class inheritance\n• 'implements': Used to implement interfaces\n\nJava supports SINGLE class inheritance but MULTIPLE interface implementation! 🔑"
        ));

        // ── Data Structures ────────────────────────────────────────────────────
        responses.put("DATA_STRUCTURES", Arrays.asList(
            "Common Java Data Structures:\n\n📌 Array — Fixed size, fast access O(1)\n📌 ArrayList — Dynamic array, O(1) access\n📌 LinkedList — Node-based, O(1) insert/delete\n📌 Stack — LIFO (Last In First Out)\n📌 Queue — FIFO (First In First Out)\n📌 HashMap — Key-value pairs, O(1) avg lookup\n📌 TreeMap — Sorted key-value pairs, O(log n)\n📌 HashSet — Unique elements, no duplicates\n📌 PriorityQueue — Elements ordered by priority\n\nExample:\n```java\nArrayList<String> list = new ArrayList<>();\nlist.add(\"Java\");\nHashMap<String, Integer> map = new HashMap<>();\nmap.put(\"Java\", 1995);\n```"
        ));

        // ── Exception Handling ─────────────────────────────────────────────────
        responses.put("EXCEPTION_HANDLING", Arrays.asList(
            "Java Exception Handling:\n\n✅ Syntax:\n```java\ntry {\n  // risky code\n} catch (ExceptionType e) {\n  // handle exception\n} finally {\n  // always runs\n}\n```\n\n📌 Types:\n• Checked Exceptions — Must be handled (IOException, SQLException)\n• Unchecked Exceptions — Runtime errors (NullPointerException, ArrayIndexOutOfBoundsException)\n• Error — JVM errors (OutOfMemoryError)\n\n📌 Custom Exception:\n```java\nclass MyException extends Exception {\n  public MyException(String msg) { super(msg); }\n}\n```"
        ));

        // ── Multithreading ─────────────────────────────────────────────────────
        responses.put("MULTITHREADING", Arrays.asList(
            "Java Multithreading:\n\n🔹 Two ways to create threads:\n\n1) Extend Thread class:\n```java\nclass MyThread extends Thread {\n  public void run() { /* task */ }\n}\n```\n\n2) Implement Runnable:\n```java\nThread t = new Thread(() -> System.out.println(\"Running!\"));\nt.start();\n```\n\n📌 Key concepts:\n• synchronized — prevents race conditions\n• volatile — ensures visibility across threads\n• wait() / notify() — thread communication\n• ExecutorService — thread pool management\n• Deadlock — two threads blocking each other (avoid with proper locking order)"
        ));

        // ── Collections ────────────────────────────────────────────────────────
        responses.put("COLLECTIONS", Arrays.asList(
            "Java Collections Framework:\n\n📌 Hierarchy:\n• Collection\n  ├── List (ArrayList, LinkedList, Vector)\n  ├── Set (HashSet, LinkedHashSet, TreeSet)\n  └── Queue (PriorityQueue, ArrayDeque)\n• Map (HashMap, LinkedHashMap, TreeMap, Hashtable)\n\n📌 Sorting:\n```java\nCollections.sort(list);\nCollections.sort(list, Comparator.reverseOrder());\n```\n\n📌 Iterating:\n```java\nfor (String item : list) { ... }       // Enhanced for\nlist.forEach(item -> System.out.println(item)); // Lambda\n```"
        ));

        // ── Java I/O ───────────────────────────────────────────────────────────
        responses.put("JAVA_IO", Arrays.asList(
            "Java I/O (Input/Output):\n\n📌 Reading a file:\n```java\nBufferedReader reader = new BufferedReader(new FileReader(\"file.txt\"));\nString line;\nwhile ((line = reader.readLine()) != null) {\n  System.out.println(line);\n}\nreader.close();\n```\n\n📌 Writing a file:\n```java\nFileWriter writer = new FileWriter(\"output.txt\");\nwriter.write(\"Hello, Java!\");\nwriter.close();\n```\n\n📌 Scanner (user input):\n```java\nScanner sc = new Scanner(System.in);\nString input = sc.nextLine();\n```"
        ));

        // ── Generics ───────────────────────────────────────────────────────────
        responses.put("GENERICS", Arrays.asList(
            "Java Generics allow type-safe code:\n\n```java\n// Generic class\nclass Box<T> {\n  private T value;\n  public Box(T v) { this.value = v; }\n  public T get() { return value; }\n}\n\n// Generic method\npublic <T> void print(T item) {\n  System.out.println(item);\n}\n\n// Bounded type\npublic <T extends Number> double sum(T a, T b) {\n  return a.doubleValue() + b.doubleValue();\n}\n\n// Wildcard\npublic void display(List<?> list) { ... }\n```\n\nBenefits: Type safety, eliminates casting, reusable code! ✅"
        ));

        // ── Lambda ─────────────────────────────────────────────────────────────
        responses.put("LAMBDA", Arrays.asList(
            "Java Lambda Expressions (Java 8+):\n\nSyntax: (parameters) -> expression\n\n```java\n// Without lambda\nRunnable r = new Runnable() {\n  public void run() { System.out.println(\"Hello\"); }\n};\n\n// With lambda\nRunnable r = () -> System.out.println(\"Hello\");\n\n// Sorting with lambda\nlist.sort((a, b) -> a.compareTo(b));\n\n// Common functional interfaces:\nPredicate<String> isEmpty = s -> s.isEmpty();\nFunction<String, Integer> length = s -> s.length();\nConsumer<String> printer = s -> System.out.println(s);\nSupplier<String> supplier = () -> \"Hello\";\n```"
        ));

        // ── Streams ────────────────────────────────────────────────────────────
        responses.put("STREAMS", Arrays.asList(
            "Java Stream API (Java 8+):\n\n```java\nList<String> names = Arrays.asList(\"Alice\", \"Bob\", \"Charlie\", \"Anna\");\n\n// Filter, map, collect\nList<String> result = names.stream()\n  .filter(n -> n.startsWith(\"A\"))   // [Alice, Anna]\n  .map(String::toUpperCase)          // [ALICE, ANNA]\n  .sorted()\n  .collect(Collectors.toList());\n\n// Count\nlong count = names.stream().filter(n -> n.length() > 3).count();\n\n// Reduce\nint sum = IntStream.rangeClosed(1, 10).reduce(0, Integer::sum); // 55\n\n// forEach\nnames.stream().forEach(System.out::println);\n```\n\nStreams don't modify the original collection — they create pipelines! 🔁"
        ));

        // ── Time ───────────────────────────────────────────────────────────────
        responses.put("TIME_QUESTION", Arrays.asList(
            "The current date and time on your system is:\n" + new java.util.Date().toString() + "\n\nIn Java, you can get it with:\n```java\nimport java.time.LocalDateTime;\nLocalDateTime now = LocalDateTime.now();\nSystem.out.println(now);\n```"
        ));

        // ── Jokes ──────────────────────────────────────────────────────────────
        responses.put("JOKE", Arrays.asList(
            "😄 Why do Java developers wear glasses?\n\nBecause they don't C#! 👓",
            "😂 A SQL query walks into a bar, walks up to two tables and asks...\n\n'Can I JOIN you?'",
            "🤣 Why did the programmer quit his job?\n\nBecause he didn't get arrays! (a raise)",
            "😄 How many programmers does it take to change a light bulb?\n\nNone — that's a hardware problem!",
            "😂 Java joke:\n```\nNullPointerException: Cannot make joke on null object.\n```\n...Even my jokes are strongly typed! 😅"
        ));

        // ── Compliment ─────────────────────────────────────────────────────────
        responses.put("COMPLIMENT", Arrays.asList(
            "Aww, thank you! 😊 You're making this bot blush (if I could)! Keep up the great learning!",
            "You're too kind! 🥰 But seriously — keep coding, you're doing amazing!",
            "Thanks! You're pretty great yourself! 💪 Now, shall we talk more Java? 😄"
        ));

        // ── What can you do ────────────────────────────────────────────────────
        responses.put("WHAT_CAN_YOU_DO", Arrays.asList(
            "Here's what I can help you with! 🤖\n\n📚 Java Topics:\n• Java basics & JVM architecture\n• Object-Oriented Programming (OOP)\n• Data Structures & Algorithms\n• Exception Handling\n• Multithreading & Concurrency\n• Java Collections Framework\n• Java I/O & File Handling\n• Generics\n• Lambda Expressions\n• Stream API\n\n🎭 Fun stuff:\n• Tell jokes\n• Answer general questions\n• Chat!\n\nJust type your question and I'll do my best! 💡"
        ));

        // ── Repeat ─────────────────────────────────────────────────────────────
        responses.put("REPEAT", Arrays.asList(
            "I'd be happy to repeat! What would you like me to clarify? 😊",
            "Sure! Could you tell me which part you'd like me to explain again? I'll make it clearer! 📖"
        ));

        // ── Unknown ────────────────────────────────────────────────────────────
        responses.put("UNKNOWN", Arrays.asList(
            "Hmm, I'm not sure I understood that. 🤔 Could you rephrase it?\n\nTip: Try asking something like:\n• 'What is Java?'\n• 'Explain OOP'\n• 'Tell me a joke'\n• 'What can you do?'",
            "I didn't quite catch that! 😅 I'm best at answering Java programming questions.\n\nTry asking: 'Explain exception handling' or 'What are Java streams?'",
            "That's an interesting input! I'm still learning. 😊 Try asking me about:\n• Java concepts\n• OOP principles\n• Data structures\n• Lambdas and Streams"
        ));
    }

    /**
     * Get a response for the given intent key.
     * Returns a random response from the list for variety.
     */
    public String getResponse(String intentKey) {
        List<String> options = responses.getOrDefault(intentKey, responses.get("UNKNOWN"));
        return options.get(new Random().nextInt(options.size()));
    }

    /**
     * Check if a response exists for the given key.
     */
    public boolean hasResponse(String intentKey) {
        return responses.containsKey(intentKey);
    }

    /**
     * Add a custom FAQ entry at runtime.
     */
    public void addFAQ(String key, String response) {
        responses.computeIfAbsent(key, k -> new ArrayList<>()).add(response);
    }
}
