package de.jbee.gol;

/**
 * Hier steckt die Logik. Die Entwicklung kennt den aktuellen Cluster und kann auch eine neue Zelle 
 * 
 *
 */
public interface CellTransition {

	/**
	 * Damit hier nicht die position von der zelle übergeben werden muss, sollte
	 * der cluster an die zelle nicht den ganzen cluster geben, sondern ein
	 * objekt zur ausführung, das er für jede zelle neu erzeugt. Denn dann weil
	 * dieses objekt schon, welche zelle den aufruf machen wird, da das cluster
	 * selbst diesen vorgang initiiert hat. die position steckt als zustand im
	 * erzeugten objekt und muss daher (ebenso wie die zelle selbst) nicht
	 * übergeben werden.
	 * 
	 * Die Zelle erzeugt ihrerseits lediglich die {@link EvolutionEffect}, welche
	 * aus dem innerne der Zelle über den cluster an die unbekannten
	 * nachbarzellen gelangt und diese auffordert, ihren zustand bzw. die
	 * auswirkungen auf die entwicklung anzuwenden.
	 */
	void become(Cell cell);
	
}
