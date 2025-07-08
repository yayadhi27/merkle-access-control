package org.example;

import java.util.*;

public class MerkleCSVTest {
    public static void main(String[] args) throws Exception {
        // Load users from CSV
        String filePath = "src/main/resources/merkleUsers.csv";
        List<User> allUsers = CSVreader.loadUsersFromCSV(filePath);

        // Filter active subscriptions only
        List<String> activeUserTokens = new ArrayList<>();
        for (User user : allUsers) {
            if (user.isActive) {
                activeUserTokens.add(MerkleTree.hash(user.toString()));
            }
        }

        if (activeUserTokens.isEmpty()) {
            System.out.println("No active users found.");
            return;
        }

        // Build Merkle Root with only active users
        String root = MerkleTree.buildMerkleRoot(activeUserTokens);
        System.out.println("Merkle Root (active users only): " + root);

        // Example: generate proof for first active user
        String testUser = "user1|2025-07-01";
        String testHash = MerkleTree.hash(testUser);
        int testIndex = activeUserTokens.indexOf(testHash);

        if (testIndex == -1) {
            System.out.println("Test user not active or not found.");
            return;
        }

        List<ProofNode> proof = MerkleTree.generateProof(activeUserTokens, testIndex);
        boolean valid = MerkleTree.verifyProof(testHash, proof, root);
        System.out.println("Is user proof valid? " + valid);
        System.out.println("User can access video");
    }
}

