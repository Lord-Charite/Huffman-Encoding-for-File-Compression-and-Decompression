import java.util.Comparator;
import java.util.Objects;

/**
 * compare two trees
 * @author lordchariteigirimbabazi
 * CS10, Fall 2023
 * PS3
 */
public class HuffmanNodeComparator implements Comparator<BinaryTree<CodeTreeElement>>{

    @Override
    public int compare(BinaryTree<CodeTreeElement> node1, BinaryTree<CodeTreeElement> node2) {

        if (node1.getData().getDataFrequency() < node2.getData().getDataFrequency()) {
            // Return -1 to indicate that node1 has a smaller frequency.
            return -1;
        } else if (Objects.equals(node1.getData().getDataFrequency(), node2.getData().getDataFrequency())) {
            // Return 1 to indicate that node1 has a larger frequency.
            return 0;
        }
        // Return 0 to indicate that the frequencies are equal.
        return 1;
    }
}