import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EncryptWriter extends FilterWriter {

    // the cipher engine to use to process stream data
    private final Cipher cipher;

    /* the buffer holding one char of incoming data */
    private final char[] ibuffer = new char[1];

    /* the buffer holding data ready to be written out */
    private char[] obuffer = null;

    // stream status
    private boolean closed = false;

    private void ensureCapacity(int inLen) {
        if (obuffer == null || obuffer.length < inLen) {
            obuffer = new char[inLen];
        }
    }

    public EncryptWriter(Writer w, Cipher c) {
        super(w);
        out = w;
        cipher = c;
    }

    public EncryptWriter(Writer w) {
        super(w);
        out = w;
        cipher = new NullCipher();
    }

    @Override
    public void write(int c) throws IOException {
        ibuffer[0] = (char) c;
        ensureCapacity(1);
        int ostored = cipher.encrypt(ibuffer, 0, 1, obuffer, 0);
        if (ostored > 0) {
            out.write(obuffer, 0, ostored);
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        ensureCapacity(len);
        int ostored = cipher.encrypt(cbuf, off, len, obuffer, 0);
        if (ostored > 0) {
            out.write(obuffer, 0, ostored);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        ensureCapacity(len);
        int ostored = cipher.encrypt(str.toCharArray(), off, len, obuffer, 0);
        if (ostored > 0) {
            out.write(obuffer, 0, ostored);
        }
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        closed = true;
        ensureCapacity(0);
        obuffer = null;
        flush();
        out.close();
    }
}
