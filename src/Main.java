public class Main {

    public static int startX = 0;
    public static int endX = 1;
    public static double h = 0.1; //шаг
    public static int n = (int) ((endX - startX)/h);

    public static double function(double x, double y) {
        return 0.6*Math.sin(x) - 1.25*y*y + 1;
    }

    public static double round(double value, int places) { //функция для округления
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static double[] rungeKuttaMethod(){

        double[] y = new double[n+1];
        y[0] = 0;

        for (int i = 1; i<=4; i++) {
            double x = startX + (i-1) * h;
            double k1 = function(x, y[i-1]);
            double k2 = function(x + 0.5*h, y[i-1] + 0.5 * h * k1);
            double k3 = function(x + 0.5*h, y[i-1] + 0.5 * h * k2);
            double k4 = function(x + h, y[i-1] + h * k3);

            y[i] = y[i-1] + h/6 * (k1 + 2*k2 + 2*k3 + k4);
        }

        return y;
    }

    public static double adamsMethod() {

        double[] x = new double[n+1];
        x[0] =  startX;

        for (int i = 1; i< n+1; i++) {
            x[i] = round(x[i-1] + h, 1);
        }

        double[] y = rungeKuttaMethod();
        double[] dY = new double[n+1];
        double[] q = new double[n+1];
        double[] delta1q = new double[n];
        double[] delta2q = new double[n-1];
        double[] delta3q = new double[n-2];
        double[] deltaY = new double[n+1];

        System.out.println("+----+-----+--------+--------+--------+--------+---------+---------+---------+");
        System.out.println("| i  |  x  |   y    | deltaY |   dY   |   q    | delta1q | delta2q | delta3q |");
        System.out.println("+----+-----+--------+--------+--------+--------+---------+---------+---------+");

        for (int i = 0; i < n; i++) {

            dY[i] = function(x[i], y[i]);
            q[i] = round(h*dY[i], 4);

            if (i>=1) {
                delta1q[i-1] = round(q[i] - q[i-1], 4);
            }

            if (i>=2) {
                delta2q[i-2] = round(delta1q[i-1] - delta1q[i-2], 4);
            }

            if (i>=3) {
                delta3q[i-3] = round(delta2q[i-2]-delta2q[i-3], 4);
                deltaY[i] = round((24*q[i]+12*delta1q[i-1] + 10*delta2q[i-2] + 9*delta3q[i-3])/24, 4);
                y[i+1] = round(y[i] + deltaY[i], 4);
            }

            String row = String.format("| %2d | %.1f | %6.4f | %6.4f | %6.4f | %6.4f | %7.4f | %7.4f | %7.4f |",
                    i,
                    x[i],
                    y[i],
                    (i >= 3) ? deltaY[i] : 0.0,
                    dY[i],
                    q[i],
                    (i >= 1) ? delta1q[i-1] : 0.0,
                    (i >= 2) ? delta2q[i-2] : 0.0,
                    (i >= 3) ? delta3q[i-3] : 0.0);

            System.out.println(row);

        }

        System.out.println("+----+-----+--------+--------+--------+--------+---------+---------+---------+");

        return y[n];
    }


    public static void main(String[] args) {

        double result = adamsMethod();
        System.out.println(result);
    }

}
