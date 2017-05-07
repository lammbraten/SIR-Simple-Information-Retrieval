package de.hsnr.inr.sir.dictionary;

public class Occurrence extends Posting{
	private int pos1;
	private int pos2;
	
	public Occurrence(String docId, int pos1, int pos2){
		super(docId);
		setPos1(pos1);
		setPos2(pos2);
		
	}
	
	@Override
	public String toString(){
		return name + ": " + pos1 + ", " + pos2;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if((o instanceof Occurrence)){
			Occurrence occ = (Occurrence) o;
			if(this.name.equals(occ.name) &&
					this.pos1 == occ.pos1 &&
					this.pos2 == occ.pos2)
				return true;
			return false;
		}
		if(o instanceof Posting)
			return ((Posting) o).equals(this);
		return false;
	}
	
	
	
	public String getDocId() {
		return name;
	}

	public void setDocId(String docId) {
		this.name = docId;
	}

	public int getPos1() {
		return pos1;
	}

	public void setPos1(int pos1) {
		this.pos1 = pos1;
	}

	public int getPos2() {
		return pos2;
	}

	public void setPos2(int pos2) {
		this.pos2 = pos2;
	}
}
