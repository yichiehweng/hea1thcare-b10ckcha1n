import java.security.*;
import java.util.ArrayList;
public class Transaction {
	public String transactionId; 
	public PublicKey owner; 
	public String adder; 
	public int type;
	public String content;	
	public byte[] signature; 
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();	
	private static int sequence = 0; 
	
	public Transaction(PublicKey onwer, String adder, int type,String content ,ArrayList<Transaction> transactions) {
		this.owner = onwer;
		this.adder = adder;
		this.type = type;
		this.content=content;
		this.transactions = transactions;
	}

	private String calulateHash() {
		sequence++; 
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(owner) +
				adder +
				type +content + sequence
				);
	}
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(owner) + adder + type+ content	;
		signature = StringUtil.applyECDSASig(privateKey,data);		
	}
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(owner) + adder + type + content	;
		return StringUtil.verifyECDSASig(owner, data, signature);
	}
	public boolean processTransaction() {
		if(verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		return true;
	}
}
