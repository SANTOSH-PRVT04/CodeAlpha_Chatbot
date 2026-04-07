
import java.util.*;

/**
 * ChatBot Engine — Core processing logic.
 * Combines NLP (Tokenizer + IntentClassifier) with the KnowledgeBase
 * to generate intelligent responses.
 */
public class ChatBotEngine {

    private static final IntentClassifier.Intent UNKNOWN_INTENT = IntentClassifier.Intent.UNKNOWN;

    private final Tokenizer tokenizer;
    private final IntentClassifier classifier;
    private final KnowledgeBase knowledgeBase;
    private final List<String> conversationHistory;
    private String lastResponse = "";
    private int messageCount = 0;

    public ChatBotEngine() {
        this.tokenizer = new Tokenizer();
        this.classifier = new IntentClassifier();
        this.knowledgeBase = new KnowledgeBase();
        this.conversationHistory = new ArrayList<>();
    }

    /**
     * Main method: process user input and return a bot response.
     */
    public String processInput(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "Please type something! I'm here to help.";
        }

        userInput = userInput.trim();
        messageCount++;

        // Store in history
        conversationHistory.add("User: " + userInput);

        // Step 1: Detect sentiment
        String sentiment = tokenizer.detectSentiment(userInput);

        // Step 2: Classify intent
        IntentClassifier.Intent intent = classifier.classify(userInput);
        double confidence = classifier.getConfidence(userInput, intent);

        // Step 3: Get response
        String response = generateResponse(userInput, intent, sentiment, confidence);
        response = sanitizeText(response);

        // Store response in history
        conversationHistory.add("Bot: " + response);
        lastResponse = response;

        return response;
    }

    /**
     * Generate response based on intent, sentiment, and context.
     */
    private String generateResponse(String input, IntentClassifier.Intent intent, String sentiment, double confidence) {

        // Handle very negative sentiment specially
        if (sentiment.equals("negative") && intent == UNKNOWN_INTENT) {
            return "I sense some frustration. 😔 I'm sorry if I wasn't helpful!\n\nLet me try again — could you rephrase your question? I'm here to help with Java programming topics! 💪";
        }

        // Context-aware: first message
        if (messageCount == 1 && intent == UNKNOWN_INTENT) {
            return "Welcome! 👋 I'm JavaBot — your Java programming assistant!\n\nI can help you with Java concepts, OOP, data structures, collections, streams, and much more.\n\nTry asking: 'What is Java?' or type 'help' to see all topics!";
        }

        // Get response from knowledge base
        String intentKey = intent.name();
        String response = knowledgeBase.getResponse(intentKey);

        // Add confidence-based disclaimer for low confidence
        if (confidence < 0.3 && intent != UNKNOWN_INTENT) {
            response = "I think you're asking about " + formatIntentName(intent) + ".\n\n" + response + "\n\n💡 If that's not what you meant, try rephrasing!";
        }

        // Add positive reinforcement for positive sentiment
        if (sentiment.equals("positive") && !response.isEmpty()) {
            String[] openers = {"Great! ", "Awesome! ", "Love the enthusiasm! ", "Let's go! 🚀 "};
            if (Math.random() < 0.3) {
                response = openers[new Random().nextInt(openers.length)] + response;
            }
        }

        return response;
    }

    /**
     * Format intent name for display.
     */
    private String formatIntentName(IntentClassifier.Intent intent) {
        switch (intent) {
            case JAVA_QUESTION: return "Java";
            case OOP_QUESTION: return "Object-Oriented Programming";
            case DATA_STRUCTURES: return "Data Structures";
            case EXCEPTION_HANDLING: return "Exception Handling";
            case MULTITHREADING: return "Multithreading";
            case COLLECTIONS: return "Java Collections";
            case JAVA_IO: return "Java I/O";
            case GENERICS: return "Generics";
            case LAMBDA: return "Lambda Expressions";
            case STREAMS: return "Stream API";
            default: return intent.name().toLowerCase().replace("_", " ");
        }
    }

    /**
     * Returns a suggested follow-up question based on current intent.
     */
    public String getSuggestion(String userInput) {
        IntentClassifier.Intent intent = classifier.classify(userInput);
        Map<IntentClassifier.Intent, String> suggestions = new HashMap<>();
        suggestions.put(IntentClassifier.Intent.JAVA_QUESTION, "Try asking: 'What is OOP in Java?' or 'Explain JVM'");
        suggestions.put(IntentClassifier.Intent.OOP_QUESTION, "Try asking: 'What is polymorphism?' or 'Difference between abstract class and interface'");
        suggestions.put(IntentClassifier.Intent.DATA_STRUCTURES, "Try asking: 'How does HashMap work?' or 'Explain ArrayList vs LinkedList'");
        suggestions.put(IntentClassifier.Intent.EXCEPTION_HANDLING, "Try asking: 'What is checked vs unchecked exception?' or 'How to create custom exception?'");
        suggestions.put(IntentClassifier.Intent.LAMBDA, "Try asking: 'What is a functional interface?' or 'Explain Java Streams'");
        return suggestions.getOrDefault(intent, "Try asking about Java, OOP, Collections, Streams, or type 'help'!");
    }

    /**
     * Returns a greeting message when the bot starts.
     */
    public String getWelcomeMessage() {
        return sanitizeText("👋 Hello! I'm JavaBot — your intelligent Java programming assistant!\n\n" +
               "I'm powered by NLP and built entirely in Java. I can help you with:\n" +
               "• Java concepts & syntax\n" +
               "• OOP, Collections, Streams\n" +
               "• Exception Handling & Threads\n" +
               "• And much more!\n\n" +
               "Type your question below or type 'help' to get started. 🚀");
    }

    private String sanitizeText(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String normalized = text;
        normalized = normalized.replace("\u2022", "-");
        normalized = normalized.replace("\u2192", "->");
        normalized = normalized.replace("\u2014", "-");
        normalized = normalized.replace("\u2013", "-");
        normalized = normalized.replace("\u2018", "'");
        normalized = normalized.replace("\u2019", "'");
        normalized = normalized.replace("\u201C", "\"");
        normalized = normalized.replace("\u201D", "\"");
        normalized = normalized.replace("\u2026", "...");
        normalized = normalized.replaceAll("[^\\x00-\\x7F]", "");
        return normalized;
    }

    /**
     * Get full conversation history.
     */
    public List<String> getConversationHistory() {
        return Collections.unmodifiableList(conversationHistory);
    }

    /**
     * Get count of messages exchanged.
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * Get the last bot response (for repeat intent).
     */
    public String getLastResponse() {
        return lastResponse;
    }

    /**
     * Clear conversation history.
     */
    public void clearHistory() {
        conversationHistory.clear();
        messageCount = 0;
        lastResponse = "";
    }
}
