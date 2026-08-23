package com.passwordutil;

public class PasswordOptions {
    private int length;
    private boolean useUppercase;
    private boolean useLowercase;
    private boolean useNumbers;
    private boolean useSymbols;

    // Default configuration: 12 chars with all character sets enabled
    public PasswordOptions() {
        this.length = 12;
        this.useUppercase = true;
        this.useLowercase = true;
        this.useNumbers = true;
        this.useSymbols = true;
    }

    // Custom configuration constructor
    public PasswordOptions(int length, boolean useUppercase, boolean useLowercase, boolean useNumbers, boolean useSymbols) {
        setLength(length);
        this.useUppercase = useUppercase;
        this.useLowercase = useLowercase;
        this.useNumbers = useNumbers;
        this.useSymbols = useSymbols;
        validateOptions();
    }

    // Ensures length is within reasonable bounds
    public void setLength(int length) {
        if (length < 8 || length > 128) {
            throw new IllegalArgumentException("Password length must be between 8 and 128 characters.");
        }
        this.length = length;
    }

    // Check that at least one character type is picked
    public void validateOptions() {
        if (!useUppercase && !useLowercase && !useNumbers && !useSymbols) {
            throw new IllegalArgumentException("You must select at least one character type.");
        }
    }

    // Calculate Password Pool Size

    public int calculatePoolSize() {
        int poolSize = 0;
        if (isUseUppercase()) poolSize += 26;
        if (isUseLowercase()) poolSize += 26;
        if (isUseNumbers())   poolSize += 10;
        if (isUseSymbols())   poolSize += 32;
        return poolSize;
    }

    // Standard getters and setters
    public int getLength() { return length; }

    public boolean isUseUppercase() { return useUppercase; }
    public void setUseUppercase(boolean useUppercase) { this.useUppercase = useUppercase; }

    public boolean isUseLowercase() { return useLowercase; }
    public void setUseLowercase(boolean useLowercase) { this.useLowercase = useLowercase; }

    public boolean isUseNumbers() { return useNumbers; }
    public void setUseNumbers(boolean useNumbers) { this.useNumbers = useNumbers; }

    public boolean isUseSymbols() { return useSymbols; }
    public void setUseSymbols(boolean useSymbols) { this.useSymbols = useSymbols; }
}
