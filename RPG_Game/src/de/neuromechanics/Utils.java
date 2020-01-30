package de.neuromechanics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
  public static int parseInt(String number){
    try {
      return Integer.parseInt(number);
    } catch(NumberFormatException e){
      e.printStackTrace();
      return -1;
    }
  }
  public static String loadFileAsString(String path){
	  StringBuilder builder = new StringBuilder();

	  //Get file from resources folder
	  FileReader file = null;
	  try {
	    file = new FileReader(Utils.class.getClass().getResource(path).getFile());
	  } catch (FileNotFoundException e1) {
	    e1.printStackTrace();
	  }
	  if(file != null) {
	    try {
	      BufferedReader br = new BufferedReader(file);
	      String line;
	      while((line = br.readLine()) != null) {
	        builder.append(line + "\n");
	      }
	      br.close();
	    } catch(IOException e) {
	      e.printStackTrace();
	    }
	  }
	  return builder.toString();
	}
	public static boolean containsBlock(int[][] touched) {
		for(int j = 0; j < touched.length; j++) {
			for(int i = 0; i < touched[j].length; i++) {
				if(touched[j][i] > 65535) return true;
			}
		}
		return false;
	}
}