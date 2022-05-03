package main.java.es.ucm.fdi.tp.view;

public interface SmartMoveObserver {
	/** Nos dice cuándo comenzó a buscar un movimiento */
	public void onStart();
	
	/** Nos dice cuándo terminó y algunas estadísticas. A saber:
	 * 
	 * @success: si la búsqueda de la acción retornó null, entonces vale 'false', si no, 'true'.
	 * @time: cuánto tiempo nos llevó el buscar una acción.
	 * @nodes: lo que smartPlayer.getEvaluationCount() retornó.
	 * @value: lo que smartPlayer.getValue() retornó.
	 */
	public void onEnd(boolean success, long time, int nodes, double value);
}
