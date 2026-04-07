

import java.util.*;

/**
 * Intent Classifier - Identifies the user's intent using keyword matching
 * and pattern-based NLP techniques.
 */
public class IntentClassifier {

    public enum Intent {
        GREETING, FAREWELL, THANKS, HELP,
        ABOUT_BOT, ABOUT_CODEAPLHA,
        JAVA_QUESTION, OOP_QUESTION, DATA_STRUCTURES,
        EXCEPTION_HANDLING, MULTITHREADING, COLLECTIONS,
        JAVA_IO, GENERICS, LAMBDA, STREAMS,
        TIME_QUESTION, WEATHER_QUESTION,
        JOKE, COMPLIMENT, INSULT,
        REPEAT, WHAT_CAN_YOU_DO,
        UNKNOWN
    }

    // Each intent maps to a list of trigger keywords/phrases
    private final Map<Intent, List<String>> intentPatterns = new LinkedHashMap<>();

    public IntentClassifier() {
        initializePatterns();
    }

    private void initializePatterns() {
        intentPatterns.put(Intent.GREETING, Arrays.asList(
            "hello", "hi", "hey", "howdy", "greetings", "good morning",
            "good afternoon", "good evening", "sup", "what's up", "yo"
        ));
        intentPatterns.put(Intent.FAREWELL, Arrays.asList(
            "bye", "goodbye", "see you", "see ya", "later", "farewell",
            "good night", "take care", "quit", "exit", "cya"
        ));
        intentPatterns.put(Intent.THANKS, Arrays.asList(
            "thank", "thanks", "thank you", "thx", "ty", "appreciate",
            "grateful", "cheers"
        ));
        intentPatterns.put(Intent.HELP, Arrays.asList(
            "help", "assist", "support", "guide", "how to use", "instructions"
        ));
        intentPatterns.put(Intent.ABOUT_BOT, Arrays.asList(
            "who are you", "what are you", "your name", "about you",
            "introduce yourself", "tell me about yourself", "are you a bot",
            "are you ai", "are you human"
        ));
        intentPatterns.put(Intent.ABOUT_CODEAPLHA, Arrays.asList(
            "codealpha", "code alpha", "company", "internship", "organization",
            "your creator", "who made you", "who built you"
        ));
        intentPatterns.put(Intent.JAVA_QUESTION, Arrays.asList(
            "java", "jvm", "jdk", "jre", "bytecode", "compile", "java program",
            "java language", "java history", "what is java", "why java"
        ));
        intentPatterns.put(Intent.OOP_QUESTION, Arrays.asList(
            "oop", "object oriented", "class", "object", "inheritance",
            "polymorphism", "encapsulation", "abstraction", "interface",
            "abstract class", "override", "overload"
        ));
        intentPatterns.put(Intent.DATA_STRUCTURES, Arrays.asList(
            "array", "linked list", "stack", "queue", "tree", "graph",
            "hash", "hashmap", "arraylist", "data structure", "sorting",
            "searching", "binary", "heap"
        ));
        intentPatterns.put(Intent.EXCEPTION_HANDLING, Arrays.asList(
            "exception", "try", "catch", "finally", "throw", "throws",
            "error", "nullpointer", "runtimeexception", "checked", "unchecked"
        ));
        intentPatterns.put(Intent.MULTITHREADING, Arrays.asList(
            "thread", "multithreading", "concurrency", "synchronized", "runnable",
            "callable", "executor", "deadlock", "race condition", "volatile"
        ));
        intentPatterns.put(Intent.COLLECTIONS, Arrays.asList(
            "collection", "list", "set", "map", "iterator", "hashset",
            "treemap", "linkedhashmap", "vector", "collections framework"
        ));
        intentPatterns.put(Intent.JAVA_IO, Arrays.asList(
            "file", "io", "input", "output", "stream", "reader", "writer",
            "buffered", "scanner", "fileinput", "fileoutput", "serialization"
        ));
        intentPatterns.put(Intent.GENERICS, Arrays.asList(
            "generic", "generics", "type parameter", "wildcard", "bounded"
        ));
        intentPatterns.put(Intent.LAMBDA, Arrays.asList(
            "lambda", "functional interface", "arrow", "method reference",
            "predicate", "function", "consumer", "supplier", "comparator"
        ));
        intentPatterns.put(Intent.STREAMS, Arrays.asList(
            "stream", "filter", "map", "reduce", "collect", "foreach",
            "optional", "stream api", "parallel stream"
        ));
        intentPatterns.put(Intent.TIME_QUESTION, Arrays.asList(
            "time", "date", "today", "current time", "what time", "what day"
        ));
        intentPatterns.put(Intent.JOKE, Arrays.asList(
            "joke", "funny", "laugh", "humor", "tell me a joke", "make me laugh"
        ));
        intentPatterns.put(Intent.COMPLIMENT, Arrays.asList(
            "good bot", "smart", "great bot", "awesome bot", "you are great",
            "brilliant", "love you", "you're amazing", "best bot"
        ));
        intentPatterns.put(Intent.WHAT_CAN_YOU_DO, Arrays.asList(
            "what can you do", "capabilities", "features", "what do you know",
            "topics", "ask you", "your skills", "what do you help"
        ));
        intentPatterns.put(Intent.REPEAT, Arrays.asList(
            "repeat", "say again", "what did you say", "again", "pardon", "come again"
        ));
    }

    /**
     * Classify the intent of the user's input.
     * Uses keyword matching with scoring to find the best intent.
     */
    public Intent classify(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) return Intent.UNKNOWN;

        String lower = userInput.toLowerCase();
        Intent bestIntent = Intent.UNKNOWN;
        int bestScore = 0;

        for (Map.Entry<Intent, List<String>> entry : intentPatterns.entrySet()) {
            int score = 0;
            for (String pattern : entry.getValue()) {
                if (lower.contains(pattern)) {
                    // Longer patterns = more specific = higher weight
                    score += pattern.split("\\s+").length * 2;
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestIntent = entry.getKey();
            }
        }
        return bestIntent;
    }

    /**
     * Get confidence score (0.0 to 1.0) for the classified intent.
     */
    public double getConfidence(String userInput, Intent intent) {
        if (intent == Intent.UNKNOWN) return 0.0;
        String lower = userInput.toLowerCase();
        List<String> patterns = intentPatterns.getOrDefault(intent, new ArrayList<>());
        long matched = patterns.stream().filter(lower::contains).count();
        return Math.min(1.0, matched / 2.0);
    }
}
