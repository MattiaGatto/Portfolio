package Futoshiki;
public class Cell {
	int row=0,column=0;
	
	public Cell (int row,int column){
		this.row=row;
		this.column=column;
	}
	
	public String toString(){
		return "("+row+','+column+')';
	}
	@Override
	public boolean equals (Object o){
		if(o==null)return false;
		if(o==this)return true;
		if(! (o instanceof Cell )) return false;
		Cell c=(Cell)o;
		return c.row==row&&c.column==column;
	}
	
}
