import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class DecoratorTester {
    public static void main(String[] args) throws IOException {
        String title = "Vibra.txt";
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

        // Component
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        // Decorator1
        EncryptWriter ew = new EncryptWriter(osw);
        // Decorator2
        PrintWriter writer = new PrintWriter(ew);

        writer.println("Vibra");
        writer.printf("%tF\n", new Date());
        writer.write(lyrics);

        writer.close();

        // Component
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        // Decorator1
        DecryptReader dr = new DecryptReader(isr);
        // Decorator2
        LineNumberReader reader = new LineNumberReader(dr);

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(String.format("%02d >> %s\n", reader.getLineNumber(), s));
        }
        System.out.println(sb);

        reader.close();
    }
}
