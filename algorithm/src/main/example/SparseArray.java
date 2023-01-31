package example;

/**
 * 稀疏数组
 */
public class SparseArray {
    public static void main(String[] args) {
        int[][] c = new int[11][11];
        c[1][2] = 1;
        c[2][3] = 2;
        for (int[] arr2 : c) {
            for (int item : arr2) {
                System.out.printf("%d\t",item);
            }
            System.out.println();
        }
        int sum= 0;
        for (int[] arr2 : c) {
            for (int item : arr2) {
                if (item != 0) {
                    sum++;
                }
            }
        }
        int arr[][] = new int[sum+1][3];
        arr[0][0] = 11;
        arr[0][1] = 11;
        arr[0][2] = sum;
        int count=0;
        for (int i = 0; i < c.length; i++) {
            int[] a = c[i];
            for (int j = 0; j < a.length; j++) {
                int b = a[j];
                if (b!=0) {
                    count++;
                    arr[count][0]=i;
                    arr[count][1]=j;
                    arr[count][2]=b;
                    continue;
                }
            }
        }
        System.out.println();
        System.out.println("得到的稀疏数组");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n",arr[i][0],arr[i][1],arr[i][2]);
        }
        int sparse2[][] = new int[arr[0][0]][arr[0][1]];
        for (int i = 1; i < arr.length; i++) {
            sparse2[arr[i][0]][arr[i][1]] = arr[i][2];
        }
        System.out.println("恢复数组");
        for (int[] arr2 : sparse2) {
            for (int item : arr2) {
                System.out.printf("%d\t",item);
            }
            System.out.println();
        }
    }

}
