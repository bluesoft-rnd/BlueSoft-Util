package pl.net.bluesoft.util.lang;

import java.math.BigDecimal;

/**
 * User: POlszewski
 * Date: 2012-02-09
 * Time: 15:30
 */
public class Percents {
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static BigDecimal addPercent(BigDecimal value, BigDecimal percent) {
		return value.multiply(ONE_HUNDRED.add(percent)).divide(ONE_HUNDRED);
	}

	public static BigDecimal addPercent(BigDecimal value, double percent) {
		return addPercent(value, new BigDecimal(percent));
	}

	public static BigDecimal subtractPercent(BigDecimal value, BigDecimal percent) {
		return value.multiply(ONE_HUNDRED.subtract(percent)).divide(ONE_HUNDRED);
	}

	public static BigDecimal subtractPercent(BigDecimal value, double percent) {
		return subtractPercent(value, new BigDecimal(percent));
	}
	
	public static BigDecimal multiplyByPercent(BigDecimal value, BigDecimal percent) {
		return value.multiply(percent).divide(ONE_HUNDRED);
	}

	public static BigDecimal multiplyByPercent(BigDecimal value, double percent) {
		return multiplyByPercent(value, new BigDecimal(percent));
	}

	public static BigDecimal divideByPercent(BigDecimal value, BigDecimal percent) {
		return value.multiply(ONE_HUNDRED).divide(percent);
	}

	public static BigDecimal divideByPercent(BigDecimal value, double percent) {
		return divideByPercent(value, new BigDecimal(percent));
	}


	public static double addPercent(double value, double percent) {
		return value * (100.0 + percent)/100.0;
	}

	public static double subtractPercent(double value, double percent) {
		return value * (100.0 - percent)/100.0;
	}

	public static double multiplyByPercent(double value, double percent) {
		return value * percent / 100.0;
	}

	public static double divideByPercent(double value, double percent) {
		return value * 100.0 / percent;
	}
}
