package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Streams {
    /**
     * Read from an InputStream until a quote character (") is found, then read
     * until another quote character is found and return the bytes in between the two quotes.
     * If no quote character was found return null, if only one, return the bytes from the quote to the end of the stream.
     *
     * @param in
     * @return A list containing the bytes between the first occurrence of a quote character and the second.
     */
    public static List<Byte> getQuoted(InputStream in) throws IOException {
        // TODO: Implement
        List<Byte> byteList = new ArrayList<>();
        // flags to indicate if we are inside a quote
        boolean insideQuote = false;
        boolean outsideQuote = true;
        int flipped = 0;
        int currentByte;
        char byteToChar;

        try {
            while ((currentByte = in.read()) != -1) {
                byteToChar = (char) currentByte;

                if (byteToChar == '"' && flipped < 2) {
                    insideQuote = !insideQuote;
                    outsideQuote = !outsideQuote;
                    flipped++;

                } else if (insideQuote==true || outsideQuote==false) {
                    
                    byteList.add((byte) currentByte);
                }
            }
            // no quotes were found 
            if (flipped == 0) return null;
        
        } catch (IOException e) {
            // Handle the exception (e.g., log, print, or rethrow)
            e.printStackTrace();
        }

        return byteList;
    }


    /**
     * Read from the input until a specific string is read, return the string read up to (not including) the endMark.
     *
     * @param in      the Reader to read from
     * @param endMark the string indicating to stop reading.
     * @return The string read up to (not including) the endMark (if the endMark is not found, return up to the end of the stream).
     */
    public static String readUntil(Reader in, String endMark) throws IOException {
        StringBuilder temp = new StringBuilder();
        String untilEndMark = "";
        char byteToChar; 
        int currentByte;

        try {
            while ((currentByte = in.read()) != -1) {
                byteToChar = (char)currentByte;
                if (byteToChar == '\n') {
                    break;
                }
                temp.append((char)byteToChar);
            }
            // find endMark's index and remove everything it & all after
            int endMarkIndex = temp.indexOf(endMark);
            untilEndMark = temp.substring(0, endMarkIndex);

        } catch (IOException e) {
            // Handle the exception (e.g., log, print, or rethrow)
            e.printStackTrace();
        }
        return untilEndMark;
    }

    /**
     * Copy bytes from input to output, ignoring all occurrences of badByte.
     *
     * @param in
     * @param out
     * @param badByte
     */
    public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
        // TODO: Implement
        int currentByte;

        try {
            while ((currentByte = in.read()) != -1) {
                // cast currentByte to keep signed
                if ((byte)currentByte != badByte) {
                    out.write(currentByte);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a 40-bit (unsigned) integer from the stream and return it. The number is represented as five bytes,
     * with the most-significant byte first.
     * If the stream ends before 5 bytes are read, return -1.
     *
     * @param in
     * @return the number read from the stream
     */
    public static long readNumber(InputStream in) throws IOException {
        // TODO: Implement
        long result = 0;
        
        
        byte[] bytes = new byte[5];

        // read and store into bytes array buffer
        try {
            int numOfBytes = in.read(bytes);
            if (numOfBytes == 5) {
                for (byte b : bytes) {
                    // Shifting previous value 8 bits to right and
                    // add it with next value
                    result = (result << 8 | (b & 0xFFL));
                }
            }
            else {
                result = -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result; 
    }
}
