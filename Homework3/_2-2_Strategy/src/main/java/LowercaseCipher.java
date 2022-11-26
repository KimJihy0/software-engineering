public class LowercaseCipher implements Cipher {

    @Override
    public char encryptCharacter(char character) {
        if (Character.isUpperCase(character)) {
            return Character.toLowerCase(character);
        } else {
            return character;
        }
    }

    @Override
    public char decryptCharacter(char character) {
        if (Character.isLowerCase(character)) {
            return Character.toUpperCase(character);
        } else {
            return character;
        }
    }
}
