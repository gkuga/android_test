package jp.gr.java_conf.pgmer.wanko;

public class WankoNumber {
	
	protected static int targetnum;

    protected int number;
    
    public WankoNumber(int number) {
    	targetnum = 1;
    	this.number = number;
    }
    
    public boolean check() {
    	return number == targetnum ? true : false;
    }
    
    public int setNextNumber() {
    	return ++targetnum;
    }
    
}
