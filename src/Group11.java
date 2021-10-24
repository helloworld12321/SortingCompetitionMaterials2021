import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Group11 {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {

		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}

		String inputFileName = args[0];
		String outFileName = args[1];

		// read as strings
		Integer [] data = readInts(inputFileName);

		Integer [] toSort = data.clone();

		sort(toSort);

		//printArray(sorted, 100);

		toSort = data.clone();

		Thread.sleep(10); //to let other things finish before timing; adds stability of runs

		long start = System.currentTimeMillis();

		sort(toSort);

		long end = System.currentTimeMillis();

		System.out.println(end - start);

		writeOutResult(toSort, outFileName);

	}

	// YOUR SORTING METHOD GOES HERE.
	// You may call other methods and use other classes.
	// Note: you may change the return type of the method.
	// You would need to provide your own function that prints your sorted array to
	// a file in the exact same format that my program outputs
	private static void sort(Integer[] toSort) {
    // We'll use a merge sort, since those tend to require fewer comparisons
    // than quicksorts. (The number of comparisons won't be asymptotically
    // smaller, but it should at least be smaller by a constant factor.)
    mergeSort(
      toSort,
      new Integer[toSort.length],
      0,
      toSort.length,
      new BinaryComparator()
    );
	}

  /**
   * Sort a subrange of an array of integers.
   *
   * @param toSort The array that we're going to sort a subrange of.
   * @param freeSpace An array of exactly the same size as toSort, used as
   * scratch space.
   * @param start The start of the subrange to sort (inclusive)
   * @param end The end of the subrange to sort (exclusive)
   * @param comparator How to compare elements of toSort.
   *
   * This method mutates the array toSort; after this method is called, the
   * subrange of toSort from start to end will be sorted.
   */
  private static void mergeSort(
    Integer[] toSort,
    Integer[] freeSpace,
    int start,
    int end,
    Comparator<Integer> comparator
  ) {
    if (end - start < 2) {
      return;
    }

    int midpoint = (start / 2) + (end / 2) + (start & end & 0x1);
    mergeSort(toSort, freeSpace, start, midpoint, comparator);
    mergeSort(toSort, freeSpace, midpoint, end, comparator);
    int i = start;
    int j = midpoint;
    int k = start;
    while (i < midpoint && j < end) {
      if (comparator.compare(toSort[i], toSort[j]) < 0) {
        freeSpace[k] = toSort[i];
        i++;
        k++;
      } else {
        freeSpace[k] = toSort[j];
        j++;
        k++;
      }
    }
    while (i < midpoint) {
      freeSpace[k] = toSort[i];
      i++;
      k++;
    }
    while (j < end) {
      freeSpace[k] = toSort[j];
      j++;
      k++;
    }

    System.arraycopy(freeSpace, start, toSort, start, end - start);
  }

	private static String[] readData(String inFile) throws FileNotFoundException {
		ArrayList<String> input = new ArrayList<>();
		Scanner in = new Scanner(new File(inFile));

		while(in.hasNext()) {
			input.add(in.next());
		}

		in.close();

		// the string array is passed just so that the correct type can be created
		return input.toArray(new String[0]);
	}

	private static Integer[] readInts(String inFile) throws FileNotFoundException {
		ArrayList<Integer> input = new ArrayList<>();
		Scanner in = new Scanner(new File(inFile));

		// we don't know how many input numbers, so we add them to an array list
		while(in.hasNext()) {
			input.add(in.nextInt());
		}

		in.close();

		// move input to an array of Integers
		return (Integer[]) input.toArray(new Integer[0]);
	}

	private static void writeOutResult(Integer[] sorted, String outputFilename) throws FileNotFoundException {

		PrintWriter out = new PrintWriter(outputFilename);
		for (Integer s : sorted) {
			out.println(s);
			//out.println(s + " " + Integer.toBinaryString(s));
		}
		out.close();

	}

	private static class BinaryComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer n1, Integer n2) {
			int digits1 = Helper11.numBinaryOnes(n1);
			int digits2 = Helper11.numBinaryOnes(n2);

			int lengthSubstring1 = Helper11.lengthLongestRepeatedSubstring(Integer.toBinaryString(n1));
			int lengthSubstring2 = Helper11.lengthLongestRepeatedSubstring(Integer.toBinaryString(n2));

			if (digits1 != digits2) {
        return (digits1 - digits2);
      }

			// executed only of the number of 1s is the same
			if (lengthSubstring1 != lengthSubstring2) {
        return (lengthSubstring1 - lengthSubstring2);
      }

			// executed only if both of the other ones were the same:
			return (n1 - n2);
		}
	}
}
