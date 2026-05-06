public class ContrastiveLossOptimization {

    /**
     * 計算兩個特徵向量之間的 L2 距離 (Euclidean Distance)
     * 對應公式中的 ||D(x1) - D(x2)||_2
     */
    public static double calculateEuclideanDistance(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("特徵向量維度必須相同！");
        }
        
        double sumOfSquaredDifferences = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            double diff = vector1[i] - vector2[i];
            sumOfSquaredDifferences += diff * diff;
        }
        return Math.sqrt(sumOfSquaredDifferences);
    }

    /**
     * 計算負樣本的對比損失 (Contrastive Loss)
     * 對應公式: L = max(0, C - distance)
     */
    public static double optimizeDistanceEquation(double[] anchor, double[] negative, double margin) {
        double distance = calculateEuclideanDistance(anchor, negative);
        // 如果距離大於等於 margin，損失為 0；否則計算需要拉開的懲罰值
        return Math.max(0.0, margin - distance);
    }

    public static void main(String[] args) {
        // 模擬從圖片萃取出的高維度特徵陣列 (Feature Vectors)
        
        // 基準樣本 x1 (對應 wa.png - 坐姿赤柴)
        double[] anchorFeature = {0.88, 0.75, 0.12, 0.45, 0.90}; 
        
        // 正樣本 x2+ (對應 wb.png - 坐姿赤柴，視覺特徵高度相似)
        double[] positiveFeature = {0.86, 0.77, 0.11, 0.46, 0.88}; 
        
        // 負樣本 x2- (對應 ba_2.png - 站立黑柴，視覺特徵差異大)
        double[] negativeFeature = {0.20, 0.30, 0.85, 0.15, 0.22}; 
        
        // 設定邊際常數 C (Margin)
        double marginC = 2.0;

        System.out.println("====== Contrastive Learning: Optimize Distance Equation ======\n");

        // 1. 計算正樣本對的距離
        double posDistance = calculateEuclideanDistance(anchorFeature, positiveFeature);
        System.out.printf("[正樣本] 兩張赤柴的特徵距離: %.4f\n", posDistance);

        // 2. 計算負樣本對的距離與損失值
        double negDistance = calculateEuclideanDistance(anchorFeature, negativeFeature);
        double lossValue = optimizeDistanceEquation(anchorFeature, negativeFeature, marginC);
        
        System.out.printf("[負樣本] 赤柴與黑柴的特徵距離: %.4f\n", negDistance);
        System.out.printf("設定之邊際值 (Margin C): %.1f\n", marginC);
        System.out.printf("👉 計算得出之損失值 (Loss): %.4f\n", lossValue);
    }
}