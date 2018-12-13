package diamondcore.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ExtArrayList<E> extends ArrayList<E> {
	
	public ExtArrayList(E element) {
		super(Collections.singletonList(element));
	}
	
	public ExtArrayList<E> factoryAddAll(Collection<? extends E> collection) {
		super.addAll(collection);
		return this;
	}
}
