package org.example;

import java.util.*;

public class MerkleAccessRevokedTest {
    public static void main(String[] args) throws Exception {
        // STEP 1: User list with access
        List<String> usersWithAccess = Arrays.asList(
                "user1|2025-07-08T15:00",
                "user2|2025-07-08T15:00",  // user2 will be revoked later
                "user3|2025-07-08T15:00"
        );

        // Hash all user tokens
        List<String> leaves = new ArrayList<>();
        for (String user : usersWithAccess) {
            leaves.add(MerkleTree.hash(user));
        }

        // Generate Merkle Root
        String oldRoot = MerkleTree.buildMerkleRoot(leaves);
        System.out.println("Initial Merkle Root (before revoking access): " + oldRoot);

        // Generate proof for user2 (index 1)
        int index = 1;
        String leaf = leaves.get(index);
        List<ProofNode> proof = MerkleTree.generateProof(leaves, index);

        // Verify proof is valid with original root
        boolean initialProofValid = MerkleTree.verifyProof(leaf, proof, oldRoot);
        System.out.println("✅ Step 1 - User2 valid in original tree? " + initialProofValid);

        // STEP 2: Revoke access for user2
        List<String> usersAfterRevocation = Arrays.asList(
                "user1|2025-07-08T15:00",
                "user3|2025-07-08T15:00"
        );

        List<String> leavesAfterRevocation = new ArrayList<>();
        for (String user : usersAfterRevocation) {
            leavesAfterRevocation.add(MerkleTree.hash(user));
        }

        // New Merkle root without user2
        String newRoot = MerkleTree.buildMerkleRoot(leavesAfterRevocation);
        System.out.println("New Merkle Root (after revoking access): " + newRoot);

        // Try to verify old proof of user2 with new root
        boolean revokedProofValid = MerkleTree.verifyProof(leaf, proof, newRoot);
        System.out.println("🚫 Step 2 - Is old proof valid after revocation? " + revokedProofValid);
    }
}

