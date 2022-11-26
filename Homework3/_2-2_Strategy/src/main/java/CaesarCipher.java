public class CaesarCipher implements Cipher {

    @Override
    public char encryptCharacter(char character) {
        return shift(character, 26 - 3);
    }

    @Override
    public char decryptCharacter(char character) {
        return shift(character, 3);
    }

    private char shift(char character, int shiftOffset) {
        if (Character.isLowerCase(character)) {
            int originalAlphabetPosition = character - 'a';
            int newAlphabetPosition = (originalAlphabetPosition + shiftOffset) % 26;
            return (char) ('a' + newAlphabetPosition);
        } else if (Character.isUpperCase(character)) {
            int originalAlphabetPosition = character - 'A';
            int newAlphabetPosition = (originalAlphabetPosition + shiftOffset) % 26;
            return (char) ('A' + newAlphabetPosition);
        } else {
            return character;
        }
    }
}
