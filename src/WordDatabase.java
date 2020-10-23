import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordDatabase {
    List<String> wordList = new ArrayList<String>();
    private  String anoWord;
    private String setWord;

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

    public String checkString(String input, WordDatabase wd) {
        String word = wd.getWord();
        System.out.println(word);
        if (!word.contains("?")) {
            anoWord = "?".repeat(word.length());
        }

        if (input.equals(word)) {
            return word;
        } else if (word.contains(input)) {
            int index = word.indexOf(input);
            char[] inputChar = input.toCharArray();
            StringBuilder newString = new StringBuilder(anoWord);
            newString.setCharAt(index, inputChar[0]);
            System.out.println(newString);
            return newString.toString();
        }
        return word;
    }

    public void setString(String setWord) {
        this.setWord = setWord;
    }

    public String getWord () {
        return setWord;
    }
}
