
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ChatBot GUI — Modern, dark-themed Swing interface for the Java ChatBot.
 * Features chat bubbles, typing animation, and professional design.
 */
public class ChatBotGUI extends JFrame {

    // ── Colors ─────────────────────────────────────────────────────────────────
    private static final Color BG_DARK       = new Color(18, 18, 30);
    private static final Color BG_PANEL      = new Color(25, 25, 40);
    private static final Color BG_INPUT      = new Color(35, 35, 55);
    private static final Color ACCENT        = new Color(99, 179, 237);
    private static final Color ACCENT_GREEN  = new Color(72, 199, 142);
    private static final Color BOT_BUBBLE    = new Color(40, 40, 65);
    private static final Color USER_BUBBLE   = new Color(56, 100, 180);
    private static final Color TEXT_PRIMARY  = new Color(230, 230, 240);
    private static final Color TEXT_MUTED    = new Color(130, 130, 160);
    private static final Color HEADER_BG     = new Color(20, 20, 38);
    private static final Color SEND_BTN      = new Color(99, 140, 237);
    private static final Color SEND_HOVER    = new Color(130, 165, 255);

    // ── Fonts ──────────────────────────────────────────────────────────────────
    private static final Font FONT_MAIN      = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD      = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_MONO      = new Font("Consolas", Font.PLAIN, 13);

    // ── Components ─────────────────────────────────────────────────────────────
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;
    private JLabel statusLabel;
    private JLabel messageCountLabel;

    // ── Engine ─────────────────────────────────────────────────────────────────
    private final ChatBotEngine engine;
    private static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("HH:mm");

    // Replace symbols/emojis with plain ASCII so unsupported glyphs do not render as squares.
    private static String normalizeDisplayText(String text) {
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

    public ChatBotGUI() {
        this.engine = new ChatBotEngine();
        initializeGUI();
        showWelcomeMessage();
    }

    private void initializeGUI() {
        setTitle("JavaBot — AI Chatbot | CodeAlpha Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 680);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        // Main layout
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_DARK);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildChatArea(), BorderLayout.CENTER);
        add(buildInputPanel(), BorderLayout.SOUTH);

        setupKeyBindings();
    }

    // ── Header ─────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 90)),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        // Left: avatar + name
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        // Avatar circle
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT);
                g2.fillOval(0, 0, 38, 38);
                g2.setColor(HEADER_BG);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                String t = "J";
                g2.drawString(t, (38 - fm.stringWidth(t)) / 2, (38 + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        avatar.setPreferredSize(new Dimension(38, 38));
        avatar.setOpaque(false);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setOpaque(false);

        JLabel nameLabel = new JLabel("JavaBot");
        nameLabel.setFont(FONT_TITLE);
        nameLabel.setForeground(TEXT_PRIMARY);

        statusLabel = new JLabel("Online");
        statusLabel.setFont(FONT_SMALL);
        statusLabel.setForeground(ACCENT_GREEN);

        namePanel.add(nameLabel);
        namePanel.add(statusLabel);

        left.add(avatar);
        left.add(namePanel);

        // Right: controls
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);

        messageCountLabel = new JLabel("0 messages");
        messageCountLabel.setFont(FONT_SMALL);
        messageCountLabel.setForeground(TEXT_MUTED);

        JButton clearBtn = createIconButton("Clear Chat", new Color(80, 80, 110));
        clearBtn.addActionListener(e -> clearChat());

        right.add(messageCountLabel);
        right.add(clearBtn);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    // ── Chat Area ──────────────────────────────────────────────────────────────
    private JScrollPane buildChatArea() {
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(BG_DARK);
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBackground(BG_DARK);
        scrollPane.getViewport().setBackground(BG_DARK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBackground(BG_DARK);

        // Style scrollbar
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(70, 70, 100);
                trackColor = BG_DARK;
            }
            @Override
            protected JButton createDecreaseButton(int o) { return createZeroButton(); }
            @Override
            protected JButton createIncreaseButton(int o) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });

        return scrollPane;
    }

    // ── Input Panel ────────────────────────────────────────────────────────────
    private JPanel buildInputPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_PANEL);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(60, 60, 90)),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        // Suggestion bar
        JPanel suggestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        suggestPanel.setOpaque(false);
        suggestPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel hint = new JLabel("Quick:");
        hint.setFont(FONT_SMALL);
        hint.setForeground(TEXT_MUTED);
        suggestPanel.add(hint);

        String[] quickQuestions = {"What is Java?", "Explain OOP", "Tell a joke", "What can you do?"};
        for (String q : quickQuestions) {
            suggestPanel.add(createChipButton(q));
        }

        // Input row
        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setOpaque(false);

        inputField = new JTextField();
        inputField.setFont(FONT_MAIN);
        inputField.setBackground(BG_INPUT);
        inputField.setForeground(TEXT_PRIMARY);
        inputField.setCaretColor(ACCENT);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 90), 1, true),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        inputField.putClientProperty("JTextField.placeholderText", "Ask me anything about Java...");

        sendButton = createPrimaryButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        sendButton.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { sendButton.setBackground(SEND_HOVER); }
            @Override public void mouseExited(MouseEvent e) { sendButton.setBackground(SEND_BTN); }
        });

        inputRow.add(inputField, BorderLayout.CENTER);
        inputRow.add(sendButton, BorderLayout.EAST);

        wrapper.add(suggestPanel, BorderLayout.NORTH);
        wrapper.add(inputRow, BorderLayout.CENTER);
        return wrapper;
    }

    // ── Message Bubbles ────────────────────────────────────────────────────────
    private void addUserMessage(String text) {
        JPanel msgRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        msgRow.setOpaque(false);
        msgRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel bubble = createBubble(text, USER_BUBBLE, TEXT_PRIMARY, true);
        msgRow.add(bubble);
        chatPanel.add(msgRow);
        chatPanel.add(Box.createVerticalStrut(6));
        refreshChat();
    }

    private void addBotMessage(String text) {
        JPanel msgRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        msgRow.setOpaque(false);
        msgRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel bubble = createBubble(normalizeDisplayText(text), BOT_BUBBLE, TEXT_PRIMARY, false);
        msgRow.add(bubble);
        chatPanel.add(msgRow);
        chatPanel.add(Box.createVerticalStrut(6));
        refreshChat();
    }

    private JPanel createBubble(String text, Color bgColor, Color fgColor, boolean isUser) {
        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        bubble.setOpaque(false);
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        // Time label
        JLabel time = new JLabel(TIME_FMT.format(new Date()) + (isUser ? "  You" : "  JavaBot"));
        time.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        time.setForeground(TEXT_MUTED);
        time.setAlignmentX(isUser ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        // Message text (uses JTextArea for line wrapping)
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setForeground(fgColor);
        textArea.setFont(text.contains("```") ? FONT_MONO : FONT_MAIN);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setAlignmentX(isUser ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        int viewportWidth = scrollPane.getViewport().getWidth();
        if (viewportWidth <= 0) {
            viewportWidth = getWidth();
        }
        int maxWidth = Math.min(520, Math.max(280, viewportWidth - 180));

        // Force layout pass using max width, then lock a stable preferred size.
        textArea.setSize(new Dimension(maxWidth, Short.MAX_VALUE));
        Dimension pref = textArea.getPreferredSize();
        int bubbleTextWidth = Math.min(maxWidth, Math.max(220, pref.width));
        textArea.setPreferredSize(new Dimension(bubbleTextWidth, pref.height));
        textArea.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));

        bubble.add(time);
        bubble.add(Box.createVerticalStrut(4));
        bubble.add(textArea);

        int bubbleMax = bubbleTextWidth + 30;
        bubble.setMaximumSize(new Dimension(bubbleMax, Integer.MAX_VALUE));
        return bubble;
    }

    private void addDateSeparator(String label) {
        JPanel sep = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sep.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(FONT_SMALL);
        l.setForeground(TEXT_MUTED);
        sep.add(l);
        chatPanel.add(sep);
        chatPanel.add(Box.createVerticalStrut(4));
    }

    // ── Send Logic ─────────────────────────────────────────────────────────────
    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        inputField.setText("");
        addUserMessage(text);
        updateMessageCount();

        // Disable input while processing
        sendButton.setEnabled(false);
        inputField.setEnabled(false);
        statusLabel.setText("Typing...");
        statusLabel.setForeground(ACCENT);

        // Simulate thinking delay
        Timer timer = new Timer(400 + (int)(Math.random() * 300), e -> {
            String response = normalizeDisplayText(engine.processInput(text));
            addBotMessage(response);
            updateMessageCount();
            sendButton.setEnabled(true);
            inputField.setEnabled(true);
            inputField.requestFocus();
            statusLabel.setText("Online");
            statusLabel.setForeground(ACCENT_GREEN);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showWelcomeMessage() {
        addDateSeparator("Today");
        addBotMessage(normalizeDisplayText(engine.getWelcomeMessage()));
    }

    private void clearChat() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Clear the entire chat history?", "Clear Chat",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            chatPanel.removeAll();
            engine.clearHistory();
            messageCountLabel.setText("0 messages");
            showWelcomeMessage();
        }
    }

    private void updateMessageCount() {
        int count = engine.getMessageCount();
        messageCountLabel.setText(count + " message" + (count != 1 ? "s" : ""));
    }

    private void refreshChat() {
        chatPanel.revalidate();
        chatPanel.repaint();
        SwingUtilities.invokeLater(() -> {
            JScrollBar sb = scrollPane.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });
    }

    // ── Helpers ────────────────────────────────────────────────────────────────
    private JButton createIconButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_SMALL);
        btn.setBackground(bg);
        btn.setForeground(TEXT_MUTED);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        return btn;
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BOLD);
        btn.setBackground(SEND_BTN);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 44));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    private JButton createChipButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.setBackground(new Color(45, 45, 70));
        btn.setForeground(ACCENT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 80, 120), 1, true),
            BorderFactory.createEmptyBorder(3, 10, 3, 10)
        ));
        btn.addActionListener(e -> {
            inputField.setText(label);
            sendMessage();
        });
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(55, 55, 85)); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(new Color(45, 45, 70)); }
        });
        return btn;
    }

    private void setupKeyBindings() {
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
    }
}
