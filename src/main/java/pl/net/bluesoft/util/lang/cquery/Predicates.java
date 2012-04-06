package pl.net.bluesoft.util.lang.cquery;

import pl.net.bluesoft.util.lang.Lang;
import pl.net.bluesoft.util.lang.cquery.func.P;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.regex.Pattern;

import static pl.net.bluesoft.util.lang.cquery.CQuery.from;

/**
 * User: POlszewski
 * Date: 2011-10-20
 * Time: 09:23:04
 */
public final class Predicates {

    public static abstract class ComposedPredicate<T> implements P<T> {
        public abstract boolean invoke(T t);

        public ComposedPredicate<T> and(final P<? super T> pred) {
            return new ComposedPredicate<T>() {
                @Override
                public boolean invoke(T t) {
                    return ComposedPredicate.this.invoke(t) && pred.invoke(t);
                }
            };
        }

        public ComposedPredicate<T> or(final P<? super T> pred) {
            return new ComposedPredicate<T>() {
                @Override
                public boolean invoke(T t) {
                    return ComposedPredicate.this.invoke(t) || pred.invoke(t);
                }
            };
        }

        public ComposedPredicate<T> not() {
            return new ComposedPredicate<T>() {
                @Override
                public boolean invoke(T t) {
                    return !ComposedPredicate.this.invoke(t);
                }
            };
        }
    }

    private static abstract class ComposedPropertyPredicate<T> extends ComposedPredicate<T> {
        private final String property;
        private Method getAccessor;

        public ComposedPropertyPredicate(String property) {
            this.property = property;
        }

        public Object getValue(T t) {
            if (getAccessor == null) {
                getAccessor = extractAccessor(t, property);
            }
            try {
                return getAccessor.invoke(t);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static Method extractAccessor(Object x, String property) {
            String Property = Character.toUpperCase(property.charAt(0)) + property.substring(1);
            try {
                return x.getClass().getMethod("get" + Property);
            }
            catch (Exception e) {
                try {
                    return x.getClass().getMethod("is" + Property);
                }
                catch (Exception e1) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static <T> ComposedPredicate<T> not(final P<T> pred) {
        return new ComposedPredicate<T>() {
            @Override
            public boolean invoke(T t) {
                return !pred.invoke(t);
            }
        };
    }

    // property comparisons
    
    public static <T, V> ComposedPredicate<T> eq(String property, final V value) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return Lang.equals(getValue(t), value);
            }
        };
    }

    public static <T, V> ComposedPredicate<T> ne(String property, final V value) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return !Lang.equals(getValue(t), value);
            }
        };
    }

    public static <T, C> ComposedPredicate<T> lt(final String property, final Comparable<C> value) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return ((Comparable)getValue(t)).compareTo(value) < 0;
            }
        };
    }

    public static <T, C> ComposedPredicate<T> le(final String property, final Comparable<C> value) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return ((Comparable)getValue(t)).compareTo(value) <= 0;
            }
        };
    }

    public static <T, C> ComposedPredicate<T> gt(final String property, final Comparable<C> value) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return ((Comparable)getValue(t)).compareTo(value) > 0;
            }
        };
    }

    public static <T, C> ComposedPredicate<T> ge(final String property, final Comparable<C> value) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return ((Comparable)getValue(t)).compareTo(value) >= 0;
            }
        };
    }

    public static <T, C> ComposedPredicate<T> between(final String property, final Comparable<C> fromValue, final Comparable<C> toValue) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                Comparable value = (Comparable)getValue(t);
                return value != null && value.compareTo(fromValue) >= 0 && value.compareTo(toValue) <= 0;
            }
        };
    }

    // property others

    public static <T> ComposedPredicate<T> isNull(String property) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return getValue(t) == null;
            }
        };
    }

    public static <T> ComposedPredicate<T> isNotNull(String property) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return getValue(t) != null;
            }
        };
    }

    public static <T> ComposedPredicate<T> isTrue(final String property) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return Boolean.TRUE.equals(getValue(t));
            }
        };
    }

    public static <T> ComposedPredicate<T> isFalse(final String property) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return Boolean.FALSE.equals(getValue(t));
            }
        };
    }

    public static <T> ComposedPredicate<T> in(String property, final Collection<T> collection) {
        return new ComposedPropertyPredicate<T>(property) {
            @Override
            public boolean invoke(T t) {
                return collection.contains(t);
            }
        };
    }

    public static <T> ComposedPredicate<T> in(String property, final T... array) {
        return new ComposedPropertyPredicate<T>(property) {
            Collection collection = from(array);
            @Override
            public boolean invoke(T t) {
                return collection.contains(t);
            }
        };
    }

    public static <T> ComposedPredicate<T> matches(final String property, final String regex) {
        return new ComposedPropertyPredicate<T>(property) {
            private final Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean invoke(T t) {
                String s = (String)getValue(t);
                return s != null && pattern.matcher(s).matches();
            }
        };
    }



    // value comparisons

    public static <T> P<T> eq(final T value) {
        return new P<T>() {
            @Override
            public boolean invoke(T t) {
                return Lang.equals(t, value);
            }
        };
    }

    public static <T, V> P<T> ne(final T value) {
        return new P<T>() {
            @Override
            public boolean invoke(T t) {
                return !Lang.equals(t, value);
            }
        };
    }

    public static <T extends Comparable<T>> ComposedPredicate<T> lt(final T value) {
        return new ComposedPredicate<T>() {
            @Override
            public boolean invoke(T t) {
                return t.compareTo(value) < 0;
            }
        };
    }

    public static <T extends Comparable<T>> ComposedPredicate<T> le(final T value) {
        return new ComposedPredicate<T>() {
            @Override
            public boolean invoke(T t) {
                return t.compareTo(value) <= 0;
            }
        };
    }

    public static <T extends Comparable<T>> ComposedPredicate<T> gt(final T value) {
        return new ComposedPredicate<T>() {
            @Override
            public boolean invoke(T t) {
                return t.compareTo(value) > 0;
            }
        };
    }

    public static <T extends Comparable<T>> ComposedPredicate<T> ge(final T value) {
        return new ComposedPredicate<T>() {
            @Override
            public boolean invoke(T t) {
                return t.compareTo(value) >= 0;
            }
        };
    }

    public static <T extends Comparable<T>> ComposedPredicate<T> between(final T fromValue, final T toValue) {
        return new ComposedPredicate<T>() {
            @Override
            public boolean invoke(T t) {
                Comparable value = (Comparable)t;
                return value != null && value.compareTo(fromValue) >= 0 && value.compareTo(toValue) <= 0;
            }
        };
    }

    // others

    public static P<Object> isNull() {
        return new P<Object>() {
            @Override
            public boolean invoke(Object t) {
                return t == null;
            }
        };
    }

    public static P<Object> isNotNull() {
        return new P<Object>() {
            @Override
            public boolean invoke(Object t) {
                return t != null;
            }
        };
    }

    public static P<Boolean> isTrue() {
        return new P<Boolean>() {
            @Override
            public boolean invoke(Boolean t) {
                return Boolean.TRUE.equals(t);
            }
        };
    }

    public static P<Boolean> isFalse() {
        return new P<Boolean>() {
            @Override
            public boolean invoke(Boolean t) {
                return Boolean.FALSE.equals(t);
            }
        };
    }


    public static <T> P<T> in(final Collection<T> collection) {
        return new P<T>() {
            @Override
            public boolean invoke(T t) {
                return collection.contains(t);
            }
        };
    }

    public static <T> P<T> in(final T... array) {
        return new P<T>() {
            Collection collection = from(array);
            @Override
            public boolean invoke(T t) {
                return collection.contains(t);
            }
        };
    }

	public static P<String> matches(final String regex) {
		return new P<String>() {
			@Override
			public boolean invoke(String t) {
				return t != null && t.matches(regex);
			}
		};
	}
}
