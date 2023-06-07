package ir.ac.kntu.utility;

@FunctionalInterface public interface ItemPrinter<T> {
    String getText(T t, int count);
}
