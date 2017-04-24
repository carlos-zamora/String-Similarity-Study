import java.util.*;
import java.lang.String;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.io.*;

public class Qgram {
  
  public String comp;
  
  
  public Qgram(int num, String comp) {
    
    String[] word = this.twoGrams(this.usefulLetters(comp));
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = "\n";
    try {
      br = new BufferedReader(new FileReader("IMDB_dataset.csv"));
      int i = 0;
      while ((line = br.readLine()) != null && i < num) {
        StringBuilder sb = new StringBuilder();
        sb.append(line.split(csvSplitBy));
        String[] gr = this.twoGrams(this.usefulLetters(sb.toString()));
        this.compare(gr, word, sb.toString());
        i ++;
      }
    }
    
    catch (FileNotFoundException e) {
    }
    catch (IOException e) {
    }
    
  }
  
  //takes out all spaces and special characters and makes everything lower case
  public String usefulLetters(String s) {
    
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < s.length(); i ++) {
      if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
        sb.append(s.charAt(i));
      }
      else if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
        sb.append(Character.toLowerCase(s.charAt(i)));
      }
    }
    
    return sb.toString();
  }
  
  //separates out into 2-grams
  public String[] twoGrams(String s) {
    
    String[] gramString = new String[s.length() - 1];
    
    for (int i = 0; i < s.length(); i ++) {
      String temp = new StringBuilder().append(s.charAt(i)).append(s.charAt(i + 1)).toString();
      gramString[i] = temp;
    }
    
    return gramString;
  }
  
  public void compare(String[] comp, String[] word, String s) {
    
    boolean same = false;
    int ed = 0;
    int i = 0;
    while (i < word.length) {
      for (int j = 0; j < comp.length; j ++) {
        if (word[i].equals(comp[j])) {
          same = true; 
        }
      }
      if (!same) {
        ed ++; 
      }
      
      i ++;
    }
    
    if (ed <= 5) {
      System.out.println(s); 
    }
    
  }
  
  public static void main(String[] args) {
    
    Qgram gram = new Qgram(100, "John Travolta");
    
    
  }
}



