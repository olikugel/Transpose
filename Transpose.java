import java.io.*;
import java.util.*;


public class Transpose 
{

    private static String[] chords = new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private static String transpose(String chord, boolean transposeUp, int transposeBy)
    {
        String transposedChord = "";
        char[] chordAsCharArray = chord.toCharArray();
        int chordIndex = getChordIndex(chord);
        int newChordIndex; 

        if (chordAsCharArray[0] == '.')
            return transposedChord += chord;
            // return transposedChord += chord.substring(1) + " "; // ignore '°', keep space

        if (transposeUp)
            newChordIndex = (chordIndex + transposeBy) % chords.length;
        else // transpose down
            newChordIndex = (chordIndex - transposeBy + chords.length) % chords.length;

        transposedChord = chords[newChordIndex];

        if (chordAsCharArray.length > 1)
            if (chordAsCharArray[1] == '#' || chordAsCharArray[1] == 'b')
                transposedChord += chord.substring(2); // ignore first two characters, e.g. C#m
            else
                transposedChord += chord.substring(1); // ignore first character, e.g. Dsus4

        return transposedChord;
    }   // transpose()
      



    private static int getChordIndex(String chord)
    {
        int chordIndex = 0;

        char[] chordAsCharArray = chord.toCharArray();

        String justTheChord = Character.toString(chordAsCharArray[0]);
        justTheChord = justTheChord.toUpperCase();

        if (chordAsCharArray.length > 1)
            if (chordAsCharArray[1] == '#' || chordAsCharArray[1] == 'b')
                justTheChord += Character.toString(chordAsCharArray[1]);

        switch (justTheChord)
        {
            case "C":  chordIndex = 0;  break;
            case "B#": chordIndex = 0;  break;

            case "C#": chordIndex = 1;  break;
            case "Db": chordIndex = 1;  break;

            case "D":  chordIndex = 2;  break;

            case "D#": chordIndex = 3;  break;
            case "Eb": chordIndex = 3;  break;

            case "E":  chordIndex = 4;  break;
            case "Fb": chordIndex = 4;  break;

            case "F":  chordIndex = 5;  break;
            case "E#": chordIndex = 5;  break;

            case "F#": chordIndex = 6;  break;
            case "Gb": chordIndex = 6;  break;

            case "G":  chordIndex = 7;  break;

            case "G#": chordIndex = 8;  break;
            case "Ab": chordIndex = 8;  break;

            case "A":  chordIndex = 9;  break;

            case "A#": chordIndex = 10; break;
            case "Bb": chordIndex = 10; break;

            case "B":  chordIndex = 11; break;
            case "Cb": chordIndex = 11; break;
            case "H":  chordIndex = 11; break; // for our German friends
        }
        return chordIndex;
    }



    public static void main(String[] args)
    {
        String songtext = args[0];  // The name of the file we're reading from
        String transposition = args[1];
        String line = null;
        char[] lineCharacters = null;
        String[] lineChords = null;
        boolean transposeUp = true;
        int transposeBy = Integer.parseInt(transposition.substring(1)); // ignore first characer (+ or -)
        char[] transpAsCharArray = transposition.toCharArray();
        
        if (transpAsCharArray[0] == '+')
            transposeUp = true;
        else if (transpAsCharArray[0] == '-')
            transposeUp = false;
        else
        {
            System.out.println("Please provide your transposition in the form +x or -x");
            System.exit(0);
        }


        try 
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(songtext);

            // We wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            boolean preceedingLineHeldChords = false;

            while((line = bufferedReader.readLine()) != null) 
            {
                lineCharacters = line.toCharArray();
                
                if (line.length() != 0 && lineCharacters[0] == '.')
                {    
                    lineChords = line.split("[ ]\\b");

                    for (int i = 0; i < lineChords.length; i++)
                    {
                        lineChords[i] = transpose(lineChords[i], transposeUp, transposeBy);
                        System.out.print(lineChords[i] + " ");  
                    }  

                    preceedingLineHeldChords = true;
                } // if

                else if (line.length() != 0 && lineCharacters[0] != '.' && preceedingLineHeldChords)
                {
                    System.out.println("\n" + line);
                    preceedingLineHeldChords = false;
                }

                else if (line.length() != 0 && lineCharacters[0] != '.')
                {
                    System.out.println(line);
                    preceedingLineHeldChords = false;
                }

                else
                    System.out.println();
            } // while 

            // Close the file
            bufferedReader.close(); 
        } // try
        
        catch(FileNotFoundException exception) 
        {
            System.out.println("Unable to open file '" + songtext + "'");                
        }

        catch(IOException exception) 
        {
            System.out.println("Error reading file '" + songtext + "'");                  
        }

    } // main

} // class Transpose

	
