package Futoshiki;

public class GameTable {
	
	
	private GameCell[][] table;
	
	public GameTable(int dim){
		this(new GameCell[dim][dim]);
	}
	
	public GameTable (GameCell[][] table){
		if(table.length==0){
			this.table=table;
			return;
		}
		this.table=new GameCell[table.length][table[0].length];
		for(int i=0; i<table.length;i++){
			if(table.length!=table[i].length)throw new IllegalArgumentException();
			for(int j=0;j<table[i].length;j++){
				if(table[i][j]==null){
					this.table[i][j]=new GameCell();
				}else {
					this.table[i][j]=new GameCell(table[i][j]);
				}
			}
		}//for 
	}//GameTable
	
	public GameTable(GameTable gt){
		this(gt.table);
	}
	
	public GameCell getCellAt(int row, int column){
		return new GameCell(table[row][column]);
	}
	
	public int getCellValueAt(int row, int column){
		return table[row][column].getValue();
	}
	
	public GameCell setCellAt(int row,int column, GameCell value, boolean is_default){
		GameCell g = table[row][column];
		table[row][column]=new GameCell(value);
		table[row][column].setIsDefault(is_default);
		return g;
	}
	
	public GameCell setCellAt(int row,int column, GameCell value){
		return setCellAt(row,column,value,value.isDefault());
	}
	
	
	public int setCellValueAt(int row , int column, int value){
		GameCell c=table[row][column];
		return setCellValueAt(row,column,value,(c==null)?value!=GameCell.EMPTY_CELL:c.isDefault());
	}
	
	
	public int setCellValueAt(int row, int column, int value, boolean b) {
		int i = table[row][column].getValue();
		table[row][column].setValue((value<=0)?GameCell.EMPTY_CELL:value);
		table[row][column].setIsDefault(b);
		return i;
	}
	
	public int getDimension(){return table.length;}
	
	public String toString(){
		final String LESS_EAST_CHAR="<",GREATER_EAST_CHAR=">",NONE_EAST_CHAR=" ",
				LESS_SOUTH_CHAR="^",GREATER_SOUTH_CHAR="v",NONE_SOUTH_CHAR=" ";
		StringBuilder sb=new StringBuilder();
		for(int i = 0; i<table.length;i++){
			sb.append('|');
			for(int j=0;j<table[i].length;j++){
				GameCell g=table[i][j];
				sb.append(g.getValue());
				if(j<table[i].length-1){
					Relation east=g.getEast();
					switch(east){
						case LESS : sb.append(LESS_EAST_CHAR); break;
						case GREATER : sb.append(GREATER_EAST_CHAR); break;
						default : sb.append(NONE_EAST_CHAR);
					}
				}
			}//riga valori relazioni
			if(i<table.length-1){
				sb.append("|\n|");
				for(int j=0;j<table[i].length;j++){
					GameCell g=table[i][j];
					Relation south=g.getSouth();
					switch(south){
						case LESS : sb.append(LESS_SOUTH_CHAR); break;
						case GREATER : sb.append(GREATER_SOUTH_CHAR); break;
						default : sb.append(NONE_SOUTH_CHAR);
					}
					if(j<table[i].length-1) sb.append(" ");
				}
			}
			sb.append("|\n");
		}
		return sb.toString();
	}//toString
	public boolean equals (Object o){
		if(o==null)return false;
		if(o==this)return true;
		if(! (o instanceof GameTable )) return false;
		GameTable c=(GameTable)o;
		boolean b=true;
		for(int i=0;i<c.getDimension();i++) {
			for(int j=0;j<c.getDimension();j++) {
				if(this.getCellValueAt(i, j)!=c.getCellValueAt(i, j))b=false;
			}
		}
		return b;
	}
	
}//class GameTable
