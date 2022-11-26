import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class StrategyTester {
    public static void main(String[] args) throws IOException {
        String title = "Vibra.txt";
        String test = """
                abcdefghijklmnopqrstuvwxyz
                ABCDEFGHIJKLMNOPQRSTUVWXYZ
                !@#$%^&*()1234567890
                가나다라마바사아자차카타파하
                """;
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
        Cipher cipher;

        cipher = new CaesarCipher();
        cipher = new LowercaseCipher();

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        EncryptWriter ew = new EncryptWriter(osw, cipher);
        PrintWriter writer = new PrintWriter(ew);

        writer.println("Vibra");
        writer.printf("%tF\n", new Date());
        writer.write(lyrics);
        writer.close();

        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        DecryptReader dr = new DecryptReader(isr, cipher);
        LineNumberReader reader = new LineNumberReader(dr);

        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            sb.append(String.format("%02d >> %s\n", reader.getLineNumber(), str));
        }
        System.out.println(sb);
        reader.close();
    }
}
