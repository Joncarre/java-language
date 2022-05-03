package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import main.java.es.ucm.fdi.tp.base.Utils;
import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.extra.jcolor.ColorChooser;

public class PlayersInfoViewerComp<S extends GameState<S, A>, A extends GameAction<S, A>> extends PlayersInfoViewer<S, A> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	class PlayerInfoTableModel extends DefaultTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -252200516291838092L;

		private String[] colNames;
		
		/**
		 * Constructora
		 */
		PlayerInfoTableModel() {
			this.colNames = new String[] { "#Player", "Color" };
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public int getRowCount() {
			return numOfPlayers;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				return "" + rowIndex;
			else
				return "";
		}
		
		public void refresh() {
			fireTableStructureChanged();
		}
	};
	
	private Map<Integer, Color> playerColors;
	private MatteBorder playerInfoTableBorder = new MatteBorder(0, 0, 0, 0, Color.black);
	private PlayerInfoTableModel playerInfoTable;
	Iterator<Color> colorsIter;
	private ColorChooser c;
	private int numOfPlayers;
	
	/**
	 * Constructora
	 */
	public PlayersInfoViewerComp() {
		this.colorsIter = Utils.colorsGenerator();
		this.playerColors = new HashMap<>();
		this.observers = new ArrayList<>();
		initGUI();
	}
	
	/**
	 * Inicializa lo necesario para el 'PlayersInfoViewer'
	 */
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Player Information"));

		playerInfoTable = new PlayerInfoTableModel();
		final JTable table = new JTable(playerInfoTable) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				((JComponent) comp).setBorder(playerInfoTableBorder);
				if (col == 1)
					comp.setBackground(playerColors.get(row));
				else
					comp.setBackground(Color.WHITE);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};

		table.setToolTipText("Click on a row to change the color of a player");
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					changePlayerColor(row);
				}
			}
		});

		JScrollPane sp = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		this.setPreferredSize(new Dimension(50, 120));
		this.add(sp);

		c = new ColorChooser(new JFrame(), "Select Piece Color", playerColors.get(0));
	}
	
	/* ----------------------------------- Métodos de las clases abstractas PlayersInfoViewer y GUIView ----------------------------------- */
	
	/**
	 * Cambia el color del jugador
	 * @param playerId
	 */
	protected void changePlayerColor(int playerId) {
		c.setSelectedColorDialog( playerColors.get(playerId) );
		c.openDialog();
		if (c.getColor() != null) {
			playerColors.put(playerId, c.getColor());
			notifyObservers(playerId,c.getColor());
		}
	}
	
	/**
	 * Asigna el color al jugador p-ésimo
	 * @param p
	 * @return
	 */
	private Color assignColor(int p) {
		Color c = playerColors.get(p);
		if (c == null){
			c = colorsIter.next();
			playerColors.put(p, c);
			playerInfoTable.refresh();
			notifyObservers(p,c);
		}
		return c;
	}
	
	/**
	 * Asigna el color a todos los jugadores
	 */
	private void assignColors() {
		for (int i = 0; i < numOfPlayers; i++) {
			assignColor(i);
		}
	}
	
	@Override
	public void setNumberOfPlayer(int n) {
		numOfPlayers = n;
		assignColors();	
	}

	@Override
	public Color getPlayerColor(int p) {
		Color c = playerColors.get(p);
		if (c == null) {
			c = assignColor(p);
		}
		return c;
	}

	@Override
	public void enable() {
		// No hace nada. //
	}

	@Override
	public void disable() {
		// No hace nada. //
	}
	
	@Override
	public void update(S state) {
		// No hace nada. //
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> messageInfoViewer) {
		// No hace nada. //
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		// No hace nada. //
	}

	@Override
	public void colorChanged(int p, Color color) {
		// No hace nada. //
	}
}
