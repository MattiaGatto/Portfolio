package Futoshiki;

public class GameCell {
	private Relation south=Relation.NONE;
	private Relation east=Relation.NONE;
	/**indica una cella vuota*/
	public static final int EMPTY_CELL=0;
	/**valore della cella*/
	private int value=EMPTY_CELL;
	/**definisce se il valore della cella è predefinito (inserito alla creazione della tabella) o modificabile */
	private boolean is_default=false;
	
	
	
	public GameCell(int value,Relation east,Relation south,boolean is_default){
		this.value=value;
		this.east=east;
		this.south=south;
		this.is_default=is_default;
	}
	
	public GameCell(int value,Relation east,Relation south){
		this(value,east,south,false);
		if(value!=EMPTY_CELL) is_default=true;
		
	}
	
	public GameCell(int value, String relation_east,String relation_south){
		this(value,stringToRelation(relation_east),stringToRelation(relation_south));
	}
	
	public GameCell(int value){
		this(value,Relation.NONE,Relation.NONE);
	}
	public GameCell(GameCell copy){
		this(copy.value,copy.east,copy.south,copy.is_default);
	}
	
	public GameCell(){this(EMPTY_CELL);}
	
	public void setValues(int value, Relation east, Relation south){
		setValue(value);
		setEastRelation(east);
		setSouthRelation(south);
	}
	
	public void setValues(int value,String east,String south){
		setValues(value,stringToRelation(east),stringToRelation(south));
	}
	
	public void setValue(int value){
		this.value=value;
	}
	public void setEastRelation(Relation east){
		this.east=east;
	}
	public void setEastRelation(String east){
		setEastRelation(stringToRelation(east));
	}
	public void setSouthRelation(Relation south){
		this.south=south;
	}
	public void setSouthRelation(String south){
		setSouthRelation(stringToRelation(south));
	}
	public int getValue (){
		return value;
	}
	public Relation getEast(){
		return east;
	}
	public Relation getSouth(){
		return south;
	}
	public void setIsDefault(boolean is_default){
		this.is_default=is_default;
	}
	public boolean isDefault(){return is_default;}
	
	public static Relation stringToRelation(String relation){
		if(relation==null)return Relation.NONE;
		relation=relation.toUpperCase().trim();
		for(Relation r:Relation.values()){
			if(r.toString().equals(relation)) return r;
		}
		return Relation.NONE;
	}//stringToRelation
	
	public String toString (){
		return "value="+value+";east="+east+";south="+south+";";
	}

}
