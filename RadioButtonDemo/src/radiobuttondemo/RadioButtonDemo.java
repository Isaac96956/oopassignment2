package radiobuttondemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class RadioButtonDemo extends JFrame implements ActionListener {
    private JRadioButton birdButton, catButton, dogButton, rabbitButton, pigButton;
    private JLabel imageLabel, messageLabel;
    private ButtonGroup group;

    public RadioButtonDemo() {
        super("RadioButtonDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Message label above image section ---
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setForeground(new Color(0, 102, 204));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Radio buttons panel ---
        JPanel radioPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        birdButton = new JRadioButton("Bird");
        catButton = new JRadioButton("Cat");
        dogButton = new JRadioButton("Dog");
        rabbitButton = new JRadioButton("Rabbit");
        pigButton = new JRadioButton("Pig");

        group = new ButtonGroup();
        group.add(birdButton);
        group.add(catButton);
        group.add(dogButton);
        group.add(rabbitButton);
        group.add(pigButton);

        radioPanel.add(birdButton);
        radioPanel.add(catButton);
        radioPanel.add(dogButton);
        radioPanel.add(rabbitButton);
        radioPanel.add(pigButton);

        birdButton.addActionListener(this);
        catButton.addActionListener(this);
        dogButton.addActionListener(this);
        rabbitButton.addActionListener(this);
        pigButton.addActionListener(this);

        // --- Image label section ---
        imageLabel = new JLabel("No image selected", JLabel.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(250, 250));

        // --- Center panel combining message + image ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(messageLabel, BorderLayout.NORTH);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        add(radioPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        setSize(550, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pet = e.getActionCommand();
        String[] extensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        String imagePath = null;

        // Try to find an existing image file with any supported extension
        for (String ext : extensions) {
            File file = new File("image/" + pet.toLowerCase() + ext);
            if (file.exists()) {
                imagePath = file.getPath();
                break;
            }
        }

        if (imagePath != null) {
            // Load and display image
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            imageLabel.setIcon(icon);
            imageLabel.setText("");
            messageLabel.setForeground(new Color(0, 128, 0));
        } else {
            // Show placeholder if not found
            imageLabel.setIcon(createPlaceholderIcon(pet));
            imageLabel.setText("");
            messageLabel.setText("Image not found for: " + pet + " (showing placeholder)");
            messageLabel.setForeground(Color.RED);
        }
        
        // ADDED: Display selection in message box as required by assignment
        JOptionPane.showMessageDialog(
            this,
            "You selected: " + pet,
            "Pet Selection",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private ImageIcon createPlaceholderIcon(String pet) {
        int width = 200, height = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        g.drawString("No Image", 60, 90);
        g.drawString("for " + pet, 70, 120);
        g.dispose();
        return new ImageIcon(image);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RadioButtonDemo::new);
    }
}