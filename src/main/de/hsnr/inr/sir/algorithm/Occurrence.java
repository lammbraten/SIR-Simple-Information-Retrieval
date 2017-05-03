package de.hsnr.inr.sir.algorithm;

public class Occurrence {

	private String docId;
	private int pos1;
	private int pos2;
	
	public Occurrence(String docId, int pos1, int pos2){
		setDocId(docId);
		setPos1(pos1);
		setPos2(pos2);
		
	}
	
	@Override
	public String toString(){
		return docId + ": " + pos1 + ", " + pos2;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(!(o instanceof Occurrence))
			return false;
		Occurrence occ = (Occurrence) o;
		if(this.docId.equals(occ.docId) &&
				this.pos1 == occ.pos1 &&
				this.pos2 == occ.pos2)
			return true;
		return false;
	}
	
	
	
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
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
