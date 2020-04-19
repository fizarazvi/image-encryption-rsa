package socketProgramming;


import java.math.BigInteger;
import java.security.SecureRandom;
import javax.swing.JOptionPane;


public final class RSA {

    public static void main(String[] args) {

    }

    private BigInteger privateKey, dp, dq, Cp, Cq, mul1, mul2, p, q;
    public static  BigInteger publicKey, modulus;
    public RSA(int bits) {
        generateKeys(bits);
    }


    public static synchronized BigInteger encrypt(BigInteger message, BigInteger publicKey, BigInteger modulus ) {
        return message.modPow(publicKey, modulus);
    }


    public synchronized BigInteger decrypt(BigInteger encryptedMessage) {
        BigInteger yp, yq, xp, xq, result;
        yp = encryptedMessage.mod(p);
        yq = encryptedMessage.mod(q);
        xp = yp.modPow(dp, p);
        xq = yq.modPow(dq, q);
        result = (mul1.multiply(xp).add(mul2.multiply(xq))).mod(modulus);
        return result;
    }


    public synchronized void generateKeys(int bits) {
        SecureRandom r = new SecureRandom();
        p = new BigInteger(bits / 2, 100, r);
        q = new BigInteger(bits / 2, 100, r);
        modulus = p.multiply(q);

        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        publicKey = new BigInteger("65537");
        BigInteger two = new BigInteger("2");
        while (m.gcd(publicKey).intValue() > 1) {
            publicKey = publicKey.add(two);
        }
        privateKey = publicKey.modInverse(m);

        dp = privateKey.mod(p.subtract(BigInteger.ONE));
        dq = privateKey.mod(q.subtract(BigInteger.ONE));
        Cp = q.modInverse(p);
        Cq = p.modInverse(q);
        mul1 = q.multiply(Cp);
        mul2 = p.multiply(Cq);
        System.out.println(publicKey.bitLength()+" "+privateKey.bitLength());
        System.out.println(p.bitLength());
        System.out.println(q.bitLength());
        System.out.println(m.bitLength());
        System.out.println("done");
    }

}