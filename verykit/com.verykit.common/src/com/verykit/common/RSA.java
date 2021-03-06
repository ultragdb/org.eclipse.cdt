package com.verykit.common;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
	private PublicKey publicKey;
	private PrivateKey privateKey;

	// generate an N-bit (roughly) public and private key
	RSA(int N/* number of bits */) {
		// modulus
		BigInteger n;
		// public key
		BigInteger e;
		// private key
		BigInteger d;

		SecureRandom random = new SecureRandom();
		BigInteger p = new BigInteger(N / 2, 100, random);
		BigInteger q = new BigInteger(N / 2, 100, random);
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q
				.subtract(BigInteger.ONE));

		n = p.multiply(q);
		// common value in practice = 2^16 + 1
		e = new BigInteger("65537");
		while (phi.gcd(e).compareTo(BigInteger.ONE) > 0) {
			e = e.add(new BigInteger("2"));
		}
		d = e.modInverse(phi);

		publicKey = new PublicKey(N, n, e);
		privateKey = new PrivateKey(N, n, d);
	}

	public PublicKey getEncryptor() {
		return publicKey;
	}

	public PrivateKey getDecryptor() {
		return privateKey;
	}

	public static class PublicKey {
		// number of bits
		private int N;
		// modulus
		private BigInteger n;
		// public key
		private BigInteger e;

		public PublicKey(int N, BigInteger n, BigInteger e) {
			this.N = N;
			this.n = n;
			this.e = e;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("number of bits = " + N + "\n");
			sb.append("modulus = \"" + n + "\"\n");
			sb.append("public  = \"" + e + "\"\n");
			return sb.toString();
		}

		public BigInteger modPow(BigInteger data) {
			return data.modPow(e, n);
		}
	}

	public static class PrivateKey {
		// number of bits
		private int N;
		// modulus
		private BigInteger n;
		// private key
		private BigInteger d;

		public PrivateKey(int N, BigInteger n, BigInteger d) {
			this.N = N;
			this.n = n;
			this.d = d;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("number of bits = " + N + "\n");
			sb.append("modulus = \"" + n + "\"\n");
			sb.append("private = \"" + d + "\"\n");
			return sb.toString();
		}

		public BigInteger modPow(BigInteger data) {
			return data.modPow(d, n);
		}
	}

	public static void main(String[] args) {
		// Number of bits of the n (the modulus)
		int N = 8192;
		if (args.length >= 1) {
			try {
				N = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number of bits");
				System.exit(1);
			}
			if (N % 2 != 0) {
				System.out.println("number of bits must be even");
				System.exit(1);
			}
			if (N < 2048) {
				System.out
						.println("number of bits must be greater or equal to 2048");
				System.exit(1);
			}
		}

		RSA rsa = new RSA(N);
		System.out.println(rsa.getEncryptor());
		System.out.println(rsa.getDecryptor());

		String text1 = "Yellow and Black Border Collies";
		System.out.println("Plaintext: " + text1);
		// ensure that plaintext is always positive
		BigInteger plaintext = new BigInteger(1, text1.getBytes());

		BigInteger ciphertext = rsa.getEncryptor().modPow(plaintext);
		System.out.println("Ciphertext: " + ciphertext);
		plaintext = rsa.getDecryptor().modPow(ciphertext);

		String text2 = new String(plaintext.toByteArray());
		System.out.println("Plaintext: " + text2);

	}
}
