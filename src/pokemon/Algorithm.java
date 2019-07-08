/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon;

import java.awt.Point;

/**
 *
 * @author MON MINA
 */
public class Algorithm {
    private int[][] value;
    
    public Algorithm(){}
    public Algorithm(int[][] value){
        this.value = value;
    }
    
    private boolean checkLineX(int y1, int y2, int x) {
//	System.out.println("Kiểm tra hàng");
	// Điểm max và min theo cột
	int min = Math.min(y1, y2);
	int max = Math.max(y1, y2);
	// Chạy kiểm tra theo cột
	for (int y = min + 1; y < max; y++) {
            if (value[x][y] > 0) {
//		System.out.println("Gặp chướng ngại!");
    		return false;
            }
//            System.out.println("Thành công");
	}
	return true;
    }
    
    private boolean checkLineY(int x1, int x2, int y) {
//	System.out.println("Kiểm tra cột");
	int min = Math.min(x1, x2);
	int max = Math.max(x1, x2);
	for (int x = min + 1; x < max; x++) {
            if (value[x][y] > 0) {
//		System.out.println("Gặp chướng ngại!");
		return false;
            }
//            System.out.println("Thành công");
	}
	return true;
    }
    
    private int checkRectX(Point p1, Point p2) {
//	System.out.println("Kiểm tra hình chữ nhật x");
                
	Point pMinY = p1, pMaxY = p2;
	if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
	}
	for (int y = pMinY.y; y <= pMaxY.y; y++) {
            if (y > pMinY.y && value[pMinY.x][y] > 0) {
		return -1;
            }
            // Kiểm tra 2 kiểu đường
            if ((value[pMaxY.x][y] == 0 || y == pMaxY.y)
                    && checkLineY(pMinY.x, pMaxY.x, y)
                    && checkLineX(y, pMaxY.y, pMaxY.x)) {
//		System.out.println("Hình chữ nhật x");
//		System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
//					+ pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
//					+ ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
		// Nếu đúng 3 đường thì trả về y
                return y;
		}
            }
        
        return -1;
    }
    
    private int checkRectY(Point p1, Point p2) {
//	System.out.println("Kiểm tra hình chữ nhật y");
	// Tìm điểm có y min
	Point pMinX = p1, pMaxX = p2;
	if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
	}
	// Tìm đường và bắt đầu từ y
	for (int x = pMinX.x; x <= pMaxX.x; x++) {
            if (x > pMinX.x && value[x][pMinX.y] > 0) {
		return -1;
            }
            if ((value[x][pMaxX.y] == 0 || x == pMaxX.x)
                && checkLineX(pMinX.y, pMaxX.y, x)
                && checkLineY(x, pMaxX.x, pMaxX.y)) {
//		System.out.println("Hình chữ nhật y");
//		System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x
//					+ "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
//					+ ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
		return x;
            }
	}
	return -1;
    }
    
    private int checkMoreLineX(Point p1, Point p2, int type) {
        //System.out.println("Kiểm tra mở rộng x");
	// Tìm điểm có min y
	Point pMinY = p1, pMaxY = p2;
	if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
	}
	// Tìm đường và bắt đầu từ y
	int y = pMaxY.y + type;
	int row = pMinY.x;
	int colFinish = pMaxY.y;
	if (type == -1) {
            colFinish = pMinY.y;
            y = pMinY.y + type;
            row = pMaxY.x;
            //System.out.println("Cột kết thúc = " + colFinish);
	}

	// Tìm cột kết thúc dòng

	// Kiểm tra
	if ((value[row][colFinish] == 0 || pMinY.y == pMaxY.y)
                    && checkLineX(pMinY.y, pMaxY.y, row)) {
            while (value[pMinY.x][y] == 0
                        && value[pMaxY.x][y] == 0) {
		if (checkLineY(pMinY.x, pMaxY.x, y)) {
//                    System.out.println("Trường Hợp X " + type);
//                    System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
//					+ pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
//					+ ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                    return y;
		}
		y += type;
            }
	}
	return -1;
    }
    
    private int checkMoreLineY(Point p1, Point p2, int type) {
	//System.out.println("Kiểm tra mở rộng y");
	Point pMinX = p1, pMaxX = p2;
	if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
	}
	int x = pMaxX.x + type;
	int col = pMinX.y;
	int rowFinish = pMaxX.x;
	if (type == -1) {
            rowFinish = pMinX.x;
            x = pMinX.x + type;
            col = pMaxX.y;
	}
        if ((value[rowFinish][col] == 0 || pMinX.x == pMaxX.x)
                    && checkLineY(pMinX.x, pMaxX.x, col)) {
            while (value[x][pMinX.y] == 0
                    && value[x][pMaxX.y] == 0) {
		if (checkLineX(pMinX.y, pMaxX.y, x)) {
//                    System.out.println("Trường hợp Y " + type);
//                    System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> ("
//                                        + x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
//					+ ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                    return x;
		}
		x += type;
            }
	}
        return -1;
    }
    
    public int checkTwoPoint(Point p1, Point p2) {
        
        if ((!p1.equals(p2)) && (value[p1.x][p1.y] == value[p2.x][p2.y])) {
            // Kiểm tra đường x
            if (p1.x == p2.x) {
                if (checkLineX(p1.y, p2.y, p1.x)) {
                    //System.out.println("Dòng x");
                    return 1;
                }
            }
            // Kiểm tra đường y
            if (p1.y == p2.y) {
                if (checkLineY(p1.x, p2.x, p1.y)) {
                    //System.out.println("Dòng y");
                    return 2;
                }
            }

            // Kiểm tra hình chữ nhật x
            if ((checkRectX(p1, p2)) != -1) {
                //System.out.println("Hình chữ nhật x");
                return 3;
            }
            // Kiểm tra hình chữ nhật y
            if ((checkRectY(p1, p2)) != -1) {
                //System.out.println("Hình chữ nhật y");
                return 4;
            }
            // Kiểm tra mở rộng phải
            if ((checkMoreLineX(p1, p2, 1)) != -1) {
                //System.out.println("Mở rộng phải");
                return 5;
            }
            // Kiểm tra mở rộng trái
            if ((checkMoreLineX(p1, p2, -1)) != -1) {
                //System.out.println("Mở rộng trái");
                return 6;
            }
            // Kiểm tra mở rộng dưới
            if ((checkMoreLineY(p1, p2, 1)) != -1) {
                //System.out.println("Mở rộng dưới");
                return 7;
            }
            // Kiểm tra mở rộng trên
            if ((checkMoreLineY(p1, p2, -1)) != -1) {
                //System.out.println("Mở rộng trên");
                return 8;
            }
        }
	return -1;
    }
    

    /*
     * Tìm cặp hình giống nhau và trả về vị trí của 2 hình
     * Hình 1 có vị trí [i][j]
     * Hình 2 có vị trí [k][l]
     * Trả giá trị -1 nếu không tìm thấy cặp nào giống nhau
     */
    public int[] findPair(){
        for(int i=1; i<=10; i++)
            for(int j=1; j<=10; j++)
                for(int k=1; k<=10; k++)
                    for(int l=1; l<=10; l++){
                        if(value[i][j]!=0 && value[k][l]!=0){
                            Point p1 = new Point(i, j);
                            Point p2 = new Point(k, l);
                            if(checkTwoPoint(p1, p2) > 0){
                                return new int[] {i, j, k, l};
                            }
                        }
                    }
                        
        return new int[] {-1};
    }

}
