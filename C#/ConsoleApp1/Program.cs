/**
  * @Author -  Bryne T Chikomo
  * @Version - 1.1 
  * The purpose of this code is to demonistrate match between 2 people of different gender
 */
using System;
using System.Collections;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;

namespace ConsoleApp1
{
    class Program
    {
        //Global Variables
        const int MAX_CHAR = 256; //holds array max size
        static void Main(string[] args)
        {
            string text = "";
           
            ArrayList nameList = new ArrayList(); //stores all names
            ArrayList males = new ArrayList(); //store males
            ArrayList females = new ArrayList(); // store females
            ArrayList outputStrings = new ArrayList(); // stores final output

            /**
             * Loop to read all names in the csv file
             */
            foreach (string line in System.IO.File.ReadLines("names.csv"))
            {
               text =  line;
                nameList.Add(line);
            }
            /**
             * Loop to split names, and gender 
             * This loop split by , commma
             */
            for (int z =0 ; z < nameList.Count;z++)
            {
                String[] splittedName = (nameList[z]).ToString().Split(", ");
                if (splittedName[1]== "m")
                {
                    if (check(males, splittedName[0]) == false)
                    {
                        males.Add(splittedName[0]);
                    }
                }
                else if (splittedName[1]== "f")
                {
                    if (check(females, splittedName[0]) == false)
                    {
                        females.Add(splittedName[0]);
                    }
                }


            }
            /**
             * So That males will be matches with females only
             */
            for (int i = 0; i < males.Count; i++)
            {
                for (int j = 0; j < females.Count; j++)
                {
                    if (isStringOnlyAlphabet((string)males[i]) == true && isStringOnlyAlphabet((string)females[j]) == true)
                    {
                        outputStrings.Add(countCharacterOccurence((string)males[i], (string)females[j]));
                    }
                  
                }
            }

            string[] array = new string[outputStrings.Count];
            for (int k = 0; k < outputStrings.Count; k++)
            {
                array[k] = (string)outputStrings[k];
              
            }
            /**
             * Loop to orders names in aphabetical order -Bubble Sort
             */
            for (int i = 0; i < array.Length; i++)
            {
              
                for (int j = i + 1; j < array.Length; j++)
                {
                    //if (Int32.Parse(array[i].Replace("[^0-9]", "")) < Int32.Parse(array[j].Replace("[^0-9]", "")))
                    String first_ = array[i].Substring(0, array[i].IndexOf(" "));
                    String second_ = array[j].Substring(0, array[j].IndexOf(" "));

                    if (first_.CompareTo(second_) >(second_.CompareTo(first_)))
                    {
                       String temp = array[i];
                       array[i] = array[j];
                       array[j] = temp;
                    }
                }
            }
            /**
            * Loop to orders percentages from highest to lowest -Bubble Sort
            */
            for (int i = 0; i < array.Length; i++)
            {
                for (int j = i + 1; j < array.Length; j++)
                {
                    // if (Int32.Parse(array[i].Replace("[^0-9]", "")) == Int32.Parse(array[j].Replace("[^0-9]", "")))
                    String first = array[i].Substring(0, array[i].IndexOf(" "));
                    String second = array[j].Substring(0, array[j].IndexOf(" "));

                    if (first.CompareTo(second) <(second.CompareTo(first)))
                    {

                        String fName = array[i].Substring(0, array[i].IndexOf(" "));
                        String sName = array[j].Substring(0, array[j].IndexOf(" "));

                        if (fName.CompareTo(sName) > 0)
                        {
                            String temp = array[i];
                            array[i] = array[j];
                            array[j] = temp;
                        }
                    }
                }
            }

            string folder = "";
          
            string fileName = "output.txt"; //save output into output file
            // Fullpath. You can direct hardcode it if you like.  
            string fullPath = folder + fileName;
            File.WriteAllLines(fullPath, array);
            Console.WriteLine("Successfully wrote to the file.");


        }
        private static bool check(ArrayList arr, String toCheckValue)
        {
            bool test = false;
            foreach (var c in arr)
            {
                if (c.ToString() == toCheckValue)
                {
                    test = true;
                    break;
                }
            }

            return test;
        }
        /**
          * Function to check if the string is only alphabetic -Using Regex Expressions
          * @Param- str
          * @Return a
         */
        public static bool isStringOnlyAlphabet(String str)
        {
            string   input  = "^[a-zA-Z]*$"; //Regex Expression to check if given string is an alphabetic string

            var a = false;
            if (str != "") a = true;
            if (str != null) a = true;
            int c = 0;
            MatchCollection matches = Regex.Matches(input, str);
            foreach (Match m in matches)
            {
                c++;
                a = false;
            }

            return a;
        }

        /**
          * Function to get the Occurance of a character in a string
          * @Param- str
          * @Return occurances
         */
        public static ArrayList getOccurringChar(String str)
        {
            ArrayList occurences = new ArrayList();
            //const int MAX_CHAR = 256;
            int [] count = new int[MAX_CHAR];
            int len = str.Length;

            for (int i = 0; i < len; i++)
            {
                count[str[i]]++;
            }

            char [] ch = new char[str.Length];
            for (int i = 0; i < len; i++)
            {
                ch[i] = str[i];
                int find = 0;
                for (int j = 0; j <= i; j++)
                {
                    if (str[i] == ch[j])
                    {
                        find++;
                    }
                }

                if (find == 1)
                {
                    occurences.Add(count[str[i]]);
                }
            }

            return occurences;
        }

        /**
          * This Reccuring Function reduces number untill it left with 2 digits
          * 
          * @Param- num
          * @Return 
         */
        static String reduceNum(String num)
        {
            if (num.Length == 2)
            {
                return num;
            }
            else
            {
                StringBuilder str = new StringBuilder(num.Length);
                for (int i = 0, n = num.Length - 1, j = n; i < j; i++, j--)
                {
                    char first = num[i];
                    char last = num[j];

                    int total =(int)char.GetNumericValue(first) + (int)char.GetNumericValue(last);
                    str.Append((total));
                }

                if (num.Length % 2 != 0)
                {
                    str.Append(num[num.Length / 2]);
                }

                return reduceNum(str.ToString());
            }
        }

        /**
          * Heplier Function to count character occurrance
          * 
          * @Param- fString
          * @Param -sString
          * @Return 
         */
        public static String countCharacterOccurence(String fString, String sString)
        {
            ArrayList occurences = new ArrayList();
            String sentence = fString + "matches" + sString;
            String lowerCaseSentence = sentence.ToLower();

            occurences = getOccurringChar(lowerCaseSentence);

            int mainIndex = occurences.Count;

            String numberAsString = "";

            for (int i = 0; i < mainIndex; i++)
            {
                numberAsString = numberAsString + ((int)occurences[i]);
            }

            String finalString = reduceNum(numberAsString);

            if (Int32.Parse(finalString) > 80)
            {
                return fString + " matches " + sString + " " + finalString + "%, good match";
                //outputStrings.add(fString + " matches " + sString + " " + finalString + "%, good match");
                //System.out.println(fString + " matches " + sString + " " + finalString + "%, good match");
            }
            else
            {
                return fString + " matches " + sString + " " + finalString + "%";
                //outputStrings.add(fString + " matches " + sString + " " + finalString + "%");
                //System.out.println(fString + " matches " + sString + " " + finalString + "%");
            }

        }
    }
}
