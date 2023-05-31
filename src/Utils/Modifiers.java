package Utils;

public enum Modifiers {

    Prefix,
    Suffix,
    PrefixOnly,
    SuffixOnly,
    Both;

    @Override
    public String toString() {
        return this.name();
    }
}
