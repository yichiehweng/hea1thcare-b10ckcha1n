package Blockchain;
import java.security.*;
import java.security.spec.*;

public class ElectronicMedicalRecord {
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public ElectronicMedicalRecord() {
		super();
		generateKeyPair();	
	}
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			keyGen.initialize(ecSpec, random);   
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	privateKey = keyPair.getPrivate();
	        	publicKey = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addRecord() {}
}
