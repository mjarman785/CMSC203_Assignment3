/*
 * Class: CMSC203
 * Instructor: Khandan Monshi
 * Description: Assignment 3: encrypt and decrypt using three different methods
 * Due: 10/26/2026
 * Platform/compiler: Win11 26H2 build 26430.9767 / openjdk 21.0.1 (Microsoft provided)
 * I pledge that I have completed the programming assignment independently. I have not copied the code from a student or
 * any source. I have not given my code to any student.
 * Print your Name here: Michael Jarman
 */

import java.util.ArrayList;

/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
		if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
			return "The selected string is not in bounds, Try again.";
		}

    	int length = plainText.length();
    	int keyLength = key.length();
    	String expandedKey = "";
    	String encryptedText = "";
		int plainIndex, keyIndex, encryptedIndex;

		if (key.isEmpty()) {
			return "An empty key is not allowed for the Vigenere Encryption algorithm, please try again.";
		}

		for (int i = 0; i < length;) {
			expandedKey += key;
			i += keyLength;
		}

    	for (int j = 0; j < length; j++) {
			plainIndex = toAlphabet64(plainText.charAt(j));
			keyIndex = toAlphabet64(expandedKey.charAt(j));
			encryptedIndex = (plainIndex + keyIndex) % RANGE;
			encryptedText += toASCII(encryptedIndex);
    	}
    	return encryptedText;
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key) {
		if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
			return "The selected string is not in bounds, Try again.";
		}

    	int length = encryptedText.length();
    	int keyLength = key.length();
    	String expandedKey = "";
    	String decryptedText = "";
		int encryptedIndex, keyIndex, decryptedIndex;

		if (key.isEmpty()) {
			return "An empty key is not allowed for the Vigenere Encryption algorithm, please try again.";
		}

    	for (int i = 0; i < length;) {
    		expandedKey += key;
    		i += keyLength;
    	}
    	for (int j = 0; j < length; j++) {
			encryptedIndex = toAlphabet64(encryptedText.charAt(j));
			keyIndex = toAlphabet64(expandedKey.charAt(j));
			decryptedIndex = (encryptedIndex - keyIndex + RANGE) % RANGE;
			decryptedText += toASCII(decryptedIndex);
    	}
    	return decryptedText;
    }

	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    public static String playfairEncryption(String plainText, String key) {
		if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
			return "The selected string is not in bounds, Try again.";
		}

        String[][] pfKey = generatePlayfairKey(key);
		ArrayList<String> digraphs = generateDigraphs(plainText);
		String encryptedText = "";
		for (int i = 0; i < digraphs.size(); i++) {
			String digraph = digraphs.get(i);
			int[] coords1 = findCoordinates(digraph.charAt(0), pfKey);
			int[] coords2 = findCoordinates(digraph.charAt(1), pfKey);

			// check for existence within the same row
			if (coords1[0] == coords2[0]) {
				coords1[1] = (coords1[1] + 1) % 8;
				coords2[1] = (coords2[1] + 1) % 8;
				encryptedText += pfKey[coords1[0]][coords1[1]];
				encryptedText += pfKey[coords2[0]][coords2[1]];
			} else if (coords1[1] == coords2[1]) { // check for existence within the same column
				coords1[0] = (coords1[0] + 1) % 8;
				coords2[0] = (coords2[0] + 1) % 8;
				encryptedText += pfKey[coords1[0]][coords1[1]];
				encryptedText += pfKey[coords2[0]][coords2[1]];
			} else { // doesn't match either case, so assume rectangular
				int temp = coords1[1];
				coords1[1] = coords2[1];
				coords2[1] = temp;
				encryptedText += pfKey[coords1[0]][coords1[1]];
				encryptedText += pfKey[coords2[0]][coords2[1]];
			}
		}
		return encryptedText;
    }

    public static String playfairDecryption(String encryptedText, String key) {
		if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
			return "The selected string is not in bounds, Try again.";
		}

		String[][] pfKey = generatePlayfairKey(key);
		ArrayList<String> digraphs = generateDigraphs(encryptedText);
		String decryptedText = "";
		for (int i = 0; i < digraphs.size(); i++) {
			String digraph = digraphs.get(i);
			int[] coords1 = findCoordinates(digraph.charAt(0), pfKey);
			int[] coords2 = findCoordinates(digraph.charAt(1), pfKey);

			// check for existence within the same row
			if (coords1[0] == coords2[0]) {
				coords1[1] = (coords1[1] + 7) % 8;
				coords2[1] = (coords2[1] + 7) % 8;
				decryptedText += pfKey[coords1[0]][coords1[1]];
				decryptedText += pfKey[coords2[0]][coords2[1]];
			} else if (coords1[1] == coords2[1]) { // check for existence within the same column
				coords1[0] = (coords1[0] + 7) % 8;
				coords2[0] = (coords2[0] + 7) % 8;
				decryptedText += pfKey[coords1[0]][coords1[1]];
				decryptedText += pfKey[coords2[0]][coords2[1]];
			} else { // doesn't match either case, so assume rectangular
				int temp = coords1[1];
				coords1[1] = coords2[1];
				coords2[1] = temp;
				decryptedText += pfKey[coords1[0]][coords1[1]];
				decryptedText += pfKey[coords2[0]][coords2[1]];
			}
		}
		if (decryptedText.endsWith("X")) {
			decryptedText = decryptedText.substring(0, decryptedText.length() - 1);
		}
		return decryptedText;
    }

	// Build 8x8 key array from provided key
	public static String[][] generatePlayfairKey (String key) {
		String[][] keyMatrix = new String[8][8];
        ArrayList<String> usedCharacters = new ArrayList<>();
		String alphabet = "";
		boolean alphabetPrepared = false;

		for (int i = 0; i < keyMatrix.length; i++) {
			for (int j = 0; j < keyMatrix[i].length; j++) {
				while (!key.isEmpty() && usedCharacters.contains("" + key.charAt(0))) { // if already used characters are present, we discard them
					key = key.substring(1);
				}
				if (!key.isEmpty()) {
					keyMatrix[i][j] = "" + key.charAt(0);
					usedCharacters.add("" + key.charAt(0)); // add to used chars list
					key = key.substring(1); // discard processed key segments
				} else { // no more key left to process, so we move onto the alphabet to fill the rest of the matrix.
					if (!alphabetPrepared) { // we only want to do this once
						for (int k = 0; (k < ALPHABET64.length()); k++) {
							if (!usedCharacters.contains("" + ALPHABET64.charAt(k))) {
								alphabet += ALPHABET64.charAt(k); // create new alphabet as we process it
							}
						}
						alphabetPrepared = true; // and set to true once finished
					}
					keyMatrix[i][j] = "" + alphabet.charAt(0);
					alphabet = alphabet.substring(1); // and now we discard the "new" and processed alphabet chars
				}
			}
		}

		return keyMatrix;
	}

	// Build StringList digraph
	public static ArrayList<String> generateDigraphs (String text) {
		ArrayList<String> digraphs = new ArrayList<>();
		if (text.length() % 2 == 1) { // if odd number, add an X to make it even
			text += "X";
		}
		int textLength = text.length();
		for (int i = 0; i < textLength; i+=2) {
			digraphs.add(text.charAt(0) + "" + text.charAt(1));
			text = text.substring(2);
		}
		return digraphs;
	}

	// Find coordinates
	public static int[] findCoordinates (char character, String[][] key) {
		int[] coordinates = new int[2];
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key[i].length; j++) {
				if (character == key[i][j].charAt(0)) {
					coordinates[0] = i;
					coordinates[1] = j;
					return coordinates;
				}
			}
		}
		return null;
	}

	public static int toAlphabet64 (char input) {
		return ALPHABET64.indexOf("" + input);
	}

	public static char toASCII (int input) {
		return ALPHABET64.charAt(input);
	}

    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key) {
		if (!isStringInBounds(plainText)) {
			return "The selected string is not in bounds, Try again.";
		}

		key = Math.floorMod(key, RANGE);

    	int length = plainText.length();
    	String encryptedText = "";
    	int encryptedChar;
    	for (int i = 0; i < length; i++) {
    		encryptedChar = plainText.charAt(i) + key;
    		if (encryptedChar > UPPER_RANGE) {
    			encryptedChar -= RANGE;
    		}
    		encryptedText += (char) encryptedChar;
    	}
    	return encryptedText;
    }

    // Caesar Decryption
    public static String caesarDecryption(String encryptedText, int key) {
		if (!isStringInBounds(encryptedText)) {
			return "The selected string is not in bounds, Try again.";
		}

		key = Math.floorMod(key, RANGE);

    	int length = encryptedText.length();
    	String decryptedText = "";
    	int decryptedChar;
    	for (int i = 0; i < length; i++) {
    		decryptedChar  = encryptedText.charAt(i) - key;
    		if (decryptedChar < LOWER_RANGE) {
    			decryptedChar += RANGE;
    		}
    		decryptedText += (char) decryptedChar;
    	}
    	return decryptedText;
    }    

}
