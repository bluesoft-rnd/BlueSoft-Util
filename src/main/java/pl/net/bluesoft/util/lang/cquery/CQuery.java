package pl.net.bluesoft.util.lang.cquery;

import pl.net.bluesoft.util.lang.cquery.func.P;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
/**
 * Collection wrapper that mimics .NET LINQ functionality
 */
public final class CQuery {
	public static <T> CQueryCollection<T> from(final Set<T> collection) {
		return collection == null ? CQuery.<T>empty() : new CollectionWrapper<T>(collection) {
			@Override
			public CQueryCollection<T> distinct() {
				return this;
			}
		};
	}
	
	public static <T> CQueryCollection<T> from(final Collection<T> collection) {
		return collection == null ? CQuery.<T>empty() : new CollectionWrapper<T>(collection);
	}
	
	public static <T> CQueryCollection<T> from(final Iterable<T> iterable) {
		return iterable == null ? CQuery.<T>empty() : new CQueryCollection<T>() {
			@Override
			public Iterator<T> iterator() {
				return iterable.iterator();
			}
		};
	}
	
	public static <T> CQueryCollection<T> from(final T...array) {
		return array == null ? CQuery.<T>empty() : new ArrayWrapper<T>(array);
	}
	
	public static CQueryCollection<Boolean> from(final boolean...array) {
		return array == null ? CQuery.<Boolean>empty() : new BooleanArrayWrapper(array);
	}
	
	public static CQueryCollection<Integer> from(final int...array) {
		return array == null ? CQuery.<Integer>empty() : new IntArrayWrapper(array);
	}	
	
	public static <T> CQueryCollection<T> repeat(final T value, final int n) {
		return new RepeatWrapper<T>(n, value);
	}
	
	public static CQueryCollection<Integer> range(final int start, final int end) {
		return range(start, end, 1);
	}
	
	public static CQueryCollection<Integer> range(final int start, final int end, final int step) {
		return new RangeWrapper(start, end, step);
	}
	
	@SuppressWarnings("rawtypes")
	private static CQueryCollection empty = new CQueryCollection() {
		Iterator iterator = new ReadOnlyIterator() {
			@Override
			public boolean hasNext() {				
				return false;
			}

			@Override
			public Object next() {
				throw new IllegalStateException();
			}
		};
		
		@Override
		public Iterator iterator() {
			return iterator;
		}
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public boolean any() {
			return false;
		}
		
		@Override
		public int count() {
			return 0;
		}
		
		@Override
		public int count(P pred) {
			return 0;
		}
		
		@Override
		public int size() {
			return 0;
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <T> CQueryCollection<T> empty(Class<T> clazz) {
		return empty;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> CQueryCollection<T> empty() {
		return empty;
	}

	private static class CollectionWrapper<T> extends CQueryCollection<T> {
		private final Collection<T> collection;

		public CollectionWrapper(Collection<T> collection) {
			this.collection = collection;
		}

		@Override
		public Iterator<T> iterator() {
			return collection.iterator();
		}

		@Override
		public boolean any() {
			return collection.isEmpty();
		}

		@Override
		public boolean isEmpty() {
			return collection.isEmpty();
		}

		@Override
		public int count() {
			return collection.size();
		}

		@Override
		public int size() {
			return collection.size();
		}

		@Override
		public boolean add(T t) {
			return collection.add(t);
		}

		@Override
		public boolean remove(Object o) {
			return collection.remove(o);
		}

		@Override
		public boolean addAll(Collection<? extends T> c) {
			return collection.addAll(c);
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return collection.removeAll(c);
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return collection.retainAll(c);
		}

		@Override
		public void clear() {
			collection.clear();
		}
	}

	private static class ArrayWrapper<T> extends CQueryCollection<T> {
		private final T[] array;

		public ArrayWrapper(T... array) {
			this.array = array;
		}

		@Override
		public Iterator<T> iterator() {
			return new ReadOnlyIterator<T>() {
				private int pos = 0;

				@Override
				public boolean hasNext() {
					return pos < array.length;
				}

				@Override
				public T next() {
					return array[pos++];
				}
			};
		}

		@Override
		public boolean isEmpty() {
			return array.length == 0;
		}

		@Override
		public boolean any() {
			return array.length > 0;
		}

		@Override
		public int count() {
			return array.length;
		}

		@Override
		public int size() {
			return array.length;
		}

		@Override
		public T elementAt(int index) {
			return array[index];
		}

		@Override
		public T elementAtOrDefault(int index, T default_) {
			return index < array.length ? array[index] : default_;
		}
	}

	private static class BooleanArrayWrapper extends CQueryCollection<Boolean> {
		private final boolean[] array;

		public BooleanArrayWrapper(boolean... array) {
			this.array = array;
		}

		@Override
		public Iterator<Boolean> iterator() {
			return new ReadOnlyIterator<Boolean>() {
				private int pos = 0;

				@Override
				public boolean hasNext() {
					return pos < array.length;
				}

				@Override
				public Boolean next() {
					return array[pos++];
				}
			};
		}

		@Override
		public boolean isEmpty() {
			return array.length == 0;
		}

		@Override
		public boolean any() {
			return array.length > 0;
		}

		@Override
		public int count() {
			return array.length;
		}

		@Override
		public int size() {
			return array.length;
		}

		@Override
		public Boolean elementAt(int index) {
			return array[index];
		}

		@Override
		public Boolean elementAtOrDefault(int index, Boolean default_) {
			return index < array.length ? array[index] : default_;
		}
	}

	private static class IntArrayWrapper extends CQueryCollection<Integer> {
		private final int[] array;

		public IntArrayWrapper(int... array) {
			this.array = array;
		}

		@Override
		public Iterator<Integer> iterator() {
			return new ReadOnlyIterator<Integer>() {
				private int pos = 0;

				@Override
				public boolean hasNext() {
					return pos < array.length;
				}

				@Override
				public Integer next() {
					return array[pos++];
				}
			};
		}

		@Override
		public boolean isEmpty() {
			return array.length == 0;
		}

		@Override
		public boolean any() {
			return array.length > 0;
		}

		@Override
		public int count() {
			return array.length;
		}

		@Override
		public int size() {
			return array.length;
		}

		@Override
		public Integer elementAt(int index) {
			return array[index];
		}

		@Override
		public Integer elementAtOrDefault(int index, Integer default_) {
			return index < array.length ? array[index] : default_;
		}
	}

	private static class RepeatWrapper<T> extends CQueryCollection<T> {
		private final int n;
		private final T value;

		public RepeatWrapper(int n, T value) {
			this.n = n;
			this.value = value;
		}

		@Override
		public Iterator<T> iterator() {
			return new ReadOnlyIterator<T>() {
				int pos = 0;
				@Override
				public boolean hasNext() {
					return pos < n;
				}

				@Override
				public T next() {
					if (pos < n) {
						++pos;
						return value;
					}
					throw new IllegalStateException();
				}
			};
		}
	}

	private static class RangeWrapper extends CQueryCollection<Integer> {
		private final int start;
		private final int end;
		private final int step;

		public RangeWrapper(int start, int end, int step) {
			this.start = start;
			this.end = end;
			this.step = step;
		}

		@Override
		public Iterator<Integer> iterator() {
			return new ReadOnlyIterator<Integer>() {
				int pos = start;
				@Override
				public boolean hasNext() {
					return pos <= end;
				}

				@Override
				public Integer next() {
					if (pos <= end) {
						int result = pos;
						pos += step;
						return result;
					}
					throw new IllegalStateException();
				}
			};
		}
	}
}
