package org.example;

import java.security.MessageDigest;
import java.util.*;

public class MerkleTree {
    public static String hash(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static String buildMerkleRoot(List<String> leaves) throws Exception {
        if (leaves.isEmpty()) return "";
        List<String> temp = new ArrayList<>(leaves);
        while (temp.size() > 1) {
            List<String> nextLevel = new ArrayList<>();
            for (int i = 0; i < temp.size(); i += 2) {
                String left = temp.get(i);
                String right = (i + 1 < temp.size()) ? temp.get(i + 1) : left;
                nextLevel.add(hash(left + right));
            }
            temp = nextLevel;
        }
        return temp.get(0);
    }

    public static List<ProofNode> generateProof(List<String> leaves, int index) throws Exception {
        List<ProofNode> proof = new ArrayList<>();
        List<String> level = new ArrayList<>(leaves);

        while (level.size() > 1) {
            List<String> nextLevel = new ArrayList<>();
            for (int i = 0; i < level.size(); i += 2) {
                String left = level.get(i);
                String right = (i + 1 < level.size()) ? level.get(i + 1) : left;
                nextLevel.add(hash(left + right));

                if (i == index || i + 1 == index) {
                    boolean isLeft = (i + 1 == index); // sibling is on left if current is right
                    String sibling = isLeft ? left : right;
                    proof.add(new ProofNode(sibling, isLeft));
                    index = nextLevel.size() - 1;
                }
            }
            level = nextLevel;
        }
        return proof;
    }

    public static boolean verifyProof(String leaf, List<ProofNode> proof, String root) throws Exception {
        String currentHash = leaf;
        for (ProofNode node : proof) {
            if (node.isLeft) {
                currentHash = hash(node.hash + currentHash);
            } else {
                currentHash = hash(currentHash + node.hash);
            }
        }
        return currentHash.equals(root);
    }
}
