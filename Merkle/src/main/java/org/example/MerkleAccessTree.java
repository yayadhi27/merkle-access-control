package org.example;


import java.util.*;

public class MerkleAccessTree {
    public static void main(String[] args) throws Exception {
        List<String> users = Arrays.asList(
                "user1|2025-07-08T15:00",
                "user2|2025-07-08T16:00",
                "user3|2025-07-08T15:00"
        );

        List<String> leaves = new ArrayList<>();
        for (String user : users) {
            leaves.add(MerkleTree.hash(user));
        }

        String root = MerkleTree.buildMerkleRoot(leaves);
        System.out.println("Merkle Root: " + root);

        int index = 1; // user2
        String leaf = leaves.get(index);
        List<ProofNode> proof = MerkleTree.generateProof(leaves, index);

        System.out.println("Proof:");
        for (ProofNode node : proof) {
            System.out.println("Hash: " + node.hash + " | isLeft: " + node.isLeft);
        }

        boolean isValid = MerkleTree.verifyProof(leaf, proof, root);
        System.out.println("Is proof valid? " + isValid);
    }
}
