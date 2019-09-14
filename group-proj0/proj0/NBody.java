public class NBody {
  public static double readRadius(String filename) {
		In in = new In(filename);
    int N = in.readInt();
    double R = in.readDouble();
    return R;
  }

  public static Body[] readBodies(String filename) {
    In in = new In(filename);
    int N = in.readInt();
    double R = in.readDouble();
    Body[] bodies = new Body[N];
    for (int i = 0; i < N; i+= 1) {
      double xP = in.readDouble();
      double yP = in.readDouble();
      double xV = in.readDouble();
      double yV = in.readDouble();
      double m = in.readDouble();
      String img = in.readString();
      Body b = new Body(xP, yP, xV, yV, m, img);
      bodies[i] = b;
    }
    return bodies;
  }

  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double R = readRadius(filename);
    Body[] bodies = readBodies(filename);
    String imageToDraw = "images/starfield.jpg";
    StdDraw.setScale(-R, R);
    StdDraw.picture(0, 0, imageToDraw);
    for (int i = 0; i < bodies.length; i += 1) {
      bodies[i].draw();
    }

    StdDraw.enableDoubleBuffering();

    for (double t = 0; t <= T; t += dt) {
      double[] xForces = new double[bodies.length];
      double[] yForces = new double[bodies.length];
      for (int i = 0; i < bodies.length; i += 1) {
        xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
        yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
      }
      for (int i = 0; i < bodies.length; i += 1) {
        bodies[i].update(dt, xForces[i], yForces[i]);
      }
      StdDraw.picture(0, 0, imageToDraw);
      for (int i = 0; i < bodies.length; i += 1) {
        bodies[i].draw();
      }
      StdDraw.show();
      StdDraw.pause(10);
    }
    StdOut.printf("%d\n", bodies.length);
    StdOut.printf("%.2e\n", R);
    for (int i = 0; i < bodies.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
                  }
  }
}
