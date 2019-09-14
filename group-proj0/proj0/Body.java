public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private final static double G = 6.67 * 1e-11;

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;

	}

	public double calcDistance(Body a) {
		double x_pos = a.xxPos - xxPos;
		double y_pos = a.yyPos - yyPos;
		return Math.sqrt(x_pos * x_pos + y_pos * y_pos);
	}

	public double calcForceExertedBy(Body a) {
		return G * mass * a.mass / Math.pow(this.calcDistance(a), 2);
	}

	public double calcForceExertedByX(Body a) {
		double x_pos = a.xxPos - xxPos;
		return this.calcForceExertedBy(a) * x_pos / this.calcDistance(a);
	}

	public double calcForceExertedByY(Body a) {
		double y_pos = a.yyPos - yyPos;
		return this.calcForceExertedBy(a) * y_pos / this.calcDistance(a);
	}

	public double calcNetForceExertedByX(Body[] bodys) {
		double sum = 0;
		for (int i = 0; i < bodys.length; i += 1) {
			if (this.equals(bodys[i])) {
			 continue;
			}
			sum += this.calcForceExertedByX(bodys[i]);
		}
		return sum;
	}


	public double calcNetForceExertedByY(Body[] bodys) {
		double sum = 0;
		for (int i = 0; i < bodys.length; i += 1) {
			if (this.equals(bodys[i])) {
			 continue;
			}
			sum += this.calcForceExertedByY(bodys[i]);
		}
		return sum;
	}

	public void update(double dt, double fX, double fY) {
		double a_netx = fX / mass;
		double a_nety = fY / mass;
		xxVel = xxVel + dt * a_netx;
		yyVel = yyVel + dt * a_nety;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}

}
