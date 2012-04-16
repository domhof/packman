package at.ac.tuwien.foop.network;

/**
 * Exposes controlled way of disposing FileHandles etc.
 * Closable would be another name.
 * After short research i found nothing appropriate in java base libs?
 *
 */
public interface Disposable {
	/**
	 * Savely closes all resources of this object. 
	 */
	public void dispose();
}
