import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

class StringCalculator {
    public int add(String numbers) {
        List<String> delimiters = new ArrayList<String>();
        List<Integer> negatives = new ArrayList<Integer>();

        int result = 0, number;

        if (numbers.isEmpty()) return 0;

        if (numbers.charAt(0) == '/' && numbers.charAt(1) == '/')
        {
            // Finding part with numbers
            String pattern1 = "\\[(.*?)\\]";
            String pattern2 = "\\n(.+)";
            String pattern3 = "//(.*?)\\n";

            Pattern delimiters_pattern = Pattern.compile(pattern1);
            Pattern delimiters_pattern_without_brackets = Pattern.compile(pattern3);
            Pattern numbers_pattern = Pattern.compile(pattern2);

            Matcher delimiters_matcher = delimiters_pattern.matcher(numbers.substring(2));
            Matcher delimiters_matcher_without_brackets = delimiters_pattern_without_brackets.matcher(numbers);
            Matcher numbers_matcher = numbers_pattern.matcher(numbers);

            if (numbers_matcher.find()){ // Finding numbers part
                numbers = numbers_matcher.group(1);
            }

            if (!delimiters_matcher.find())
            {
                while (delimiters_matcher_without_brackets.find())
                {
                    delimiters.add(delimiters_matcher_without_brackets.group(1));
                }
            }else {
                do{
                    delimiters.add(delimiters_matcher.group(1));
                }while (delimiters_matcher.find());
            }
        }else{
            delimiters.add(",");
            delimiters.add("\n");
        }

        Collections.sort(delimiters, Collections.reverseOrder()); // Replacing delimiters with spaces
        for (String i : delimiters){
            numbers = numbers.replace(i, " ");
        }

        String[] split_numbers = numbers.split(" ", -1); // Final split part

        // Calculation part
        for (String num : split_numbers)
        {
            if (num.isEmpty()) {
                System.out.println("Error. Wrong delimiter use.");
                //System.exit(1);
                return 0;
            }

            if (int_check(num)) // is it number
            {
                number = Integer.parseInt(num);
                if (is_positive(number)) // is it positive
                {
                    if (is_maximum(number)) result += number; // is it greater than 1000
                }else {
                    negatives.add(Integer.parseInt(num));
                }
            }else {
                System.out.printf("%s is not a number! So it'll be ignored.\n", num);
            }
        }
        if (!negatives.isEmpty()){
            System.out.println("Negative numbers are found: " + negatives.toString().substring(1, negatives.toString().length()-1));
        }

        return result;
    }

    // Check if string is number
    public static boolean int_check(String number)
    {
        try
        {
            Integer.parseInt(number);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }


    // Properties check
    public static boolean is_positive(int number) {return number >= 0;}
    public static boolean is_maximum(int number) {return number <= 1000;}

}


public class Main {
    public static void main(String[] args) {

        StringCalculator calc = new StringCalculator();
        System.out.println(calc.add("//[***][*][**][123]\n2*2***21232,"));
    }
}