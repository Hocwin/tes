import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Implementasi_AVL_Tree {

	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		AvlTree avl = new AvlTree();

		avl.insert(25);
		avl.insert(7);
		avl.insert(-3);
		avl.insert(35);
		avl.insert(33);
		avl.insert(22);
		avl.insert(67);
		avl.insert(27);
		avl.insert(29);
		avl.insert(77);
		avl.insert(34);
		avl.insert(24);
		avl.insert(5);
		avl.insert(72);
		avl.insert(23);

		System.out.println("Inorder:");
		avl.inOrder();
		System.out.println();
		System.out.println("Preorder");
		avl.preOrder();
		System.out.println();
		System.out.println("Postorder");
		avl.postOrder();
		System.out.println("\n");
		
		avl.delete_succesor(60);
		System.out.println("Setelah delete succesor 60");
		System.out.println("Inorder:");
		avl.inOrder();
		System.out.println();
		System.out.println("Preorder");
		avl.preOrder();
		System.out.println();
		System.out.println("Postorder");
		avl.postOrder();
		System.out.println("\n");
		
		avl.delete_predeccesor(65);
		System.out.println("Setelah delete predeccesor 65");
		System.out.println("Inorder:");
		avl.inOrder();
		System.out.println();
		System.out.println("Preorder");
		avl.preOrder();
		System.out.println();
		System.out.println("Postorder");
		avl.postOrder();
		System.out.println("\n");
		
		//avl.cariNode(55);
		System.out.print("Masukkan node yang di cari = ");
		int n = Integer.parseInt(br.readLine());
		
		System.out.print("Node " + n + " ");
		avl.cariNode(n);
		
		int hitungNode = avl.hitungNode();
		System.out.println("Jumlah Node: " + hitungNode);
		
		boolean isTreeBalanced = avl.isBalanced();
		System.out.println("Is AVL Tree Balanced: " + isTreeBalanced);

		int treeHeight = avl.getTreeHeight();
		System.out.println("Tree Height: " + treeHeight);

		System.out.println("AVL Tree Structure:");
		avl.printTreeStructure();
		
		 System.out.println("Is AVL Tree Empty: " + avl.isEmpty());

		 // Menambahkan pemanggilan method levelOrder
		 System.out.println("Level Order Traversal:");
		 avl.levelOrder();

		 // Menambahkan pemanggilan method deleteValue
		 System.out.print("Masukkan node yang ingin dihapus = ");
		 int nodeToDelete = Integer.parseInt(br.readLine());
		 avl.deleteValue(nodeToDelete);

		 System.out.println("Setelah menghapus node " + nodeToDelete);
		 avl.inOrder();
		 System.out.println();
		 
		 avl.clear();
	}

}

class AvlNode<AnyType> {
	AnyType element;
	AvlNode<AnyType> left;
	AvlNode<AnyType> right;
	int height;

	public AvlNode(AnyType e, AvlNode<AnyType> l, AvlNode<AnyType> r) {
		element = e;
		left = l;
		right = r;
	}

	public String tString() {
		return left + " " + right;
	}
}

class AvlTree<AnyType extends Comparable<? super AnyType>> {
	private AvlNode<AnyType> root;

	public AvlTree() {
		root = null;
	}

	private int height(AvlNode<AnyType> t) {
		return t == null ? -1 : t.height;
	}

	private AnyType elementAt(AvlNode<AnyType> t) {
		return t == null ? null : t.element;
	}

	public int max(int lh, int rh) {
		if (lh > rh) {
			return lh;
		}
		return rh;
	}

	private AvlNode<AnyType> case1(AvlNode<AnyType> k2) {
		AvlNode<AnyType> k1 = k2.left;

		k2.left = k1.right;
		k1.right = k2;

		k2.height = max(height(k2.left), height(k2.right)) + 1;
		k1.height = max(height(k1.left), k2.height) + 1;

		return k1;
	}

	private AvlNode<AnyType> case4(AvlNode<AnyType> k1) {
		AvlNode<AnyType> k2 = k1.right;

		k1.right = k2.left;
		k2.left = k1;

		k1.height = max(height(k1.left), height(k1.right)) + 1;
		k2.height = max(height(k2.right), k1.height) + 1;

		return k2;
	}

	private AvlNode<AnyType> case2(AvlNode<AnyType> k3) {
		k3.left = case4(k3.left); //// arah rotasi ke kiri
		return case1(k3); // arah rotasi ke kanan
	}

	private AvlNode<AnyType> case3(AvlNode<AnyType> k1) {
		k1.right = case1(k1.right); //// arah rotasi ke kanan
		return case4(k1); // arah rotasi ke kiri
	}

	public AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
		if (t == null) {
			t = new AvlNode<AnyType>(x, null, null);
		}
		// case 1 dan case 2 pohon mengalami lh
		else if (x.compareTo(t.element) < 0) {
			t.left = insert(x, t.left);
			if (height(t.left) - height(t.right) > 1) { // tidak seimbang (LH)
				if (x.compareTo(t.left.element) < 0) {
					t = case1(t); // single rotasi
				} else {
					t = case2(t); // double rotasi
				}
			}
		}
		// case 3 dan case 4 pohon mengalami rh
		else if (x.compareTo(t.element) > 0) {
			t.right = insert(x, t.right);
			if (height(t.right) - height(t.left) > 1) { // tidak seimbang
				if (x.compareTo(t.right.element) > 0) {
					t = case4(t);
				} else {
					t = case3(t);
				}
			}
		}
		t.height = max(height(t.left), height(t.right)) + 1;
		return t;
	}
	
	//Method Insert
	public void insert(AnyType x) {
		root = insert(x, root);
	}
	
	//Method inOrder
	private void inOrder(AvlNode<AnyType> t) {
		if (t != null) {
			if (t.left != null) {
				inOrder(t.left);
			}
			System.out.print(t.element + " ");
			if (t.right != null) {
				inOrder(t.right);
			}
		}
	}

	public void inOrder() {
		if (root == null) {
			System.out.println("Emptyt AVL Tree");
		} else {
			inOrder(root);
		}
	}
	
	// Method preOrder
	private void preOrder(AvlNode<AnyType> t) {

		if (t != null){

			System.out.print(t.element + " ");

			if (t.left != null)
			{
				preOrder(t.left);
			}

			if (t.right != null)
			{

				preOrder(t.right);
			}
		}
	}

	public void preOrder() {

		if (root == null) {
			System.out.println("Empty AVL Tree");
		}

		else {
			preOrder(root);
		}

	}
	
	//Method postOrder
	private void postOrder(AvlNode<AnyType> t) {

		if (t != null)
		{
			if (t.left != null)
			{
				postOrder(t.left);
			}

			if (t.right != null)
			{
				postOrder(t.right);
			}
			System.out.print(t.element + " ");
		}

	}

	public void postOrder() {

		if (root == null) {

			System.out.println("Empty AVL Tree");
		}
		else {
			postOrder(root);

		}

	}
	
	//Method Balance
	private AvlNode<AnyType> balance (AvlNode<AnyType> t){
		if(t == null) {
			return null;
		}
		if(height(t.left) - height(t.right) > 1) {	//lh
			if(height(t.left.left) >= height(t.left.right)) {	// left substring lh
				t = case1(t);
			}
			else {	// left substree rh
				t = case2(t);
			}
		}
		else if(height(t.right) - height(t.left) > 1) { // tree rh
			if(height(t.right.right) >= height(t.right.left)) {	// right subtree rh
				t = case4(t);
			}
			else{	// right subtree lh
				t = case3(t);
			}
		}
		t.height = max(height(t.left), height(t.right)) + 1;
		return t;
	}
	
	// Method delete succesor : kanan terkecil
	private AvlNode<AnyType> findMinimum (AvlNode<AnyType> t){
		if(t == null) {
			return null;
		}
		while(t.left != null) {
			t = t.left;
		}
		return t;
	}
	
	private AvlNode<AnyType> delete_succesor (AvlNode<AnyType> t, AnyType x){
		if(t == null) {
			return null;
		}
		if(x.compareTo(t.element) < 0) { // x < element
			t.left = delete_succesor(t.left, x);
		}
		else if(x.compareTo(t.element) > 0) {	// x > element
			t.right = delete_succesor(t.right, x);
		}
		else if(t.left != null && t.right != null) {	// punya 2 anak
			t.element = findMinimum(t.right).element;
			t.right = delete_succesor(t.right, t.element);
		}
		else {	// mempunyai 1 anak
			t = t.left != null ? t.left : t.right;
		}
		return balance(t);
	}
	
	public void delete_succesor(AnyType x) {
		root = delete_succesor(root, x);
	}
	
	// Method delete predeccesor : kiri terbesar
		private AvlNode<AnyType> findMaksimum (AvlNode<AnyType> t){
			if(t == null) {
				return null;
			}
			while(t.right != null) {
				t = t.right;
			}
			return t;
		}
		
		private AvlNode<AnyType> delete_predeccesor (AvlNode<AnyType> t, AnyType x){
			if(t == null) {
				return null;
			}
			if(x.compareTo(t.element) < 0) { // x < element
				t.left = delete_predeccesor(t.left, x);
			}
			else if(x.compareTo(t.element) > 0) {	// x > element
				t.right = delete_predeccesor(t.right, x);
			}
			else if(t.right != null && t.left != null) {	// punya 2 anak
				t.element = findMaksimum(t.left).element;
				t.left = delete_predeccesor(t.left, t.element);
			}
			else {	// mempunyai 1 anak
				t = t.right != null ? t.right : t.left;
			}
			return balance(t);
		}
		
		public void delete_predeccesor(AnyType x) {
			root = delete_predeccesor(root, x);
		}
		
		//Method Cari Node
		private AvlNode<AnyType> cariNode(AvlNode<AnyType> t, AnyType x) {
		    if (t == null || x == null) {
		        return null;
		    }

		    int bandingkan = x.compareTo(t.element);

		    if (bandingkan < 0) {
		        return cariNode(t.left, x);
		    } else if (bandingkan > 0) {
		        return cariNode(t.right, x);
		    } else {
		        return t;
		    }
		}

		public void cariNode(AnyType x) {
		    AvlNode<AnyType> hasil = cariNode(root, x);

		    if (hasil != null) {
		        System.out.println("Ketemu");
		    } else {
		        System.out.println("Tidak Ketemu");
		    }
		}
		
		//Method hitungNode
		private int hitungNode(AvlNode<AnyType> t) {
		    if (t == null) {
		        return 0;
		    }

		    int hitungKiri = hitungNode(t.left);
		    int hitungKanan = hitungNode(t.right);

		    return hitungKiri + hitungKanan + 1; // Count the current node
		}

		public int hitungNode() {
		    return hitungNode(root);
		}

        //Method cek sudah balace atau belum
		private boolean isBalanced(AvlNode<AnyType> t) {
		    if (t == null) {
		        return true;
		    }

		    int balanceFactor = height(t.left) - height(t.right);

		    return Math.abs(balanceFactor) <= 1 && isBalanced(t.left) && isBalanced(t.right);
		}

		public boolean isBalanced() {
		    return isBalanced(root);
		}
		
        //method hitung tinggi pohon
		public int getTreeHeight() {
		    return height(root);
		}

		public void printTreeStructure() {
		    printTreeStructure(root, 0);
		}

		private void printTreeStructure(AvlNode<AnyType> t, int level) {
		    if (t != null) {
		        printTreeStructure(t.right, level + 1);
		        for (int i = 0; i < level; i++) {
		            System.out.print("\t");
		        }
		        System.out.println(t.element);
		        printTreeStructure(t.left, level + 1);
		    }
		}
		
		// Method untuk menghapus seluruh node pada pohon AVL
		public void clear() {
		    root = null;
		}

		// Method untuk mengecek apakah pohon AVL kosong
		public boolean isEmpty() {
		    return root == null;
		}

		// Method untuk mendapatkan keseimbangan faktor (balance factor) suatu node
		private int balanceFactor(AvlNode<AnyType> t) {
		    return (t == null) ? 0 : height(t.left) - height(t.right);
		}

		// Method untuk menampilkan elemen-elemen pada suatu level tertentu
		private void printLevel(AvlNode<AnyType> t, int level, int currentLevel) {
		    if (t != null && currentLevel == level) {
		        System.out.print(t.element + " ");
		    } else if (t != null) {
		        printLevel(t.left, level, currentLevel + 1);
		        printLevel(t.right, level, currentLevel + 1);
		    }
		}

		// Method untuk menampilkan pohon AVL secara level-order
		public void levelOrder() {
		    if (root == null) {
		        System.out.println("Empty AVL Tree");
		    } else {
		        int height = height(root);
		        for (int i = 0; i <= height; i++) {
		            for (int j = 0; j < Math.pow(2, height - i); j++) {
		                System.out.print("\t");
		            }
		            printLevel(root, i, 0);
		            System.out.println();
		        }
		    }
		}

		// Method untuk menemukan dan menghapus suatu node dengan nilai tertentu
		private AvlNode<AnyType> deleteValue(AvlNode<AnyType> t, AnyType x) {
		    if (t == null) {
		        return null;
		    }

		    int compareResult = x.compareTo(t.element);

		    if (compareResult < 0) {
		        t.left = deleteValue(t.left, x);
		    } else if (compareResult > 0) {
		        t.right = deleteValue(t.right, x);
		    } else {
		        // Node ditemukan, lakukan operasi penghapusan
		        if (t.left != null && t.right != null) {
		            t.element = findMinimum(t.right).element;
		            t.right = deleteValue(t.right, t.element);
		        } else {
		            t = (t.left != null) ? t.left : t.right;
		        }
		    }

		    return balance(t);
		}

		// Method untuk menghapus suatu node dengan nilai tertentu
		public void deleteValue(AnyType x) {
		    root = deleteValue(root, x);
		}


}
