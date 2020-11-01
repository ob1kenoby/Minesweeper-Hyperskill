import java.util.*;

class AsciiCharSequence implements CharSequence {
    byte[] data;

    public AsciiCharSequence(byte[] data) {
        this.data = data.clone();
    }

    @Override
    public int length() {
        return data.length;
    }

    @Override
    public char charAt(int index) {
        return (char) data[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new AsciiCharSequence(Arrays.copyOfRange(this.data, start, end));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (byte b : this.data) {
            result.append((char) b);
        }
        return result.toString();
    }
}