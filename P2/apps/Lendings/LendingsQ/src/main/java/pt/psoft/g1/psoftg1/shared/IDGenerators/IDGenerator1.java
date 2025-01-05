package pt.psoft.g1.psoftg1.shared.IDGenerators;

import java.security.SecureRandom;

public class IDGenerator1 implements IDGenerator {

    private static final int LENGTH = 24;
    private static final SecureRandom RANDOM = new SecureRandom(); // Using SecureRandom for better security - as we will be using that for IDs

    /**
     * Generates a 24-character hexadecimal ID using random characters.
     *
     * @return String
     */
    @Override
    public String generateID() {
        StringBuilder id = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            id.append(Integer.toHexString(RANDOM.nextInt(16)));
        }
        return id.toString();
    }
}
