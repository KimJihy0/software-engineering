public class CaesarCipher {

    private char encryptCharacter(char character) {
        return shift(character, 26 - 3);
    }

    private char decryptCharacter(char character) {
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

    public int encrypt(char[] input, int inputOffset, int inputLen, char[] output, int outputOffset) {
        int result = inputLen;
        while (inputLen-- > 0) {
            output[outputOffset++] = encryptCharacter(input[inputOffset++]);
        }
        return result;
    }

    public int decrypt(char[] input, int inputOffset, int inputLen, char[] output, int outputOffset) {
        int result = inputLen;
        while (inputLen-- > 0) {
            output[outputOffset++] = decryptCharacter(input[inputOffset++]);
        }
        return result;
    }
}
