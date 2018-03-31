import java.util.ArrayList;
import com.google.gson.GsonBuilder;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;

import Blockchain.ElectronicMedicalRecord;


public class Blockchain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	public static ElectronicMedicalRecord e;
	public static Transaction initialTransaction; 
	public static void main(String[] args) {		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());	
		ElectronicMedicalRecord e=new ElectronicMedicalRecord();
		
		initialTransaction = new Transaction(e.publicKey, "adder1",1,"DM",null);
		initialTransaction.generateSignature(e.privateKey);
		initialTransaction.transactionId = "0";
		
		Block firstblock = new Block("0");
		firstblock.addTransaction(initialTransaction);
		addBlock(firstblock);
		
		Transaction secondTransaction=new Transaction(e.publicKey, "Adder2",2,"HTN",null);
		secondTransaction.generateSignature(e.privateKey);
		secondTransaction.transactionId="1";
		Block block1 = new Block(firstblock.hash);
		block1.addTransaction(secondTransaction);
		addBlock(block1);
		isChainValid();
	}
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes is not equal");
				return false;
			}
			
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes is not equal");
				return false;
			}
		}
		return true;
	}
	public static void addBlock(Block block) {
		block.mineBlock(difficulty);
		blockchain.add(block);
	}
}
