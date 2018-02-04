import javax.swing.JOptionPane;

public class Polynomial_ADT {

	public static void main(String[] args) {
		
		int more = 0;     
		 //this loop is for allowing the user to continue using the program until they wish to exit
		while (more == 0) {                        
			PolyLinkedList poly1 = new PolyLinkedList();

			PolyLinkedList poly2 = new PolyLinkedList();

			String input1 = JOptionPane.showInputDialog("Please enter polynomial 1: ");
			poly1.insertString(input1);

			String input2 = JOptionPane.showInputDialog("Please enter polynomial 2: ");
			poly2.insertString(input2);

			boolean choose = false;
			while (choose == false) {
				int choice = Integer.parseInt(JOptionPane.showInputDialog(""
						+ "\nMake a Selection from the following"
						+ "\n1 Add Polynomials" 
						+ "\n2 Multiply Polynomials" 
						+ "\n3 Log Out"));
				switch (choice) {
				case 1:
					JOptionPane.showMessageDialog(null, poly1.add(poly2));
					choose = true;
					break;

				case 2:
					JOptionPane.showMessageDialog(null, poly1.multiply(poly2));
					choose = true;
					break;

				case 3:
					more = 1;
					choose = true;
					break;

				default:
					JOptionPane.showMessageDialog(null, "Incorrect Option");
					choose = false;
					break;
				}

			}
			
			more = JOptionPane.showConfirmDialog(null, "Do you want to go again", //Loop escape function
					"or log out", JOptionPane.YES_NO_OPTION);
			
			if(more == 1)
				JOptionPane.showMessageDialog(null, "Have a great day!");

		}
	}
}

//This class is for making a object for containing the coeffeicent and degree of each term in the form of a node.
class PolyNode {

	double coef;
	int expo;
	public PolyNode next;

	public PolyNode() {
		next = null;
		coef = 0;
		expo = 0;
	}

	public PolyNode(double c, int e, PolyNode n) {
		next = n;
		coef = c;
		expo = e;
	}

	public void setLink(PolyNode n) {
		next = n;
	}

	public void setData(double c, int e) {
		coef = c;
		expo = e;
	}

	public PolyNode getLink() {
		return next;
	}

	public double getCoef() {
		return coef;
	}

	public int getExpo() {
		return expo;
	}

}

class PolyLinkedList {

	PolyNode start;
	PolyNode end;
	int size;

	public PolyLinkedList() {
		start = null;
		end = null;
		size = 0;
	}

	public int getSize() {
		return size;
	}
	
	//this method is for reading a string and adding each polynomial term into the Polylinkedlist 
	//this method is special tuned for reading a variety of different inputs the user could enter.
	public void insertString(String str) {

		for (int i = 0; i < str.length(); i++) {
			//an x is located and then the value of the coefficent and degree on it are inputed.
			if (str.charAt(i) == 'x') {
				String c = "";
				String e = "";
				
				if(str.charAt(i - 1) == '+' || str.charAt(i - 1) == '-')
					c = "1" + c;
				else
					c = str.charAt(i - 1) + c;
				
				//this is for inserting a negative coef for any integer coef with a minus in front. 
				if ((i - 2) != -1 && str.charAt(i - 2) == '-')
					c = "-" + c;
				
				//this is for inserting a coef of -1 for an input staring with -x^something + something
				if((i - 1) != -1 && str.charAt(i - 1) == '-')    
					c = "-" + c;

				//this for 
				if (i < str.length() - 1 && (str.charAt(i + 1) == '+' || str.charAt(i + 1) == '-'))
					e += 1;
				else {
					if (i == str.length() - 1) {
						e += 1;
					} else
						e += str.charAt(i + 2);
				}

				this.insertTerm(Double.parseDouble(c), Integer.parseInt(e));

			}
		}

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '+' || str.charAt(i) == '-') {
				for (int j = i + 1; j < str.length() && str.charAt(j) != 'x'; j++) {
					if (j < str.length() && (str.charAt(j) == '+' || str.charAt(j) == '-'))
						this.insertTerm(Double.parseDouble(str.substring(i + 1, j)), 0);
					if (j == str.length() - 1)
						this.insertTerm(Double.parseDouble(str.substring(i + 1)), 0);
				}
			}
		}

	}
	
	//This is for inserting a term into the PolyLinkedList in the form of a polynode
	public void insertTerm(double c, int e) {
		PolyNode ptr = new PolyNode(c, e, null);
		if (present(ptr)) {   // this is to check weather a degree of this new term is already present and if so add the coeffeicents 

		} else {
			ptr = isGreater(ptr); //this is to make the sure the polyLinkedList is order of greater degree to least degree.
			if (start == null) {
				start = ptr;
				end = start;
			} else {
				end.setLink(ptr);
				end = ptr;
			}
			size++;

		}

	}
	
	//This is for adding two polynomials and returning the result as a new polynomial
	public PolyLinkedList add(PolyLinkedList p) {
		PolyLinkedList temp = new PolyLinkedList();

		PolyNode t1 = this.start;

		for (int i = 0; i < this.getSize(); i++) {
			temp.insertTerm(t1.getCoef(), t1.getExpo());	//The inserting term method already takes care of the adding new terms to a polynomial.
			t1 = t1.getLink();
		}

		PolyNode t2 = p.start;

		for (int i = 0; i < p.getSize(); i++) {
			temp.insertTerm(t2.getCoef(), t2.getExpo());
			t2 = t2.getLink();
		}

		return temp;
	}
	
	//This is for multiplying two polynomials and returning the result as a new polynomial
	public PolyLinkedList multiply(PolyLinkedList p) {
		PolyLinkedList temp = new PolyLinkedList();
		PolyNode t1 = this.start;
		
		//A nested for loop is used for multiplying all terms of each polynomial together
		for (int i = 0; i < this.getSize(); i++) {

			PolyNode t2 = p.start;

			for (int j = 0; j < p.getSize(); j++) {

				temp.insertTerm(t1.getCoef() * t2.getCoef(), t1.getExpo() + t2.getExpo());

				t2 = t2.getLink();
			}

			t1 = t1.getLink();
		}

		return temp;
	}
	
	//This for displaying the polynomial in the proper format.
	public String toString() {
		PolyNode temp = this.start;
		String output = "";

		for (int i = 0; i < this.getSize(); i++) {

			if (temp.getExpo() > 1 || temp.getExpo() < 0)
				output += temp.getCoef() + "x^" + temp.getExpo();
			else {
				if (temp.getExpo() == 1)
					output += temp.getCoef() + "x";
				else
					output += temp.getCoef();
			}

			if (i == (this.getSize() - 1)) {

			} else
				output += " + ";

			temp = temp.getLink();
		}

		return output;
	}
	
	// this is to check weather a degree of this new term is already present and if so add the coeffeicents
	public boolean present(PolyNode ptr) {
		PolyNode t1 = this.start;
		boolean here = false;

		for (int i = 0; i < this.getSize(); i++) {
			if (ptr.getExpo() == t1.getExpo()) {
				t1.setData(ptr.getCoef() + t1.getCoef(), t1.getExpo());
				here = true;
			}
			t1 = t1.getLink();
		}
		return here;
	}
	
	//this is to make the sure the polyLinkedList is order of greater degree to least degree.
	public PolyNode isGreater(PolyNode ptr) {
		PolyNode t1 = this.start;
		PolyNode temp = new PolyNode(ptr.getCoef(), ptr.getExpo(), null);
		boolean ordered = false;

		for (int i = 0; i < this.getSize(); i++) {
			if (ptr.getExpo() > t1.getExpo() && !ordered) {
				temp.setData(t1.getCoef(), t1.getExpo());
				t1.setData(ptr.getCoef(), ptr.getExpo());

				ordered = true;
			} else
				t1 = t1.getLink();
		}

		return temp;
	}

}