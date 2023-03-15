import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CNNAPI {
    private static double[][] multiplyArrays(double[][] a, double[][] b) {
        int n = a.length;
        int m = a[0].length;
        double[][] result = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = a[i][j] * b[i][j];
            }
        }

        return result;
    }

    @PostMapping("/convolution")
    public double[][] convolution(@RequestBody double[][] image) {
        // Assume input image is a 2D array with dimensions n x m
        int n = image.length;
        int m = image[0].length;
        double[][] kernel = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}}; // 3x3 kernel
        double[][] output = new double[n - 2][m - 2]; // Output dimensions will be (n-2) x (m-2)

        // Perform convolution
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                // Extract 3x3 sub-image
                double[][] subImage = {{image[i-1][j-1], image[i-1][j], image[i-1][j+1]},
                                       {image[i][j-1], image[i][j], image[i][j+1]},
                                       {image[i+1][j-1], image[i+1][j], image[i+1][j+1]}};
                // Multiply sub-image by kernel and sum the elements
                double sum = 0;
                double[][] product = multiplyArrays(subImage, kernel);
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        sum += product[k][l];
                    }
                }
                output[i-1][j-1] = sum; // Store result in output array
            }
        }

        return output;
    }

    public static void main(String[] args) {
        SpringApplication.run(CNNAPI.class, args);
    }
}
