import static java.lang.Math.abs;

public class Rational {
    private int numerator;
    private int denominator;

    public Rational() {
        numerator = 0;
        denominator = 1;
    }

    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("division by zero !");
        }

        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
        reduce();
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
        reduce();
    }

    @Override
    public String toString() {
        return String.join("/", String.valueOf(numerator), String.valueOf(denominator));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Rational rational = (Rational)obj;
        return (this.numerator == rational.numerator && this.denominator == rational.denominator);
    }

    public boolean less(Rational rational) {
        if (this.denominator == rational.getDenominator()) {
            return this.numerator < rational.getNumerator();
        } else if (this.numerator == rational.getNumerator()) {
            return this.denominator > rational.getDenominator();
        } else {
            return this.numerator * rational.getDenominator() < getNumerator() * this.denominator;
        }
    }

    public boolean lessOrEqual(Rational rational) {
        return this.equals(rational) || this.less(rational);
    }

    public Rational plus(Rational rational) {
        return new Rational(
                this.getNumerator() * rational.getDenominator()
                        + rational.getNumerator() * this.getDenominator(),
                this.getDenominator() * rational.getDenominator()
        );
    }

    public Rational multiply(Rational rational) {
        return new Rational(
                this.getNumerator() * rational.getNumerator(),
                this.getDenominator() * rational.getDenominator()
        );
    }

    public Rational minus(Rational rational) {
        return this.plus( rational.multiply( new Rational(-1, 1) ) );
    }

    public Rational divide(Rational rational) {
        if (rational.getNumerator() == 0) {
            throw new ArithmeticException("division by zero !");
        }
        return this.multiply( new Rational(rational.getDenominator(), rational.getNumerator()) );
    }

    private void reduce() {
        int gcd = getGCD();
        numerator /= gcd;
        denominator /= gcd;

        if (numerator == 0) {
            denominator = 1;
        }

        simplifyMinuses();
    }

    private int getGCD() {
        int n = abs(numerator);
        int d = abs(denominator);

        while (n > 0 && d > 0) {
            if (n > d) {
                n %= d;
            } else {
                d %= n;
            }
        }

        return n + d;
    }

    private void simplifyMinuses() {
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
    }
}
