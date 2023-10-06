import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvexHullCalculator {

    public static List<int[]> convexHull(int[][] points) {
        int n = points.length;

        if (n < 3) {
            return convertArrayToList(points);
        }

        sortPointsByX(points);

        List<int[]> upper = new ArrayList<>();
        List<int[]> lower = new ArrayList<>();

        // Compute upper hull
        for (int[] point : points) {
            while (upper.size() >= 2 && isNotRightTurn(upper.get(upper.size() - 2), upper.get(upper.size() - 1), point)) {
                upper.remove(upper.size() - 1);
            }
            upper.add(point);
        }

        // Compute lower hull
        for (int i = n-1; i >= 0; i--) {
            while (lower.size() >= 2 && isNotRightTurn(lower.get(lower.size() - 2), lower.get(lower.size() - 1), points[i])) {
                lower.remove(lower.size() - 1);
            }
            lower.add(points[i]);
        }

        // Merge the two hulls
        upper.addAll(lower);
        return upper;
    }

    private static List<int[]> convertArrayToList(int[][] points) {
        List<int[]> list = new ArrayList<>();
        for (int[] point : points) {
            list.add(point);
        }
        return list;
    }

    private static void sortPointsByX(int[][] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i][0] > points[j][0] || (points[i][0] == points[j][0] && points[i][1] > points[j][1])) {
                    int[] temp = points[i];
                    points[i] = points[j];
                    points[j] = temp;
                }
            }
        }
    }

    private static boolean isNotRightTurn(int[] a, int[] b, int[] c) {
        return crossProduct(a, b, c) <= 0;
    }

    private static int crossProduct(int[] a, int[] b, int[] c) {
        int x1 = b[0] - a[0];
        int y1 = b[1] - a[1];
        int x2 = c[0] - b[0];
        int y2 = c[1] - b[1];
        return x1 * y2 - x2 * y1;
    }

    public static void main(String[] args) {
        int[][] points = { { 4, 5 }, { -6, 2 }, { -1, 3 },
                { -4, -4 }, { 6, -5 }, { 3, 3 },
                { 3, -2 }, { 5, -2 }, {4,4}, {-3,6}, {8,2},
                {1,1}, {2,-4}, { -3, -1}};

        List<int[]> result = convexHull(points);
        for (int[] point : result) {
            System.out.println(Arrays.toString(point));
        }
    }
}