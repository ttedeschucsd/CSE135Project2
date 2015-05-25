package helpers;

import java.util.*;

public class TableHelper {
	public ArrayList<Header> rowHeaders;
	public ArrayList<Header> colHeaders;
	public int [][] itemTotals;		//[row][column]
	
	public TableHelper(){
		rowHeaders = new ArrayList<>();
		colHeaders = new ArrayList<>();
		itemTotals = new int[21][11];
	}
	
	public void addRowHeader(Header header){
		rowHeaders.add(header);
	}
	
	public void addColHeader(Header header){
		colHeaders.add(header);
	}
	
	public void addTableRow(Map<Integer, Integer> input, int rowIndex){
		for(int i=0; i<colHeaders.size(); i++){
			int prodId = colHeaders.get(i).id;
			if(input.containsKey(prodId)){
				int totalVal = input.get(prodId);
				itemTotals[rowIndex][i] = totalVal;
			}
		}
	}
	
	public void addItem(int row, int col, int total){
		itemTotals[row][col] = total;
	}
}
