public interface Cipher {

    default int encrypt(char[] input, int inputOffset, int inputLen, char[] output, int outputOffset) {
        int result = inputLen;
        while (inputLen-- > 0) {
            output[outputOffset++] = encryptCharacter(input[inputOffset++]);
        }
        return result;
    }

    default int decrypt(char[] input, int inputOffset, int inputLen, char[] output, int outputOffset) {
        int result = inputLen;
        while (inputLen-- > 0) {
            output[outputOffset++] = decryptCharacter(input[inputOffset++]);
        }
        return result;
    }

    char encryptCharacter(char character);

    char decryptCharacter(char character);
}
