import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordDatabase {
    List<String> wordList = new ArrayList<String>();

    public WordDatabase() {
        try {
            Scanner sc = new Scanner(new File("src/ordliste.txt"));
            while (sc.hasNextLine()) {
                wordList.add(sc.nextLine());
            }
            System.out.println(wordList);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public String randomWord() {
        Random generator = new Random();
        int randomIndex = generator.nextInt(wordList.size());
        return wordList.get(randomIndex);
    }
}
