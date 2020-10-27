import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordDatabase {
    List<String> wordList = new ArrayList<String>();
    private  String anoWord = "";
    private String setWord;
    private StringBuilder guessWord;
    private String guessedChar = "";

    public WordDatabase() {
        try {
            //Creates a word list out of the text document ordliste
            Scanner sc = new Scanner(new File("src/ordliste.txt"));
            while (sc.hasNextLine()) {
                wordList.add(sc.nextLine());
            }
            System.out.println(wordList);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    //Choose a random word from the wordlist
    public String randomWord() {
        Random generator = new Random();
        //Chooses a random number between 0 and the size of the wordlist
        int randomIndex = generator.nextInt(wordList.size());
        //Return the random word from the list
        return wordList.get(randomIndex);
    }

    public String checkString(String input, WordDatabase wd) {
        String word = wd.getWord();
        System.out.println(word);
        //If the anonymous word isn't set to the word selected
        //set anonymous word to as many questionmarks as the lenght of the word
        //set guessed word to anonymous word
        if (!anoWord.contains("?"))  {
            anoWord = "?".repeat(word.length());
            guessWord = new StringBuilder(anoWord);
        }

        //if (input.equals(word)) {
            //return word;
         if (word.contains(input)) {
            //Convert inputed string to character array
            char[] inputChar = input.toCharArray();
            StringBuilder newString = new StringBuilder(anoWord);
            //Loop through the word and se where the input matches the words character
            //Set guess word character to the character that is guessed
            for(int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == inputChar[0]) {
                    newString.setCharAt(i, inputChar[0]);
                    guessWord.setCharAt(i, inputChar[0]);
                }
            }
            System.out.println(guessWord);
            return newString.toString();
         } else if (!word.contains(input)){
            guessedChar += input;
            System.out.println(guessedChar);
         }
         //System.out.println(newString);
         System.out.println(guessWord);
         return word;
    }

    public void setString(String setWord) {
        this.setWord = setWord;
    }

    public String getWord () {
        return setWord;
    }


}
