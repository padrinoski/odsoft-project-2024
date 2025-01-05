package pt.psoft.g1.psoftg1.shared.IDGenerators;

import java.security.SecureRandom;

public class IDGenerator2 implements IDGenerator {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RND = new SecureRandom(); // Using SecureRandom for better security - as we will be using that for IDs
    private static final int LENGTH = 20;

    /**
     * Generates a 20-character alphanumeric hash ID for an entity.
     *
     * @return String
     */
    @Override
    public String generateID() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(AB.charAt(RND.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
