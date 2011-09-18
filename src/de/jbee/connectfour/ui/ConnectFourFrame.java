package de.jbee.connectfour.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.jbee.connectfour.model.Displayable;
import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.ai.RulePlayer;

public class ConnectFourFrame
		extends JFrame {

	public static void main( String[] args ) {
		ConnectFourFrame frame = new ConnectFourFrame();

		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}

	public ConnectFourFrame() {
		final Displayable game = new Game( RulePlayer.newDefault(), RulePlayer.newDefault() );
		final BoardCanvas boardCanvas = new BoardCanvas( game );
		setTitle( "Connect 4" );
		boardCanvas.setPreferredSize( new Dimension( 700, 600 ) );
		setLayout( new BorderLayout() );
		add( boardCanvas, BorderLayout.CENTER );
		JPanel buttons = new JPanel();
		JButton turnButton = new JButton( "Turn" );
		turnButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent actionevent ) {
				game.turn();
				boardCanvas.repaint();
			}
		} );
		buttons.add( turnButton );
		add( buttons, BorderLayout.PAGE_END );
	}
}
