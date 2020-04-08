
public class Quadtree {
	
	private Ponto quinaSupEsq;
	private Ponto quinaInfDir;
	private No noAtual;
	private Quadtree[] childNode;
	/*
	 * topLeftTree ----> childNode[0]
	 * topRightTree ----> childNode[1]
	 * bottomLeftTree ----> childNode[2]
	 * bottomRightTree ----> childNode[3]
	 */
	
	public Quadtree(No no, Ponto superior, Ponto inferior) {
		
		this.noAtual = no;
		this.quinaSupEsq = superior;
		this.quinaInfDir = inferior;
		this.childNode = new Quadtree[4];
	}
	
	public Quadtree() {
	
		this.noAtual = null;
		this.quinaSupEsq = new Ponto();
		this.quinaInfDir = new Ponto();
		this.childNode = new Quadtree[4];
	}
	
	public Quadtree(Ponto superior, Ponto inferior) {
		
		this.quinaSupEsq = superior;
		this.quinaInfDir = inferior;
		this.noAtual = null;
		this.childNode = new Quadtree[4];
	}
	
	public No getRoot() {
		return this.noAtual;
	}
	
	public Ponto getSuperiorCorner() {
		return this.quinaSupEsq;
	}
	
	public Ponto getInferiorCorner() {
		return this.quinaInfDir;
	}
	
	public Ponto getRootCoordinate(int index) {
		return this.noAtual.coordenada;
	}
	
	public Quadtree[] getChildren() {
		return this.childNode;
	}
	
	public Quadtree getChildAt(int index) {
		return this.childNode[index];
	}
	
	public No getChildNodeAt(int index) {
		return this.childNode[index].noAtual;
	}
	
	public Ponto getChildCoordinateAt(int index) {
		return this.childNode[index].noAtual.coordenada;
	}
	
	public int getChildrenSize() {
		
		int quantity = 0;
		for (;quantity <= this.childNode.length -1; quantity++) {
			
			if (this.childNode[quantity] == null) {
				
				return quantity;
			}
		}
		
		return quantity;
	}
	
	public void clearTree() {
		
		for (Quadtree node : this.childNode) {
			
			if (node != null) {
				
				node.clearTree();
				node = null;
			}
		}
	}
	
	public void insertNode(No novoNo) {
		
		if (novoNo == null) { return; }
		
		if(!this.contains(novoNo.coordenada)) { return; }
		
		if (Math.abs(this.quinaSupEsq.getX() - this.quinaInfDir.getX()) <= 1 && Math.abs(this.quinaSupEsq.getY() - this.quinaInfDir.getY()) <= 1) { 
		        if (this.noAtual == null) {	this.noAtual = novoNo; }
		        return;
	    }
		
		if ((this.quinaSupEsq.getX() + this.quinaInfDir.getX()) / 2 >= novoNo.coordenada.getX()) { 
	        if ((this.quinaSupEsq.getY() + this.quinaInfDir.getY()) / 2 >= novoNo.coordenada.getY()) { // Indicates topLeftTree childNode[0]
	            if (this.childNode[0] == null) {
	            	
	            	this.childNode[0] = createNode(0, this.quinaSupEsq, this.quinaInfDir);
	            }
	            this.childNode[0].insertNode(novoNo); 
	        } else { // indicates bottomLeftTree childNode[2]
	        	
	        	if (this.childNode[2] == null) {
	        		
	        		this.childNode[2] = createNode(2, this.quinaSupEsq, this.quinaInfDir);
	        	}
	        	this.childNode[2].insertNode(novoNo);
	        }
	    } else {
	    	
	    	if ((this.quinaSupEsq.getY() + this.quinaInfDir.getY()) / 2 >= novoNo.coordenada.getY()) { // Indicates topRightTree childNode[1]
	    		if (this.childNode[1] == null) {
	    			
	    			this.childNode[1] = createNode(1, this.quinaSupEsq, this.quinaInfDir);
	    		}
	    		this.childNode[1].insertNode(novoNo);
	    	} else { // indicates bottomRightTree childNode[3]
	    		
	    		if (this.childNode[3] == null) {
	    			
	    			this.childNode[3] = createNode(3, this.quinaSupEsq, this.quinaInfDir);
	    		}
	    		this.childNode[3].insertNode(novoNo);
	    	}	        
	    }
		
	}
	
	public No searchNodeByCoordinate(Ponto p) {
		
		if (!this.contains(p)) { return null; }
		if (this.noAtual != null) { return this.noAtual; }
		if ((this.quinaSupEsq.getX() + this.quinaInfDir.getX()) / 2 >= p.getX()) {

			if ((this.quinaSupEsq.getY() + this.quinaInfDir.getY()) / 2 >= p.getY()) { // Indicates topLeftTree childNode[0]
	
				if (this.childNode[0] == null) { return null; }
				return this.childNode[0].searchNodeByCoordinate(p); 
			} else {  // Indicates bottomLeftTree childNode[2]
	
				if (this.childNode[2] == null) { return null; }
				return this.childNode[2].searchNodeByCoordinate(p); 
	       } 
		} else {
			
			if ((this.quinaSupEsq.getY() + this.quinaInfDir.getY()) / 2 >= p.getY()) { // Indicates topRightTree childNode[1]
				
				if (this.childNode[1] == null) { return null; }
				return this.childNode[1].searchNodeByCoordinate(p);
			} else { // Indicates bottomRightTree childNode[3]
				
				if (this.childNode[3] == null) { return null; }
				return this.childNode[3].searchNodeByCoordinate(p);
			}
		}
	}
	
	private static Quadtree createNode(int index, Ponto superior, Ponto inferior) {
		
		if (index ==  0) {
			
			return new Quadtree(new Ponto(superior.getX(), superior.getY()),
					new Ponto((superior.getX() + inferior.getX()) / 2, (superior.getY() + inferior.getY()) / 2));			
		} else if (index == 1) {
			
			return new Quadtree(new Ponto((superior.getX() + inferior.getX()) / 2, superior.getY()), 
					new Ponto(inferior.getX(), (superior.getY() + inferior.getY()) / 2));
		} else if (index == 2) {
			
			return new Quadtree (new Ponto(superior.getX(), (superior.getY() + inferior.getY()) / 2), 
                    new Ponto((superior.getX() + inferior.getX()) / 2, inferior.getY()));
		} else if (index == 3) {
			
			return new Quadtree(new Ponto((superior.getX() + inferior.getX()) / 2, (superior.getY() + inferior.getY()) / 2), 
					new Ponto(inferior.getX(), inferior.getY()));
		}
		
		return null;
	}
	
	private boolean contains(Ponto p) { 
	    return this.inBoundary(p); 
	}
	
	private boolean inBoundary(Ponto p) {

		return (p.getX() >= this.quinaSupEsq.getX() && 
		        p.getX() <= this.quinaInfDir.getX() && 
		        p.getY() >= this.quinaSupEsq.getY() && 
		        p.getY() <= this.quinaInfDir.getY());
	}
}
