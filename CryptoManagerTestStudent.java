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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CryptoManagerTestStudent {

    @Test
    public void testEmptyPFKeyBuild() {
        String key = "";
        String[][] expectedMatrix = {
                {"A", "B", "C", "D", "E", "F", "G", "H"},
                {"I", "J", "K", "L", "M", "N", "O", "P"},
                {"Q", "R", "S", "T", "U", "V", "W", "X"},
                {"Y", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(expectedMatrix, CryptoManager.generatePlayfairKey(key));
    }

    @Test
    public void testValidPFKeyBuild() {
        String key = "MONTGOMERY";
        String[][] expectedMatrix = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(expectedMatrix, CryptoManager.generatePlayfairKey(key));
    }

    @Test
    public void testRepeatedPFKeyBuild() {
        String key = "AAAAAAAAAAAAAAAAAAAAA";
        String[][] expectedMatrix = {
                {"A", "B", "C", "D", "E", "F", "G", "H"},
                {"I", "J", "K", "L", "M", "N", "O", "P"},
                {"Q", "R", "S", "T", "U", "V", "W", "X"},
                {"Y", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(expectedMatrix, CryptoManager.generatePlayfairKey(key));
    }

    @Test
    public void testLargePFKeyBuild() {
        String key = "_^]\\[@?>=<;:/.-,+*)('&%$#\"! 9876543210ZYXWVUTSRQPONMLKJIHGFEDCBA";
        String[][] expectedMatrix = {
                {"_", "^", "]", "\\", "[", "@", "?", ">"},
                {"=", "<", ";", ":", "/", ".", "-", ","},
                {"+", "*", ")", "(", "'", "&", "%", "$"},
                {"#", "\"", "!", " ", "9", "8", "7", "6"},
                {"5", "4", "3", "2", "1", "0", "Z", "Y"},
                {"X", "W", "V", "U", "T", "S", "R", "Q"},
                {"P", "O", "N", "M", "L", "K", "J", "I"},
                {"H", "G", "F", "E", "D", "C", "B", "A"}
        };
        assertArrayEquals(expectedMatrix, CryptoManager.generatePlayfairKey(key));
    }

    @Test
    public void testExpectedArraySizeBuild() {
        String key = "";
        int expectedRows = 8;
        int expectedColumns = 8;
        String[][] matrix = CryptoManager.generatePlayfairKey(key);
        assertEquals(expectedRows, matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            assertEquals(expectedColumns, matrix[i].length, "Error in row " + i);
        }
    }

    @Test
    public void testEmptyDigraphBuild() {
        String text = "";
        ArrayList<String> test = new ArrayList<>();
        assertEquals(test, CryptoManager.generateDigraphs(text));
    }

    @Test
    public void testOddDigraphsBuild() {
        String text = "DIGRAPH PLS";
        ArrayList<String> test = new ArrayList<>(List.of("DI", "GR", "AP", "H ", "PL", "SX"));
        assertEquals(test, CryptoManager.generateDigraphs(text));
    }

    @Test
    public void testEvenDigraphsBuild() {
        String text = "DIGRAPH PLS!";
        ArrayList<String> test = new ArrayList<>(List.of("DI", "GR", "AP", "H ", "PL", "S!"));
        assertEquals(test, CryptoManager.generateDigraphs(text));
    }

    @Test
    public void testSingleCharDigraphsBuild() {
        String text = "F";
        ArrayList<String> test = new ArrayList<>(List.of("FX"));
        assertEquals(test, CryptoManager.generateDigraphs(text));
    }

    @Test
    public void testCoordinatesM () {
        char testChar = 'M';
        int[] coordinates = new int[]{0,0};
        String[][] key = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(coordinates, CryptoManager.findCoordinates(testChar, key));
    }

    @Test
    public void testCoordinatesE () {
        char testChar = 'E';
        int[] coordinates = new int[]{0,5};
        String[][] key = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(coordinates, CryptoManager.findCoordinates(testChar, key));
    }

    @Test
    public void testCoordinatesD () {
        char testChar = 'D';
        int[] coordinates = new int[]{1,3};
        String[][] key = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(coordinates, CryptoManager.findCoordinates(testChar, key));
    }

    @Test
    public void testCoordinatesSeven () {
        char testChar = '7';
        int[] coordinates = new int[]{4,1};
        String[][] key = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(coordinates, CryptoManager.findCoordinates(testChar, key));
    }

    @Test
    public void testCoordinatesExclamation () {
        char testChar = '!';
        int[] coordinates = new int[]{4,5};
        String[][] key = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(coordinates, CryptoManager.findCoordinates(testChar, key));
    }

    @Test
    public void testCoordinatesUnderscore () {
        char testChar = '_';
        int[] coordinates = new int[]{7,7};
        String[][] key = {
                {"M", "O", "N", "T", "G", "E", "R", "Y"},
                {"A", "B", "C", "D", "F", "H", "I", "J"},
                {"K", "L", "P", "Q", "S", "U", "V", "W"},
                {"X", "Z", "0", "1", "2", "3", "4", "5"},
                {"6", "7", "8", "9", " ", "!", "\"", "#"},
                {"$", "%", "&", "'", "(", ")", "*", "+"},
                {",", "-", ".", "/", ":", ";", "<", "="},
                {">", "?", "@", "[", "\\", "]", "^", "_"}
        };
        assertArrayEquals(coordinates, CryptoManager.findCoordinates(testChar, key));
    }

    @Test
    public void testPlayfairEncryption () {
        String key = "COMPUTERSCIENCE";
        String plainText = "QUALCOMM";
        String expected = "2BL1OMPP";
        assertEquals(expected, CryptoManager.playfairEncryption(plainText, key));
    }

    @Test
    public void testPlayfairEncryptionSameRow () {
        String key = "COMPUTERSCIENCE";
        String plainText = "CO";
        String expected = "OM";
        assertEquals(expected, CryptoManager.playfairEncryption(plainText, key));
    }

    @Test
    public void testPlayfairEncryptionSameColumn () {
        String key = "COMPUTERSCIENCE";
        String plaintext = "QU";
        String expected = "2B";
        assertEquals(expected, CryptoManager.playfairEncryption(plaintext, key));
    }

    @Test
    public void testPlayfairEncryptionRectangle () {
        String key = "COMPUTERSCIENCE";
        String plainText = "XE";
        String expected = "WR";
        assertEquals(expected, CryptoManager.playfairEncryption(plainText, key));
    }

    @Test
    public void testPlayfairEncryptionRowWraparound () {
        String key = "COMPUTERSCIENCE";
        String plainText = "RC";
        String expected = "CO";
        assertEquals(expected, CryptoManager.playfairEncryption(plainText, key));
    }

    @Test
    public void testPlayfairEncryptionColumnWraparound () {
        String key = "COMPUTERSCIENCE";
        String plainText = "_R";
        String expected = "RG";
        assertEquals(expected, CryptoManager.playfairEncryption(plainText, key));
    }

    @Test
    public void testPlayfairDecryption () {
        String key = "COMPUTERSCIENCE";
        String encrypted = "2BL1OMPP";
        String plainText = "QUALCOMM";
        assertEquals(plainText, CryptoManager.playfairDecryption(encrypted, key));
    }

    @Test
    public void testPlayfairDecryptionSameRow () {
        String key = "COMPUTERSCIENCE";
        String encrypted = "OM";
        String plainText = "CO";
        assertEquals(plainText, CryptoManager.playfairDecryption(encrypted, key));
    }

    @Test
    public void testPlayfairDecryptionSameColumn () {
        String key = "COMPUTERSCIENCE";
        String encrypted = "2B";
        String plainText = "QU";
        assertEquals(plainText, CryptoManager.playfairDecryption(encrypted, key));
    }

    @Test
    public void testPlayfairDecryptionRectangle () {
        String key = "COMPUTERSCIENCE";
        String encrypted = "WR";
        String plainText = "XE";
        assertEquals(plainText, CryptoManager.playfairDecryption(encrypted, key));
    }

    @Test
    public void testPlayfairDecryptionRowWraparound () {
        String key = "COMPUTERSCIENCE";
        String encrypted = "CO";
        String plainText = "RC";
        assertEquals(plainText, CryptoManager.playfairDecryption(encrypted, key));
    }

    @Test
    public void testPlayfairDecryptionColumnWraparound () {
        String key = "COMPUTERSCIENCE";
        String encrypted = "RG";
        String plainText = "_R";
        assertEquals(plainText, CryptoManager.playfairDecryption(encrypted, key));
    }

    @Test
    public void testAlphabet64Conversion () {
        assertEquals(0, CryptoManager.toAlphabet64('A'));
        assertEquals(24, CryptoManager.toAlphabet64('Y'));
        assertEquals(27, CryptoManager.toAlphabet64('1'));
        assertEquals(36, CryptoManager.toAlphabet64(' '));
        assertEquals(63, CryptoManager.toAlphabet64('_'));
    }

    @Test
    public void testASCIIConversion () {
        assertEquals('A', CryptoManager.toASCII(0));
        assertEquals('Y', CryptoManager.toASCII(24));
        assertEquals('1', CryptoManager.toASCII(27));
        assertEquals(' ', CryptoManager.toASCII(36));
        assertEquals('_', CryptoManager.toASCII(63));
    }

    @Test
    public void testLowerCaseStringBounds () {
        assertFalse(CryptoManager.isStringInBounds("hello world"));
    }

    @Test
    public void testUpperCaseStringBounds () {
        assertTrue(CryptoManager.isStringInBounds("HELLO WORLD!!!!"));
    }

    @Test
    public void testInvalidCharactersStringInBounds () {
        assertFalse(CryptoManager.isStringInBounds("`"));
        assertFalse(CryptoManager.isStringInBounds("\n"));
    }

    @Test
    public void testCaeserDecryption () {
        assertEquals("GHI", CryptoManager.caesarDecryption("JKL", 3));
        assertEquals("ABCD", CryptoManager.caesarDecryption("EFGH", 4));
        assertEquals("____", CryptoManager.caesarDecryption("    ", 1));
        assertEquals("ABC", CryptoManager.caesarDecryption("ABC", 64));
    }

    @Test
    public void testCaeserEncryption () {
        assertEquals("JKL", CryptoManager.caesarEncryption("GHI", 3));
        assertEquals("EFGH", CryptoManager.caesarEncryption("ABCD", 4));
        assertEquals("    ", CryptoManager.caesarEncryption("____", 1));
        assertEquals("ABC", CryptoManager.caesarEncryption("ABC", 64));
        assertEquals("BCD", CryptoManager.caesarEncryption("ABC", 65));
        assertEquals("@AB", CryptoManager.caesarEncryption("ABC", -1));
    }

    @Test
    public void testVigenereEncryption() {
        assertEquals("!\"#", CryptoManager.vigenereEncryption("ABC", "!"));
        assertEquals(" !\"", CryptoManager.vigenereEncryption("ABC", " "));
        assertEquals(" \"$", CryptoManager.vigenereEncryption("ABC", " !\""));
    }

    @Test
    public void testVigenereDecryption() {
        assertEquals("ABC", CryptoManager.vigenereDecryption("!\"#", "!"));
        assertEquals("ABC", CryptoManager.vigenereDecryption(" !\"", " "));
        assertEquals("ABC", CryptoManager.vigenereDecryption(" \"$", " !\""));
    }

    @Test
    public void testVigenereRepeatedKey() {
        assertEquals("!#%!#%", CryptoManager.vigenereEncryption("ABCABC", "!\"#"));
        assertEquals("ABCABC", CryptoManager.vigenereDecryption("!#%!#%", "!\"#"));
    }

    @Test
    public void testVigenereEmptyKey () {
        assertEquals("An empty key is not allowed for the Vigenere Encryption algorithm, please try again.", CryptoManager.vigenereEncryption("ABC", ""));
        assertEquals("An empty key is not allowed for the Vigenere Encryption algorithm, please try again.", CryptoManager.vigenereDecryption("ABC", ""));
    }

}
