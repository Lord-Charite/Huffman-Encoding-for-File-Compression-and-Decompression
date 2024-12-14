/**
 * implements a code Tree Element
 * provided in the assignment
 * @author lordchariteigirimbabazi , added compareTo method
 * PS-3, CS10, Fall 2023
 */

public class CodeTreeElement implements Comparable<CodeTreeElement>{

    private final Character dataCharacter; //repeated Character
    private final Integer dataFrequency; //frequency at which a character repeats


    /**
     * construstor
     *
     * @param freq - frequency of repeats for individual character
     */
    public CodeTreeElement(Integer freq) {;
        this.dataFrequency = freq;
        dataCharacter = null;
    }

    public CodeTreeElement(Integer freq, Character character) {;
        this.dataFrequency = freq;
        this.dataCharacter = character;
    }


    // Retrieve the data name
    public Character getCharacter() {
        return dataCharacter;
    }


    // Retrieve the data frequency
    public Integer getDataFrequency() {
        return dataFrequency;
    }

    public int compareTo(CodeTreeElement node){
        //greater: return a number > 0
        //equal: return 0
        //less:  return a number < 0
        return dataFrequency.compareTo(node.dataFrequency);
    }

    @Override
    // Convert the data to a string for printing
    public String toString() {
        return dataCharacter+ " " +dataFrequency;
    }
}
