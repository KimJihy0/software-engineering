import java.io.*;
import java.nio.charset.StandardCharsets;

public class LowercaseStrategyTester {
    public static void main(String[] args) throws IOException {
        String title = "strategy-caesar-test.txt";
        String lyrics = """
                밖을 내다봐, 마음이 없어.
                It's all about the money and the sex.
                어떤이는 눈 풀린 밤을 보내고 노리는 홀인원
                됐어 내겐 안 어울리는 걸.
                누군 날 놀리는걸, 넌 너무 어려.
                Fuck that shit man, 난 배운대로 해.
                누군 날 놀리는걸, 넌 너무 어리다고
                Fuck that shit man.
                """;

        File file = new File(title);

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        EncryptWriter writer = new EncryptWriter(osw, new LowercaseCipher());

        writer.write(lyrics);
        writer.close();

        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        DecryptReader reader = new DecryptReader(isr, new LowercaseCipher());

        int c;
        while ((c = reader.read()) != -1) {
            System.out.print((char) c);
        }
        reader.close();
    }
}
