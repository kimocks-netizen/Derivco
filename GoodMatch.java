/**
 * GoodMatch
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GoodMatch {
    BufferedReader reader;
    static final int MAX_CHAR = 256;
    public static void main(String[] args) {
        //getUserNames();
        try {
            readCSVFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
 * 
 * @param str
 * @return string
 * This method checks  if the String is only alphabetic 
 */
    public static boolean isStringOnlyAlphabet(String str)
    {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }
/**
 * 
 * @param str
 * @return occurrences (how many times the character occurred in the string)
 * This method gets number of occurrence of a character in a string.
 */
    public static ArrayList<Integer> getOccurringChar(String str)
    {
        ArrayList<Integer> occurences = new ArrayList<Integer>();
        int count[] = new int[MAX_CHAR];
        int len = str.length();
 
        for (int i = 0; i < len; i++){
            count[str.charAt(i)]++;
        }
            
        char ch[] = new char[str.length()];
        for (int i = 0; i < len; i++) {
            ch[i] = str.charAt(i);
            int find = 0;
            for (int j = 0; j <= i; j++) {
                if (str.charAt(i) == ch[j]){
                    find++;
                }
            }
 
            if (find == 1){
                occurences.add(count[str.charAt(i)]);
            }    
        }

        return occurences;
    }
/**
 * To check if Ist and 2nd string are are alphabetic
 */
    public static void getUserNames(){
        Scanner myObj = new Scanner(System.in);

        try{
            System.out.print("Enter first string: ");
            String firstString = myObj.nextLine();
            System.out.print("Enter second string: ");
            String secondString = myObj.nextLine();

            if(isStringOnlyAlphabet(firstString) == true && isStringOnlyAlphabet(secondString) == true){
                countCharacterOccurence(firstString, secondString);
            }else{
                System.out.println("Only alphabetic characters are allowed for the names.");
            }
        }finally{
            myObj.close();
        }
    }

    private static boolean check(ArrayList<String> arr, String toCheckValue)
    {
        boolean test = false;
        for (String element : arr) {
            if (element.equals(toCheckValue)) {
                test = true;
                break;
            }
        }
 
        return test;
    }
/**
 * Method to open and read CSV file
 * @throws Exception
 */
    public static void readCSVFile() throws Exception{
        ArrayList<String> namesList = new ArrayList<String>();
        ArrayList<String> males = new ArrayList<String>();
        ArrayList<String> females = new ArrayList<String>();
        ArrayList<String> outputStrings = new ArrayList<String>();

        //Change CSV name below
        Scanner sc = new Scanner(new File("names.csv"));
        //Change CSV name above
        
        sc.useDelimiter(",");
        while (sc.hasNext()){
            namesList.add(sc.nextLine());
        }
        sc.close();

        for(int i = 0; i < namesList.size(); i++){
            String[] splittedName = namesList.get(i).split(", ");
            if(splittedName[1].equals("m")){
                if(check(males, splittedName[0]) == false){
                    males.add(splittedName[0]);
                }
            }else if(splittedName[1].equals("f")){
                if(check(females, splittedName[0]) == false){
                    females.add(splittedName[0]);
                }
            }
        }

        for(int i = 0; i < males.size(); i++){
            for(int j = 0; j < females.size(); j++){
            	
            	//compare male and female
                if(isStringOnlyAlphabet(males.get(i)) == true && isStringOnlyAlphabet(females.get(j)) == true){
                    outputStrings.add(countCharacterOccurence(males.get(i), females.get(j)));
                }else{
                    if(isStringOnlyAlphabet(males.get(i)) == false){
                        System.out.println("The name " + males.get(i) + " should contain only alphabetic characters.");
                    }else if(isStringOnlyAlphabet(females.get(j)) == false){
                        System.out.println("The name " + females.get(j) + " should contain only alphabetic characters.");
                    }
                }
            }
        }

        String[] array = new String[outputStrings.size()];
        for(int i = 0; i < outputStrings.size(); i++){
            array[i] = outputStrings.get(i);
        }
        
        for(int i = 0 ; i < array.length;i++)
        {
            for(int j = i+1 ; j < array.length;j++)
            {
                if(Integer.parseInt(array[i].replaceAll("[^0-9]", "")) < Integer.parseInt(array[j].replaceAll("[^0-9]", "")))
                {
                    String temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }

        for(int i = 0 ; i < array.length;i++)
        {
            for(int j = i+1 ; j < array.length;j++)
            {
                if(Integer.parseInt(array[i].replaceAll("[^0-9]", "")) == Integer.parseInt(array[j].replaceAll("[^0-9]", "")))
                {

                    String fName= array[i].substring(0, array[i].indexOf(" "));
                    String sName= array[j].substring(0, array[j].indexOf(" "));

                    if(fName.compareTo(sName) > 0){
                        String temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
            }
        }
        
        try {
        	//Save to output.txt file
            FileWriter myWriter = new FileWriter("output.txt");

            for(int i = 0; i < array.length; i++){
                myWriter.write(array[i]);
                myWriter.write(System.lineSeparator());
            }
            myWriter.close();
            System.out.println("Successfully wrote in the Output.txt file.");
          } catch (IOException e) {
            e.printStackTrace();
          }
    }

    /**
     * This method counts number of occurrences of a character from 1st string, matches and 2nd string
     * @param fString
     * @param sString
     * @return String
     */
    public static String countCharacterOccurence(String fString, String sString)
    {
        ArrayList<Integer> occurences = new ArrayList<Integer>();
        String sentence = fString + "matches" + sString;
        String lowerCaseSentence = sentence.toLowerCase();

        occurences = getOccurringChar(lowerCaseSentence);

        int mainIndex = occurences.size();

        String numberAsString = "";

        for(int i = 0; i < mainIndex; i++){
            numberAsString = numberAsString + Integer.toString(occurences.get(i));
        }

        String finalString = reduceNum(numberAsString);

        if(Integer.parseInt(finalString) > 80){
            return fString + " matches " + sString + " " + finalString + "%, good match";
            //outputStrings.add(fString + " matches " + sString + " " + finalString + "%, good match");
            //System.out.println(fString + " matches " + sString + " " + finalString + "%, good match");
        }else{
            return fString + " matches " + sString + " " + finalString + "%";
            //outputStrings.add(fString + " matches " + sString + " " + finalString + "%");
            //System.out.println(fString + " matches " + sString + " " + finalString + "%");
        }

        //ArrayList<String> outputStringsInOrder = new ArrayList<String>();
    }
/**
 * 
 * @param num
 * @return
 */
    static String reduceNum(String num){
        if(num.length() == 2){
            return num;
        }else{
            StringBuilder str = new StringBuilder(num.length());
            
            //now lets truncate
            for (int i = 0, n = num.length() - 1, j = n; i < j; i++, j--) {
                char first = num.charAt(i);
                char last = num.charAt(j);
                
                int total = Character.getNumericValue(first) + Character.getNumericValue(last);
                str.append(Integer.toString(total));
            }

            if(num.length()%2 != 0){
                str.append(num.charAt(num.length()/2));
            }

            return reduceNum(str.toString());
        }
    }
}