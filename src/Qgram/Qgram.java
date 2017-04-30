package Qgram;

import java.lang.String;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.io.*;
import java.util.ArrayList;

public class Qgram {
  
  public String comp; //comparison string
  public ArrayList<String> output = new ArrayList<String>(1); //output list
  
  //constructor: takes file, comparison wstring, and edit distance; calls compare and calls outputReturn
  public Qgram(int num, String comp, String[] file) {
  
    String[] word = this.twoGrams(this.usefulLetters(comp));
    int i = 0;
    while (i < file.length) {
      String[] gr = this.twoGrams(this.usefulLetters(file[i]);
      if (this.compare(gr, word, file[i], num)) {
        output.add(file[i]);
      }
      i ++;
    }
  }
    
    catch (FileNotFoundException e) {
    }
    catch (IOException e) {
    }
    
    this.outputReturn();
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
    
    for (int i = 0; i < s.length() - 1; i ++) {
      String temp = new StringBuilder().append(s.charAt(i)).append(s.charAt(i + 1)).toString();
      gramString[i] = temp;
    }
    
    return gramString;
  }
  
  //compares the 2-grams in the input string to the 2-grams in that particular string in the data set
  public boolean compare(String[] comp, String[] word, String s, int num) {
    
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
      if (ed > num){
       break;
      }
      
      i ++;
    }
    
    if (ed <= num) {
      return true;
    }
    
    else {
      return false;
    }
    
  }
  
  //returns the arraylist of possible strings
  public ArrayList<String> outputReturn() {
   return this.output; 
  }

}
