package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {
    /**
     * Treat the file as an array of (unsigned) 8-bit values and sort them
     * in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     */
    public static void sortBytes(RandomAccessFile file) throws IOException {
        // TODO: implement
        long fileSize = file.length();
    
        // Perform bubble sort
        try {
            for (int i = 0; i < fileSize - 1; i++) {
                for (int j = 0; j < fileSize - i - 1; j++) {
                    // Move the file pointer to the j-th position
                    file.seek(j);
                    // Read two bytes
                    int byte1 = file.read();
                    file.seek(j+1);
                    int byte2 = file.read();

                    if (byte1 > byte2) {
                        // move the pointer back to the j-th position 
                        file.seek(j);
                        // Write the swapped bytes
                        file.writeByte(byte2);
                        file.seek(j+1);
                        file.writeByte(byte1);
                    }
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
     * them in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     * @throws IOException
     */
    public static void sortTriBytes(RandomAccessFile file) throws IOException {
        // TODO: implement
        long fileSize = file.length();

        try {
            for (int i = 0; i < fileSize - 3; i+=3) {
                for (int j = 0; j < fileSize - i - 3; j+=3) {
                    // Read 3 bytes
                    file.seek(j);
                    int byte1 = read24Bits(file, j);
                    file.seek(j+3);
                    int byte2 = read24Bits(file, j+3);

                    if (byte1 > byte2) {
                        // move the pointer back to the j-th position 
                        file.seek(j);
                        // Write the swapped bytes
                        writeNumberTo3Bytes(file, byte2);
                        file.seek(j+3);
                        writeNumberTo3Bytes(file, byte1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int read24Bits(RandomAccessFile file, int index) throws IOException {
        byte[] buffer = new byte[3];
        
        // Read 3 bytes into the buffer
        file.seek(index);
        int bytesRead = file.read(buffer);
    
        // Combine the bytes to form a 24-bit value
        int value = ((buffer[0] & 0xFF) << 16) | ((buffer[1] & 0xFF) << 8) | (buffer[2] & 0xFF);

        return value;
    }
    public static void writeNumberTo3Bytes(RandomAccessFile file, int number) throws IOException {
        // Extract individual bytes using bitwise operations
        byte byte1 = (byte) ((number >> 16) & 0xFF);
        byte byte2 = (byte) ((number >> 8) & 0xFF);
        byte byte3 = (byte) (number & 0xFF);

        // Write the bytes to the output stream
        file.write(byte1);
        file.write(byte2);
        file.write(byte3);
    }
        
}


