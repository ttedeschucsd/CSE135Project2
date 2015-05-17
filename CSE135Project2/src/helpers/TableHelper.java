package helpers;

import java.util.*;

public class TableHelper {
	public ArrayList<Header> rowHeaders;
	public ArrayList<Header> colHeaders;
	public int [][] itemTotals;		//[column][row]
	
	public TableHelper(){
		rowHeaders = new ArrayList<>();
		colHeaders = new ArrayList<>();
	}
	
	public void addRowHeader(Header header){
		rowHeaders.add(header);
	}
	
	public void addColHeader(Header header){
		colHeaders.add(header);
	}
}
