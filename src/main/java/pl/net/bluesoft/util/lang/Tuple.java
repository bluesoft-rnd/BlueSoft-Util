package pl.net.bluesoft.util.lang;

import java.util.Arrays;

/**
 * User: POlszewski
 * Date: 2012-02-04
 * Time: 09:30
 */
/* Better alternative for multipart string keys */
public class Tuple implements Comparable<Tuple> {
	private final Object[] parts;
	
	public Tuple(Object... parts) {
		this.parts = parts;
	}

	public Object[] getParts() {
		return parts;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tuple) {
			return Arrays.equals(parts, ((Tuple) obj).parts);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(parts);
	}

	@Override
	public int compareTo(Tuple id) {
		return Lang.compare(parts, id.parts);
	}

	@Override
	public String toString() {
		return Arrays.toString(parts);
	}
}
