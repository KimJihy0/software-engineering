public class NullCipher implements Cipher {

    @Override
    public char encryptCharacter(char character) {
        return character;
    }

    @Override
    public char decryptCharacter(char character) {
        return character;
    }
}
