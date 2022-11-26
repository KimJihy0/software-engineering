import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class DecryptReader extends FilterReader {

    // the cipher engine to use to process stream data
    private final Cipher cipher;

    /* the buffer holding data that have been read in from the
       underlying stream, but have not been processed by the cipher
       engine. the size 512 chars is somewhat randomly chosen */
    private final char[] ibuffer = new char[512];

    // having reached the end of the underlying character-input stream
    private boolean done = false;

    /* the buffer holding data that have been processed by the cipher
       engine, but have not been read out */
    private char[] obuffer = null;
    // the offset pointing to the next "new" char
    private int ostart = 0;
    // the offset pointing to the last "new" char
    private int ofinish = 0;
    // stream status
    private boolean closed = false;

    private void ensureCapacity(int inLen) {
        if (obuffer == null || obuffer.length < inLen) {
            obuffer = new char[inLen];
        }
        ostart = 0;
        ofinish = 0;
    }

    private int getMoreData() throws IOException {
        if (done) return -1;
        int readin = in.read(ibuffer);

        if (readin == -1) {
            done = true;
            ensureCapacity(0);
            return -1;
        }
        ensureCapacity(readin);
        ofinish = cipher.decrypt(ibuffer, 0, readin, obuffer, ostart);
        return ofinish;
    }

    public DecryptReader(Reader r, Cipher c) {
        super(r);
        in = r;
        cipher = c;
    }

    public DecryptReader(Reader r) {
        super(r);
        in = r;
        cipher = new NullCipher();
    }

    @Override
    public int read() throws IOException {
        if (ostart >= ofinish) {
            int i = 0;
            while (i == 0) i = getMoreData();
            if (i == -1) return -1;
        }
        return ((int) obuffer[ostart++] & 0xffff);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (ostart >= ofinish) {
            int i = 0;
            while (i == 0) i = getMoreData();
            if (i == -1) return -1;
        }
        if (len <= 0) {
            return 0;
        }
        int available = ofinish - ostart;
        if (len < available) available = len;
        if (cbuf != null) {
            System.arraycopy(obuffer, ostart, cbuf, off, available);
        }
        ostart = ostart + available;
        return available;
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        closed = true;
        in.close();
        if (!done) {
            ensureCapacity(0);
        }
        obuffer = null;
    }
}
