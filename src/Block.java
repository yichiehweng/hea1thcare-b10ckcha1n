import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private String data; 
	private long timeStamp;
	private int nonce;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); 
	public Block(String previousHash) {
		super();
		this.previousHash = previousHash;
		this.timeStamp=new Date().getTime();
		this.hash = calculateHash();
	}
	public String calculateHash() {
		String thehash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
		return thehash;
	}
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');  
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	public boolean addTransaction(Transaction transaction) {
		if(transaction == null) return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully");
		return true;
	}
}
