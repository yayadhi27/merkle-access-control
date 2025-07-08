# Merkle-Based Time-Bound Access Verification

This project demonstrates a Merkle-tree-based system for verifying user access to content, particularly for subscription platforms. Instead of using JWT tokens or API keys, access is verified via **Merkle inclusion proofs**, making it lightweight, tamper-evident, and edge-distributable.

---

## What It Does

- Reads a list of subscribed users from a CSV file.
- Filters out expired or inactive users
- Builds a Merkle tree from the list of valid (active) users
- Generates inclusion proofs for any user
- Verifies if a user is authorized by checking their Merkle proof against the current Merkle root
