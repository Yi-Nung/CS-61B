public class Palindrome {
    /** @source https://stackoverflow.com/questions/11229986/get-string-character-by-index-java */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            deq.addLast(word.charAt(i));
        }
        return deq;
    }

    /** Helper method for isPalindrome. */
    private boolean isDequePalindrome(Deque<Character> deq) {
        if (deq.size() == 0 || deq.size() == 1) {
            return true;
        }
        if (deq.removeFirst() != deq.removeLast()) {
            return false;
        } else {
            return isDequePalindrome(deq);
        }
    }

    /** Returns true if the given word is a palindrome. */
    public boolean isPalindrome(String word) {
        Deque<Character> deq = wordToDeque(word);
        return isDequePalindrome(deq);
    }

    /** Helper method for isPalindrome with cc. */
    private boolean isCcPalindrome(Deque<Character> deq, CharacterComparator cc) {
        if (deq.size() == 0 || deq.size() == 1) {
            return true;
        }
        if (!cc.equalChars(deq.removeFirst(), deq.removeLast())) {
            return false;
        } else {
            return isCcPalindrome(deq, cc);
        }
    }

    /** Returns true if the word is a palindrome according to the character comparison test. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deq = wordToDeque(word);
        return isCcPalindrome(deq, cc);
    }
}
