import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class OnlineVotingSystem extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField ageField;
    private ButtonGroup partyGroup;
    private static Map<String, Integer> votes;

    public OnlineVotingSystem() {
        setTitle("Online Voting System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));
        votes = new HashMap<>();
        votes.put("Candidate A", 0);
        votes.put("Candidate B", 0);
        votes.put("Candidate C", 0);
        votes.put("Candidate D", 0);
        add(new JLabel("Enter Name:"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("Enter Age:"));
        ageField = new JTextField();
        add(ageField);
        add(new JLabel("Enter Phone:"));
        phoneField = new JTextField();
        add(phoneField);
        add(new JLabel("Cast Your Vote Here:"));
        partyGroup = new ButtonGroup();
        JRadioButton candidateA = new JRadioButton("Candidate A");
        partyGroup.add(candidateA);
        add(candidateA);
        JRadioButton candidateB = new JRadioButton("Candidate B");
        partyGroup.add(candidateB);
        add(candidateB);
        JRadioButton candidateC = new JRadioButton("Candidate C");
        partyGroup.add(candidateC);
        add(candidateC);
        JRadioButton candidateD = new JRadioButton("Candidate D");
        partyGroup.add(candidateD);
        add(candidateD);
        JButton submitButton = new JButton("Submit Your Vote");
        submitButton.addActionListener(new SubmitVoteListener());
        add(submitButton);
        JButton checkResultsButton = new JButton("Check Results");
        checkResultsButton.addActionListener(new CheckResultsListener());
        add(checkResultsButton);
    }

    private class SubmitVoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String age = ageField.getText();
            String selectedParty = null;
            for (java.util.Enumeration<AbstractButton> buttons = partyGroup.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selectedParty = button.getText();
                    break;
                }
            }
            if (name.isEmpty() || phone.isEmpty() || age.isEmpty() || selectedParty == null) {
                JOptionPane.showMessageDialog(OnlineVotingSystem.this, "Please fill in all fields and select a candidate.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int ageInt = Integer.parseInt(age);
                if (ageInt < 18) {
                    JOptionPane.showMessageDialog(OnlineVotingSystem.this, "You must be at least 18 years old to vote.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(OnlineVotingSystem.this, "Invalid age.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            votes.put(selectedParty, votes.get(selectedParty) + 1);
            JOptionPane.showMessageDialog(OnlineVotingSystem.this, "Vote submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class CheckResultsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder results = new StringBuilder();
            results.append("Candidate A: ").append(votes.get("Candidate A")).append("\n");
            results.append("Candidate B: ").append(votes.get("Candidate B")).append("\n");
            results.append("Candidate C: ").append(votes.get("Candidate C")).append("\n");
            results.append("Candidate D: ").append(votes.get("Candidate D")).append("\n");
            String leadingParty = "";
            int maxVotes = 0;
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                if (entry.getValue() > maxVotes) {
                    maxVotes = entry.getValue();
                    leadingParty = entry.getKey();
                } else if (entry.getValue() == maxVotes) {
                    leadingParty = "Tie";
                }
            }
            if (!leadingParty.equals("Tie")) {
                results.append(leadingParty).append(" has a lead.");
            } else {
                results.append("It's a tie.");
            }
            JOptionPane.showMessageDialog(OnlineVotingSystem.this, results.toString(), "Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            OnlineVotingSystem OnlinevotingSystem = new OnlineVotingSystem();
            OnlinevotingSystem.setVisible(true);
        });
    }
}