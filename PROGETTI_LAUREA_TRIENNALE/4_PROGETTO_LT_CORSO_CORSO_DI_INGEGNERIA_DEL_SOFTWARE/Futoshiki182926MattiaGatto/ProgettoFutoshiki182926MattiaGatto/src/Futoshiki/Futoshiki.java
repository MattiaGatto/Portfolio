package Futoshiki;


import java.util.*;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Futoshiki extends Problema<Cell,Integer> {
	
	private GameTable table;
	private List<GameTable> soluzioni=new LinkedList<GameTable>();
	public Futoshiki(int dim,int nsol){
		this(new GameTable(dim),nsol);
	}
	public Futoshiki (GameTable table,int nsol){
		super(nsol);
		this.table=new GameTable(table);
	}
	@Override
	protected Cell primoPuntoDiScelta() {
		return prossimoPuntoDiScelta(new Cell(0,-1),1);
	}
	@Override
	protected Cell prossimoPuntoDiScelta(Cell c, Integer value) {
		for(int i=c.row;i<table.getDimension();i++){
			for(int j=(c.row==i)?c.column+1:0;j<table.getDimension();j++){
				GameCell g= table.getCellAt(i,j);
				if(g.getValue()==GameCell.EMPTY_CELL ||! g.isDefault())
					return new Cell(i,j);
			}
		};
		return ultimoPuntoDiScelta();
	}//prossimoPuntoScelta

	@Override
	protected Cell ultimoPuntoDiScelta() {
		int dim=table.getDimension()-1;
		Cell returned=new Cell(dim,dim);
		for(int i=dim;i>=0;i--){
			for(int j=dim;j>=0;j--){
				GameCell g= table.getCellAt(i, j);
				if(g.getValue()==GameCell.EMPTY_CELL ||! g.isDefault())
					return new Cell(i,j);	
			}
		}
		return returned;
	}

	@Override
	protected Integer primaScelta(Cell ps) {
		return 1;
	}

	@Override
	protected Integer prossimaScelta(Integer s) {
		return s+1;
	}

	@Override
	protected Integer ultimaScelta(Cell ps) {
		return table.getDimension();
	}

	@Override
	protected boolean assegnabile(Integer value, Cell c) {
		GameCell g=table.getCellAt(c.row, c.column);
		if(g.isDefault())return true;
		
		/*-----------inizio controlli relazioni -----------------*/
		
		GameCell east,west,south,north;
		int tmp=-1;
		if(c.row>0){
			north=table.getCellAt(c.row-1, c.column);
			Relation r=north.getSouth();
			tmp=north.getValue();
			switch(r){
			case LESS : if (tmp!=GameCell.EMPTY_CELL&&value<tmp) return  false; break;
			case GREATER : if (tmp!=GameCell.EMPTY_CELL&&value>tmp) return false;break;
			default : ;
			}//switch
		}
		
		if(c.row<table.getDimension()-1){
			south=table.getCellAt(c.row+1, c.column);
			Relation r=g.getSouth();
			tmp=south.getValue();
			switch(r){
			case LESS : if (tmp!=GameCell.EMPTY_CELL&&value>tmp) return  false; break;
			case GREATER : if (tmp!=GameCell.EMPTY_CELL&&value<tmp) return false;break;
			default : ;
			}//switch
		}
		if(c.column>0){ 
			west=table.getCellAt(c.row, c.column-1);
			Relation r= west.getEast();
			tmp=west.getValue();
			switch(r){
			case LESS : if (tmp!=GameCell.EMPTY_CELL&&value<tmp) return  false; break;
			case GREATER : if (tmp!=GameCell.EMPTY_CELL&&value>tmp) return false;break;
			default : ;
			}//switch
		}
		
		if(c.column<table.getDimension()-1){
			east=table.getCellAt(c.row, c.column+1);
			Relation r=g.getEast();
			tmp=east.getValue();
			switch(r){
			case LESS : if(tmp!=GameCell.EMPTY_CELL&&value>tmp) return false; break;
			case GREATER : if (tmp!=GameCell.EMPTY_CELL&&value< tmp) return false; break;
			default : ;
			}
		}
		/*------------fine controlli su relazioni--------------*/
		
		/*------------inizio controllo su valori uguali--------------*/
		
		for(int i=0;i<table.getDimension();i++){
			if(i==c.row) continue;
			if(value==table.getCellValueAt(i, c.column)&&value!=GameCell.EMPTY_CELL)return false;
		}
		for(int j=0;j<table.getDimension();j++){
			if(j==c.column) continue;
			if(value==table.getCellValueAt(c.row, j)&&value!=GameCell.EMPTY_CELL)return false;
		}
		/*------------fine controlli su valori uguali--------------*/
		return true;
	}

	@Override
	protected void assegna(Integer value, Cell c) {
		GameCell g=table.getCellAt(c.row, c.column);
		if(g.isDefault())return;
		table.setCellValueAt(c.row, c.column, value,false);
	}
	@Override
	protected void deassegna(Integer scelta, Cell c) {
		GameCell g=table.getCellAt(c.row, c.column);
		if(g.isDefault())return;
		table.setCellValueAt(c.row, c.column, GameCell.EMPTY_CELL,false);
	}
	@Override
	protected Cell precedentePuntoDiScelta(Cell c) {
		for(int i=c.row;i>=0;i--){
			for(int j=(c.row==i)?c.column-1:table.getDimension()-1;j>=0;j--){
				GameCell g=table.getCellAt(i, j);
				//System.err.println(g.isDefault());
				if(!g.isDefault()) return new Cell(i,j);
			}
		}
		return new Cell(0,0);
	}
	@Override
	protected Integer ultimaSceltaAssegnata(Cell c) {
		return table.getCellValueAt(c.row, c.column);
	}
	@Override
	protected void scriviSoluzione(int nrsol) {
		//System.err.println("("+nrsol+")");
		//System.err.println(table);
		soluzioni.add(new GameTable(table));
	}
	@Override
	public String tabellaInCostruzione() {
		return table.toString();
	}
	public List<GameTable> getSoluzioni(){
		return soluzioni;
	}
	
	
}
