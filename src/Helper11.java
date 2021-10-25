
public class Helper11 {

	public static void main(String [] args) {
		// Testing the longest repeated non-overlapping substring
		if (lengthLongestRepeatedSubstring("01") != 0) {
			System.out.println("Unexpected result on \"01\": " + lengthLongestRepeatedSubstring("01"));
		}

		if (lengthLongestRepeatedSubstring("00") != 1) {
			System.out.println("Unexpected result on \"00\": " + lengthLongestRepeatedSubstring("00"));
		}

		if (lengthLongestRepeatedSubstring("000") != 1) {
			System.out.println("Unexpected result on \"000\": " + lengthLongestRepeatedSubstring("000"));
		}

		String[] tests = {"0101", "1111", "11111", "010101", "0101010", "0101110101", "1110111111",
				"11100010000", "1110000100001", "111000010001", "011010101"};
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

	public static int lengthLongestRepeatedSubstring(String binary) {
		int length = 0;
		// iterate over possible lengths
		// the longest length is length/2 (rounded down) since they are non-overlapping
		for (int n = 1; n <= binary.length() / 2; ++n) {
			boolean found = false;
			// first index (the first index of the first copy):
      lookingForSubstringOfLengthN:
			for (int i = 0; i < binary.length() - 2*n + 1; ++i) {
				// second index (substrings are non-overlapping):
				for (int j = i + n; j < binary.length() - n + 1; ++j) {
					// iterating over the substring length:

					int k = 0; // need the index after the loop to see if it finished
					for (; k < n; k++) {
						//System.out.println("i = " + i + ", j = " + j + ", k = " + k);
						if (binary.charAt(i + k) != binary.charAt(j + k)) {
							break;
						}
					}
					if (k == n) {
						found = true;
            break lookingForSubstringOfLengthN;
					}
				}
			}

      if (found) {
				length++;
			} else {
				return length;
			}
		}

		return length;
	}

}
