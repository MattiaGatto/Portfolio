package Testing;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import Futoshiki.Futoshiki;
import Futoshiki.GameCell;
import Futoshiki.GameTable;
import Futoshiki.Relation;

public class Testing {
	
	@Test
	public void prova1() {
		GameTable gt= new GameTable(6);
		gt.setCellAt(0, 0, new GameCell(1));
		gt.setCellAt(3, 3, new GameCell(6,Relation.GREATER,Relation.NONE));
		gt.setCellAt(3, 4, new GameCell(GameCell.EMPTY_CELL,Relation.GREATER,Relation.NONE));
		gt.setCellAt(4, 4, new GameCell(GameCell.EMPTY_CELL,Relation.LESS,Relation.NONE));
		gt.setCellAt(4, 3, new GameCell(GameCell.EMPTY_CELL,Relation.NONE,Relation.LESS));
		Futoshiki f=new Futoshiki(gt,3);
		f.risolvi();
		List<GameTable> solu=f.getSoluzioni();
		int x=solu.size();
		assertEquals(3, x);
	}
	@Test(expected=Error.class)
	public void prova2() {
		GameTable gt= new GameTable(6);
		gt.setCellAt(0, 0, new GameCell(1));
		gt.setCellAt(3, 3, new GameCell(6,Relation.GREATER,Relation.NONE));
		gt.setCellAt(3, 4, new GameCell(GameCell.EMPTY_CELL,Relation.GREATER,Relation.NONE));
		gt.setCellAt(4, 4, new GameCell(GameCell.EMPTY_CELL,Relation.LESS,Relation.NONE));
		gt.setCellAt(4, 3, new GameCell(GameCell.EMPTY_CELL,Relation.NONE,Relation.LESS));
		Futoshiki f=new Futoshiki(gt,1);
		f.risolvi();
		List<GameTable> solu=f.getSoluzioni();
		int x=solu.size();
		assertEquals(4, x);
	}
	@Test
	public void prova3() {
		GameTable gt= new GameTable(2);
		gt.setCellAt(0, 0, new GameCell(1));
		gt.setCellAt(0, 1, new GameCell(2,Relation.NONE,Relation.GREATER));
		gt.setCellAt(1, 0, new GameCell(GameCell.EMPTY_CELL,Relation.GREATER,Relation.NONE));
		Futoshiki f=new Futoshiki(gt,3);
		f.risolvi();
		List<GameTable> solu=f.getSoluzioni();
		GameTable s=solu.get(0);
		GameTable s2=gt;
		s2.setCellValueAt(0, 0, 1);
		s2.setCellValueAt(0, 1, 2);
		s2.setCellValueAt(1, 0, 2);
		s2.setCellValueAt(1, 1, 1);
		assertEquals(s2.toString(), s.toString());
	}
	@Test(expected=Error.class)
	public void prova4() {
		GameTable gt= new GameTable(2);
		gt.setCellAt(0, 0, new GameCell(1));
		gt.setCellAt(0, 1, new GameCell(2,Relation.NONE,Relation.GREATER));
		gt.setCellAt(1, 0, new GameCell(GameCell.EMPTY_CELL,Relation.GREATER,Relation.NONE));
		Futoshiki f=new Futoshiki(gt,1);
		f.risolvi();
		List<GameTable> solu=f.getSoluzioni();
		GameTable s=solu.get(0);
		GameTable s2=gt;
		s2.setCellValueAt(0, 0, 1);
		s2.setCellValueAt(0, 1, 2);
		s2.setCellValueAt(1, 0, 2);
		s2.setCellValueAt(1, 1, 2);
		assertEquals(s2.toString(), s.toString());
	}
	
	@Test(timeout = 10000)
	public void prova5() {
		GameTable gt= new GameTable(10);
		gt.setCellAt(0, 0, new GameCell(1));
		gt.setCellAt(0, 1, new GameCell(2,Relation.NONE,Relation.GREATER));
		gt.setCellAt(1, 0, new GameCell(GameCell.EMPTY_CELL,Relation.GREATER,Relation.NONE));
		Futoshiki f=new Futoshiki(gt,1);
		f.risolvi();
		List<GameTable> solu=f.getSoluzioni();
	}
	
	

}
