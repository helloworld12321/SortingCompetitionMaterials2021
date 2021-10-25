
public class Helper11 {

	public static void main(String [] args) {
		// Testing the longest repeated non-overlapping substring
		if (lengthLongestRepeatedSubstring(0b1) != 0) {
			System.out.println("Unexpected result on \"0b1\": " + lengthLongestRepeatedSubstring(1));
		}

		if (lengthLongestRepeatedSubstring(0b11) != 1) {
			System.out.println("Unexpected result on \"0b11\": " + lengthLongestRepeatedSubstring(3));
		}

		if (lengthLongestRepeatedSubstring(0b111) != 1) {
			System.out.println("Unexpected result on \"0b111\": " + lengthLongestRepeatedSubstring(7));
		}

		int[] tests = {0b1010, 0b1111, 0b11111, 0b101010, 0b1010101, 0b1010001010, 0b1110111111,
				0b11100010000, 0b1110000100001, 0b111000010001, 0b100101010};
		int [] answers = {2, 2, 2, 2, 3, 4, 3, 4, 5, 4, 3};

		if (tests.length != answers.length) {
			System.out.println("The number of tests and answers don't match");
		}

		for(int i = 0; i < tests.length; ++i) {
			if (lengthLongestRepeatedSubstring(tests[i]) != answers[i]) {
				System.out.println("Unexpected result on " + tests[i] + ":" +
						lengthLongestRepeatedSubstring(tests[i]) + " instead of " + answers[i]);
			}
		}
	}

  // Use a static variable instead of a local variable to get rid of malloc costs.
  // The element at substringLengths[i][j] contains the length of the longest
  // repeated substring where the first repetition ends at y and the second
  // repetition ends at x.
  static final byte[][] substringLengths = new byte[24][24];
	public static int lengthLongestRepeatedSubstring(int number) {
    // This algorithm is modified from the one given here:
    // https://en.wikipedia.org/wiki/Longest_common_substring_problem#Dynamic_programming

    int binaryLength = binaryLength(number);

		int longestSubstringLengthSoFar = 0;

		for (int i = 0; i < binaryLength; i++) {
      for (int j = i + 1; j < binaryLength; j++) {
        // Do the bits at positions i and j match?
        if ((number >> i & 1) == (number >> j & 1)) {
          int lengthFound;

          // There's a repeated substring ending at i and j.
          if (i == 0) {
            lengthFound = substringLengths[j][i] = 1;
          } else {
            // Either:
            // - We can get length of the repeated substring at (i, j) from the
            //   length of the repeated substring at (i - 1, j - 1), or
            // - If that would cause the two instances of the substring to
            //   with each other, then we just make the repeated substring as
            //   big as possible.
            lengthFound = substringLengths[j][i] = (byte) Math.min(
              substringLengths[j - 1][i - 1] + 1,
              j - i
            );
          }

          if (lengthFound > longestSubstringLengthSoFar) {
            longestSubstringLengthSoFar = lengthFound;
          }
        }
			}
		}

		return longestSubstringLengthSoFar;
	}

  /**
   * Return the length of the binary representation of an integer.
   *
   * Note that in the degenerate case, length(0) returns 0.
   *
   * Source: https://stackoverflow.com/a/680040
   */
  private static int binaryLength(int i) {
    return Integer.SIZE - Integer.numberOfLeadingZeros(i);
  }

}
