import java.util.Map.Entry;

public interface Map<K, V> {
	
	// @return the number of entries in the Map
	public int size();
	
	// @return true if and only if the Map has no entries
	public boolean isEmpty();
	
	// @param k is the Key to search for
	// @return the value associated with Key k	
	public V get(K k);	
	
	// @param k is the Key for the entry to add or update
	// @param v is the Value associated with Key k
	// If the map does not contain an entry with key k, then add a new
	// entry (k,v) and return null
	// If the map contains an entry with key k, then change the value
	// associated with that key to v, and return the old value
	public V put(K k, V v);
	
	// @param k is the key for the entry to search for
	// @return the value associated with the key k, removing the entry
	// return null if no entry matches the key k
	public V remove(K k);
	
	// @return an iterable collection of all keys in the map
	public Iterable<K> keySet();
	
	// @return an iterable collection of all values in the map
	public Iterable<V> values();
	
	// @return an iterable collection of all key/value pairs (Entry objects)
	public Iterable<Entry<K,V>> entrySet();	

}