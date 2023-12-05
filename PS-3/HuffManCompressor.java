import java.io.*;
import java.util.*;

/**
 * @author lordchariteigirimbabazi
 * compress and decompress file using HuffMan approach
 * PS3
 * CS10, Fall 2023
 */
public class HuffManCompressor {
    Character ch = null; // A character variable, initially set to null.
    private final PriorityQueue<BinaryTree<CodeTreeElement>> huffmanPriorityQueue; // Priority queue for Huffman tree nodes.
    private final Map<Character, Integer> frequencyMap; // Map to store character frequencies.
    private final Map<Character, String> bitsMap; // Map to store Huffman codes for characters.
    private final ArrayList<String> path; // List to store the paths of binary codes for characters.

    protected static final String givenFile = "inputs/USConstitution.txt"; // Input text file to be compressed and decompressed.

    /*
     * WarAndPeace compressed file: 1.8MB
     * WarAndPeace decompressed file: 3.22MB
     * WarAndPeace original: 3.22MB
     */

    public HuffManCompressor() {

        this.path = new ArrayList<String>();
        this.frequencyMap = new HashMap<Character, Integer>();
        this.bitsMap = new HashMap<Character, String>();
        HuffmanNodeComparator nodeComparator = new HuffmanNodeComparator();
        this.huffmanPriorityQueue = new PriorityQueue<BinaryTree<CodeTreeElement>>(nodeComparator);
    }



    /**
     * Gets the binary codes for each character
     * Use recursion until the leaf is found
     *
     * @param codeMap - holds code paths
     * @param tree - binary tree
     * @param code    - binary codes : "1" and "0"
     */
    public void dataCode(Map<Character, String> codeMap, BinaryTree<CodeTreeElement> tree, String code) {

        if (tree.hasLeft()) {
            dataCode(codeMap, tree.getLeft(), code + "0"); // Recursively traverse left subtree with '0'.
        }

        if (tree.hasRight()) {
            dataCode(codeMap, tree.getRight(), code + "1"); // Recursively traverse right subtree with '1'.
        }

        if (tree.isLeaf()) {
            codeMap.put(tree.getData().getCharacter(), code); // Store the binary code for the leaf node character.
        }

    }

    /**
     * Method to retrieve the binary code
     */

    public void getBits()  {
        huffManTree(); // Build the Huffman tree.
        if (huffmanPriorityQueue.peek() != null) {
            dataCode(bitsMap, huffmanPriorityQueue.peek(), ""); // Generate binary codes for characters.
        }
    }

    /**
     * Constructs the Huffman tree from a priority queue of nodes.
     */
    public void huffManTree() {
        while (huffmanPriorityQueue.size() > 1) {

            // Get the two nodes with the lowest frequencies from the priority queue.
            BinaryTree<CodeTreeElement> t1= huffmanPriorityQueue.peek();
            huffmanPriorityQueue.remove(t1);
            BinaryTree<CodeTreeElement> t2 = huffmanPriorityQueue.peek();
            huffmanPriorityQueue.remove(t2);

            // Retrieve the frequencies of the two nodes.
            int t1frq = t1.getData().getDataFrequency();
            assert t2 != null;
            int t2frq = t2.getData().getDataFrequency();

            // Combine the two nodes to create a new parent node.
            int parentFrequency = t1frq + t2frq;
            BinaryTree<CodeTreeElement> tNew = new BinaryTree<>(new CodeTreeElement(parentFrequency));
            tNew.left = t1;
            tNew.right = t2;
            huffmanPriorityQueue.add(tNew);
        }


        if (huffmanPriorityQueue.peek() != null) {
            dataCode(bitsMap, huffmanPriorityQueue.peek(), ""); // Generate binary codes for characters in the final Huffman tree.
        }

    }

    /**
     * Reads the input file, calculates character frequencies, and updates the frequency map.
     *
     * @param file - The path to the input file.
     * @throws IOException - Thrown in case of I/O errors.
     */
    public void characterFrequency(String file) throws IOException {
        //creates a reader to get text in input file
        BufferedReader fileRead = new BufferedReader(new FileReader(file));
        //reads the text file in by character
        int c;
        while ((c = fileRead.read()) != -1) {
            Character ch = (char) c;
            //overwrite if character is already in the map
            //if new, update value
            if(frequencyMap.containsKey(ch)) {
                frequencyMap.replace(ch, frequencyMap.get(ch) + 1);
            } else {
                frequencyMap.put(ch, 1);
            }
        }

        fileRead.close();
    }

    /**
     * Processes the character frequency map and adds data nodes to the priority queue.
     *
     * @throws Exception - Thrown if the frequency map is empty.
     */
    public void freqPriorityQueue() throws Exception {
        //get the frequency map
        characterFrequency(givenFile);
        Set<Character> frequency = frequencyMap.keySet();
        if (frequency.isEmpty()) {
            throw new Exception("Error: Your FREQUENCY MAP IS EMPTY");
        } else {
            //using iterator to iterate through the set
            for (Character freq : frequency) {
                CodeTreeElement info = new CodeTreeElement(frequencyMap.get(freq), freq);
                BinaryTree<CodeTreeElement> info1 = new BinaryTree<CodeTreeElement>(info);
                huffmanPriorityQueue.add(info1);
            }
        }
    }

    /**
     * Helper method for finding characters in the Huffman tree based on binary codes.
     *
     * @param string - The binary code string.
     * @param t      - The current Huffman tree node.
     */
    private void findCharacterToBinaryCode (String string, BinaryTree <CodeTreeElement> t){
        String str = "";

        if (t.isLeaf()) {
            ch = t.getData().getCharacter();
        }

        if (string.length() != 0) {
            str = string.substring(0, 1);
        }

        //traverse on right tree if the binary number is one
        if (str.equals("1")){
            if (t.hasRight()) {
                findCharacterToBinaryCode(string.substring(1), t.getRight());
            }
        }
        //traverse on left tree if the binary number is zero
        else if (str.equals("0")) {
            if (t.hasLeft()) {
                findCharacterToBinaryCode(string.substring(1), t.getLeft());
            }
        }

    }

    /**
     * Compresses the input text file and writes the compressed data to an output file.
     *
     * @param bitOutput - The BufferedBitWriter object for writing bits.
     * @param input     - The BufferedReader object for reading input data.
     * @throws IOException - Thrown in case of I/O errors.
     */

    public void compress(BufferedBitWriter bitOutput, BufferedReader input) throws IOException {
        int readChar;
        while ((readChar = input.read()) != -1) {
            Character ch = (char) readChar;
            String code = bitsMap.get(ch); // Get the binary code for the character.

            int count = 0;
            while (count < code.length()) {
                char bit = code.charAt(count);
                boolean isOne = (bit == '1');
               // If isOne is true, it writes a '1'; if isOne is false, it writes a '0'
                bitOutput.writeBit(isOne); // Write the binary bits to the compressed file.
                count ++;
            }
        }

        input.close();
        bitOutput.close();
    }

    /**
     * Decompresses a compressed file and writes the decompressed data to an output file.
     *
     * @param output - The BufferedWriter object for writing decompressed data.
     * @param input  - The BufferedBitReader object for reading compressed data.
     * @throws IOException - Thrown in case of I/O errors.
     */
    public void decompress(BufferedWriter output, BufferedBitReader input) throws IOException {
        StringBuilder str = new StringBuilder();
        while ((input.hasNext())) {
            boolean bit = input.readBit();
            if (!bit) {
                str.append("0");
            } else {
                str.append("1");
            }
            if (bitsMap.containsValue(new String(str))) {
                path.add(String.valueOf(str));
                str = new StringBuilder(""); // Clear the StringBuilder for the next binary code.
            }
        }
        for (String path : path) {
            assert huffmanPriorityQueue.peek() != null;
            findCharacterToBinaryCode(path, huffmanPriorityQueue.peek());
            output.write(ch); // Write the decompressed characters to the output file.
        }
        input.close();
        output.close();
    }

    public static void main(String[] args) {

        // Create an instance of the HuffManCompressor class.
        HuffManCompressor data = new HuffManCompressor();

        try {
            data.freqPriorityQueue(); // Calculate character frequencies and populate the priority queue.
            data.getBits(); // Build the Huffman tree and generate binary codes.


            String baseFileName = givenFile.replace(".txt", "");
            String compressedFile = baseFileName + "_compressed.txt";
            String decompressedFile = baseFileName + "_decompressed.txt";

            BufferedBitWriter bitOut = new BufferedBitWriter(compressedFile);
            BufferedReader inputFile = new BufferedReader(new FileReader(givenFile));

            data.compress(bitOut, inputFile);

            BufferedBitReader bitIn = new BufferedBitReader(compressedFile);
            BufferedWriter outputFile = new BufferedWriter(new FileWriter(decompressedFile));


            data.decompress(outputFile, bitIn);

            bitOut.close();
            inputFile.close();
            bitIn.close();
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

