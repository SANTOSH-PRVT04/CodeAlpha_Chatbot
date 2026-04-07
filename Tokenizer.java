

import java.util.*;

/**
 * NLP Tokenizer - Handles text preprocessing, tokenization, and normalization.
 * Part of the Natural Language Processing pipeline.
 */
public class Tokenizer {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "a", "an", "the", "is", "it", "in", "on", "at", "to", "for",
        "of", "and", "or", "but", "i", "me", "my", "we", "you", "your",
        "he", "she", "they", "this", "that", "with", "be", "are", "was",
        "were", "do", "does", "did", "will", "can", "could", "would", "should",
        "have", "has", "had", "not", "no", "so", "if", "as", "by", "from"
    ));

    /**
     * Tokenize input text into a list of meaningful tokens.
     */
    public List<String> tokenize(String text) {
        if (text == null || text.isEmpty()) return new ArrayList<>();

        // Lowercase and clean special characters
        String cleaned = text.toLowerCase().replaceAll("[^a-z0-9\\s'?!]", " ");

        // Split by whitespace
        String[] words = cleaned.trim().split("\\s+");

        List<String> tokens = new ArrayList<>();
        for (String word : words) {
            // Remove punctuation from word boundaries
            word = word.replaceAll("^[^a-z0-9]+|[^a-z0-9]+$", "");
            if (!word.isEmpty()) {
                tokens.add(word);
            }
        }
        return tokens;
    }

    /**
     * Remove stop words from token list, keeping meaningful keywords.
     */
    public List<String> removeStopWords(List<String> tokens) {
        List<String> filtered = new ArrayList<>();
        for (String token : tokens) {
            if (!STOP_WORDS.contains(token)) {
                filtered.add(token);
            }
        }
        return filtered;
    }

    /**
     * Basic stemming: strips common suffixes to find root word.
     */
    public String stem(String word) {
        if (word.endsWith("ing") && word.length() > 6)
            return word.substring(0, word.length() - 3);
        if (word.endsWith("tion") && word.length() > 6)
            return word.substring(0, word.length() - 4);
        if (word.endsWith("ed") && word.length() > 4)
            return word.substring(0, word.length() - 2);
        if (word.endsWith("ly") && word.length() > 4)
            return word.substring(0, word.length() - 2);
        if (word.endsWith("ies") && word.length() > 4)
            return word.substring(0, word.length() - 3) + "y";
        if (word.endsWith("s") && word.length() > 3 && !word.endsWith("ss"))
            return word.substring(0, word.length() - 1);
        return word;
    }

    /**
     * Full preprocessing pipeline: tokenize → remove stop words → stem
     */
    public List<String> preprocess(String text) {
        List<String> tokens = tokenize(text);
        List<String> filtered = removeStopWords(tokens);
        List<String> stemmed = new ArrayList<>();
        for (String token : filtered) {
            stemmed.add(stem(token));
        }
        return stemmed;
    }

    /**
     * Detect the sentiment of text (positive/negative/neutral).
     */
    public String detectSentiment(String text) {
        String lower = text.toLowerCase();
        long positive = Arrays.stream(new String[]{"good","great","awesome","love","thanks","thank","nice","happy","excellent","wonderful","amazing","best","cool","yes","sure","perfect"})
                              .filter(lower::contains).count();
        long negative = Arrays.stream(new String[]{"bad","hate","terrible","awful","worst","no","not","wrong","angry","sad","horrible","ugly","never","stupid","error"})
                              .filter(lower::contains).count();
        if (positive > negative) return "positive";
        if (negative > positive) return "negative";
        return "neutral";
    }

    /**
     * Check if text is a question.
     */
    public boolean isQuestion(String text) {
        String trimmed = text.trim();
        if (trimmed.endsWith("?")) return true;
        String lower = trimmed.toLowerCase();
        return lower.startsWith("what") || lower.startsWith("who") ||
               lower.startsWith("where") || lower.startsWith("when") ||
               lower.startsWith("why") || lower.startsWith("how") ||
               lower.startsWith("is ") || lower.startsWith("are ") ||
               lower.startsWith("can ") || lower.startsWith("do ") ||
               lower.startsWith("does ") || lower.startsWith("tell me");
    }
}
