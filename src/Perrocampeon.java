import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.postgresql.util.PSQLException;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Perrocampeon extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modTable;
	
	private JTextField textFieldAKC;
	private JTextField textFieldNombre;
	private ButtonGroup grupo;
	private JCheckBox chckbxMacho;
	private JCheckBox chckbxHembra;
	private JComboBox<String> comboBoxTitulos;
	private JComboBox<String> comboBoxUpdate;
	private JFormattedTextField formattedTextFieldFechaN;
	private JFormattedTextField formattedTextFieldPuntosA;
	private JFormattedTextField formattedTextFieldIDLugar;
	private JFormattedTextField formattedTextFieldIDVR;

	public Perrocampeon() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Perrocampeon.class.getResource("/addons/icon.png")));
		setResizable(false);
		setTitle("Westminster Kennel Club - Table de Perros Campeones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1054, 558);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 160, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDisconnect = new JButton("Desconectarse");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inicio.desconectar();
				System.exit(0);
			}
		});
		btnDisconnect.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDisconnect.setBounds(10, 11, 164, 47);
		contentPane.add(btnDisconnect);
		
		JButton btnSelect = new JButton("Consultar");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vaciarTabla();
				llenarTabla();
			}
		});
		btnSelect.setFont(new Font("Arial", Font.PLAIN, 16));
		btnSelect.setBounds(10, 69, 106, 47);
		contentPane.add(btnSelect);
		
		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnInsert.setFont(new Font("Arial", Font.PLAIN, 16));
		btnInsert.setBounds(10, 127, 106, 47);
		contentPane.add(btnInsert);
		
		JButton btnUpdate = new JButton("Actualizar");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnUpdate.setFont(new Font("Arial", Font.PLAIN, 16));
		btnUpdate.setBounds(10, 185, 106, 47);
		contentPane.add(btnUpdate);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drop();
			}
		});
		btnEliminar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnEliminar.setBounds(10, 243, 106, 47);
		contentPane.add(btnEliminar);
		
		JLabel lblLogo2 = new JLabel("");
		lblLogo2.setIcon(new ImageIcon(Perrocampeon.class.getResource("/addons/logo2.png")));
		lblLogo2.setBounds(5, 312, 181, 103);
		contentPane.add(lblLogo2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(204, 26, 798, 402);
		contentPane.add(scrollPane);
		
		modTable = new DefaultTableModel();
		
		table = new JTable();
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		setModelo();
		llenarTabla();
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(61);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(96);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(35);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(67);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(106);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(56);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(98);
		scrollPane.setViewportView(table);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSalir.setBounds(963, 487, 65, 21);
		contentPane.add(btnSalir);
		
		textFieldAKC = new JTextField();
		textFieldAKC.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAKC.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldAKC.setBounds(10, 446, 106, 20);
		contentPane.add(textFieldAKC);
		textFieldAKC.setColumns(10);
		
		JLabel lblAKC = new JLabel("AKC");
		lblAKC.setHorizontalAlignment(SwingConstants.CENTER);
		lblAKC.setFont(new Font("Arial", Font.BOLD, 12));
		lblAKC.setBounds(45, 473, 46, 14);
		contentPane.add(lblAKC);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldNombre.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(126, 446, 114, 20);
		contentPane.add(textFieldNombre);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
		lblNombre.setBounds(167, 473, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSexo.setFont(new Font("Arial", Font.BOLD, 12));
		lblSexo.setBounds(250, 460, 46, 14);
		contentPane.add(lblSexo);
		
		chckbxMacho = new JCheckBox("M");
		chckbxMacho.setSelected(true);
		chckbxMacho.setFont(new Font("Arial", Font.PLAIN, 12));
		chckbxMacho.setBounds(294, 446, 97, 23);
		contentPane.add(chckbxMacho);
		
		chckbxHembra = new JCheckBox("H");
		chckbxHembra.setFont(new Font("Arial", Font.PLAIN, 12));
		chckbxHembra.setBounds(294, 471, 97, 23);
		contentPane.add(chckbxHembra);
		
		grupo = new ButtonGroup();
		grupo.add(chckbxHembra);
		grupo.add(chckbxMacho);
		
		JLabel lblNombre_1 = new JLabel("Fecha de Nacimiento");
		lblNombre_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNombre_1.setBounds(397, 475, 126, 14);
		contentPane.add(lblNombre_1);
		
		try {
			MaskFormatter mascaraFecha = new MaskFormatter("####/##/##");
			mascaraFecha.setPlaceholderCharacter(' ');
			formattedTextFieldFechaN = new JFormattedTextField(mascaraFecha);
			formattedTextFieldFechaN.setHorizontalAlignment(SwingConstants.CENTER);
			formattedTextFieldFechaN.setFont(new Font("Arial", Font.PLAIN, 12));
			formattedTextFieldFechaN.setBounds(414, 446, 73, 20);
			contentPane.add(formattedTextFieldFechaN);
			
			MaskFormatter mascaraPuntosA = new MaskFormatter("######");
			mascaraPuntosA.setPlaceholderCharacter('0');
			formattedTextFieldPuntosA = new JFormattedTextField(mascaraPuntosA);
			formattedTextFieldPuntosA.setHorizontalAlignment(SwingConstants.RIGHT);
			formattedTextFieldPuntosA.setFont(new Font("Arial", Font.PLAIN, 12));
			formattedTextFieldPuntosA.setBounds(641, 446, 73, 20);
			contentPane.add(formattedTextFieldPuntosA);
			
			MaskFormatter mascaraID = new MaskFormatter("##########");
			mascaraID.setPlaceholderCharacter('0');

			formattedTextFieldIDLugar = new JFormattedTextField(mascaraID);
			formattedTextFieldIDLugar.setHorizontalAlignment(SwingConstants.RIGHT);
			formattedTextFieldIDLugar.setFont(new Font("Arial", Font.PLAIN, 12));
			formattedTextFieldIDLugar.setBounds(747, 446, 81, 20);
			contentPane.add(formattedTextFieldIDLugar);
			
			formattedTextFieldIDVR = new JFormattedTextField(mascaraID);
			formattedTextFieldIDVR.setHorizontalAlignment(SwingConstants.RIGHT);
			formattedTextFieldIDVR.setFont(new Font("Arial", Font.PLAIN, 12));
			formattedTextFieldIDVR.setBounds(852, 446, 81, 20);
			contentPane.add(formattedTextFieldIDVR);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
		lblTitulo.setBounds(545, 473, 46, 14);
		contentPane.add(lblTitulo);
		
		comboBoxTitulos = new JComboBox<String>();
		comboBoxTitulos.setModel(new DefaultComboBoxModel<String>(new String[] {"CH", "GCH", "GCHS", "GCHG", "GCHP"}));
		comboBoxTitulos.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxTitulos.setMaximumRowCount(5);
		comboBoxTitulos.setBounds(523, 445, 90, 22);
		contentPane.add(comboBoxTitulos);
		
		JLabel lblPuntosAcumulados = new JLabel("Puntos acumulados");
		lblPuntosAcumulados.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuntosAcumulados.setFont(new Font("Arial", Font.BOLD, 12));
		lblPuntosAcumulados.setBounds(623, 473, 114, 14);
		contentPane.add(lblPuntosAcumulados);
		
		JLabel lblIdLugar = new JLabel("ID Lugar");
		lblIdLugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdLugar.setFont(new Font("Arial", Font.BOLD, 12));
		lblIdLugar.setBounds(733, 473, 114, 14);
		contentPane.add(lblIdLugar);
		
		JLabel lblIdVariedadraza = new JLabel("ID Variedad/Raza");
		lblIdVariedadraza.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdVariedadraza.setFont(new Font("Arial", Font.BOLD, 12));
		lblIdVariedadraza.setBounds(838, 473, 114, 14);
		contentPane.add(lblIdVariedadraza);
		
		comboBoxUpdate = new JComboBox<String>();
		comboBoxUpdate.setFont(new Font("Arial", Font.PLAIN, 10));
		comboBoxUpdate.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "Sexo", "Fecha de Na.", "Puntos Acu.", "ID Lugar", "ID Va./Ra."}));
		comboBoxUpdate.setToolTipText("Seleccione la columna a la cual desea realizar un UPDATE");
		comboBoxUpdate.setBounds(120, 199, 80, 22);
		contentPane.add(comboBoxUpdate);
		
	}
	private void setModelo() {
		String[] cabecera = {"AKC", "Nombre", "Sexo", "Fecha N.", "Titulo", "Puntos acumulados", "ID Lugar", "ID Variedad/Raza"};
		modTable.setColumnIdentifiers(cabecera);
		table.setModel(modTable);

	}
	private void llenarTabla(){
		Statement stmt = null;
		try {
			stmt = Inicio.con.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM PERRO_CAMPEON;" );
			Object[] datos = new Object[modTable.getColumnCount()];
			while ( rs.next() ) {
	            String akc = rs.getString("akc");
	            String nombre = rs.getString("nombre");
	            String sexo  = rs.getString("sexo");
	            String fechaN = String.valueOf(rs.getDate("fecha_de_nacimiento"));
	            String titulo = rs.getString("titulo");
	            String puntosAcumulados = String.valueOf(rs.getInt("puntos_acumulados")) ;
	            String idLugar = String.valueOf(rs.getInt("id_lugar"));
	            String idVariedad = String.valueOf(rs.getInt("id_variedad_raza"));
	            datos[0] = akc;
	            datos[1] = nombre;
	            datos[2] = sexo;
	            datos[3] = fechaN;
	            datos[4] = titulo;
	            datos[5] = puntosAcumulados;
	            datos[6] = idLugar;
	            datos[7] = idVariedad;
	            modTable.addRow(datos);
			}
			table.setModel(modTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void vaciarTabla() {
		modTable.setRowCount(0);
		table.setModel(modTable);
	}
	private void insert() {
		 Statement stmt = null;
		 try {
			if ( 	!(textFieldAKC.getText().equals("")) && 
					!(textFieldNombre.getText().equals("")) && 
					!(formattedTextFieldFechaN.getText().equals("    /  /  ")) && 
					!(formattedTextFieldPuntosA.getText().equals("")) && 
					!(formattedTextFieldIDLugar.getText().equals("")) && 
					!(formattedTextFieldIDVR.getText().equals(""))) {
				
				if (	(comboBoxTitulos.getSelectedItem().toString().equals("CH") && Integer.parseInt(formattedTextFieldPuntosA.getText()) > 14) ||
						(comboBoxTitulos.getSelectedItem().toString().equals("GCH") && Integer.parseInt(formattedTextFieldPuntosA.getText()) > 25) ||
						(comboBoxTitulos.getSelectedItem().toString().equals("GCHS") && Integer.parseInt(formattedTextFieldPuntosA.getText()) > 200) ||
						(comboBoxTitulos.getSelectedItem().toString().equals("GCHG") && Integer.parseInt(formattedTextFieldPuntosA.getText()) > 400) ||
						(comboBoxTitulos.getSelectedItem().toString().equals("GCHP") && Integer.parseInt(formattedTextFieldPuntosA.getText()) > 800)) {
					String sql = "INSERT INTO public.perro_campeon(\r\n"
							+ "	akc, nombre, sexo, fecha_de_nacimiento, titulo, puntos_acumulados, id_lugar, id_variedad_raza)\r\n"
							+ "	VALUES ('" + textFieldAKC.getText() + "', '" + textFieldNombre.getText() + "', '" + ( (chckbxHembra.isSelected()) ? chckbxHembra.getText(): chckbxMacho.getText() ) + "', '" + formattedTextFieldFechaN.getText() + "', '" + comboBoxTitulos.getSelectedItem().toString() + "', " + formattedTextFieldPuntosA.getText() + ", " + formattedTextFieldIDLugar.getText() + ", " + formattedTextFieldIDVR.getText() + ");";
					int input = JOptionPane.showConfirmDialog(null, "¿Quiere introducir esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
					if (input == 0) {
						try {
							stmt = Inicio.con.createStatement();
							stmt.executeUpdate(sql);
							Inicio.con.commit();
							JOptionPane.showMessageDialog(null, "Los datos fueron insertados con exito...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
						}catch (PSQLException e) {
							JOptionPane.showMessageDialog(null, "El registro que intenta insertar \n ya esta registrado", "Aviso", JOptionPane.WARNING_MESSAGE);
							try {
								Inicio.con = Conexion.crearConexion();
							} catch (ClassNotFoundException e1) {
								e1.printStackTrace();
							}
						}			
					}
				}else {
					JOptionPane.showMessageDialog(null, "Los puntos no concuerdan con \n el titulo seleccionado", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los compos \n para realizar una insercion", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void update() {
		Statement stmt = null;
		try {
			if (comboBoxUpdate.getSelectedItem().toString().equals("Nombre")) {
				String sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET nombre='" + textFieldNombre.getText() + "'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				int input = JOptionPane.showConfirmDialog(null, "¿Quiere actualizar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						stmt = Inicio.con.createStatement();
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta actualizar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}else if (comboBoxUpdate.getSelectedItem().toString().equals("Sexo")) {
				String sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET sexo='" + ( (chckbxHembra.isSelected()) ? chckbxHembra.getText(): chckbxMacho.getText() ) + "'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				int input = JOptionPane.showConfirmDialog(null, "¿Quiere actualizar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						stmt = Inicio.con.createStatement();
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta actualizar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}else if (comboBoxUpdate.getSelectedItem().toString().equals("Fecha de Na.")) {
				String sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET fecha_de_nacimiento='" + formattedTextFieldFechaN.getText() + "'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				int input = JOptionPane.showConfirmDialog(null, "¿Quiere actualizar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						stmt = Inicio.con.createStatement();
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta actualizar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}else if (comboBoxUpdate.getSelectedItem().toString().equals("Puntos Acu.")) {
				stmt = Inicio.con.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT puntos_acumulados\r\n"
												+ "	FROM public.perro_campeon\r\n"
												+ "	WHERE akc = '" + textFieldAKC.getText() + "';" );	
				String sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET puntos_acumulados=" + formattedTextFieldPuntosA.getText() + "\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				int input = JOptionPane.showConfirmDialog(null, "¿Quiere actualizar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						updateTitulo();
						stmt = Inicio.con.createStatement();
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta actualizar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}else if (comboBoxUpdate.getSelectedItem().toString().equals("ID Lugar")) {
				String sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET puntos_acumulados=" +  formattedTextFieldIDLugar.getText() + "\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				int input = JOptionPane.showConfirmDialog(null, "¿Quiere actualizar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						stmt = Inicio.con.createStatement();
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta actualizar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}else if (comboBoxUpdate.getSelectedItem().toString().equals("ID Va./Ra.")) {
				String sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET puntos_acumulados=" + formattedTextFieldIDVR.getText() + "\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				int input = JOptionPane.showConfirmDialog(null, "¿Quiere actualizar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						stmt = Inicio.con.createStatement();
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta actualizar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private void drop() {
		 Statement stmt = null;
		 try {
			 int input = JOptionPane.showConfirmDialog(null, "¿Quiere eliminar esta informacion en la tabla ?", "Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION);
				if(input == 0) {
					try {
						stmt = Inicio.con.createStatement();
						String sql = "DELETE from perro_campeon where akc = '"+ textFieldAKC.getText() +"';";
						stmt.executeUpdate(sql);
						Inicio.con.commit();
					}catch(PSQLException e){
						JOptionPane.showMessageDialog(null, "El registro que intenta eliminar \n no existe", "Aviso", JOptionPane.WARNING_MESSAGE);
						try {
							Inicio.con = Conexion.crearConexion();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	}
	private void updateTitulo() {
		Statement stmt = null;
		String sql;
		int puntosActu = Integer.parseInt(formattedTextFieldPuntosA.getText());
		try {
			if ((puntosActu > 14) && (puntosActu < 25)) {
				sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET titulo='CH'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				stmt = Inicio.con.createStatement();
				stmt.executeUpdate(sql);
				Inicio.con.commit();
			}
			if ((puntosActu > 25) && (puntosActu < 200)) {
				sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET titulo='GCH'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				stmt = Inicio.con.createStatement();
				stmt.executeUpdate(sql);
				Inicio.con.commit();
			}
			if ((puntosActu > 200) && (puntosActu < 400)) {
				sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET titulo='GCHS'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				stmt = Inicio.con.createStatement();
				stmt.executeUpdate(sql);
				Inicio.con.commit();
			}
			if ((puntosActu > 400) && (puntosActu < 800)) {
				sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET titulo='GCHG'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				stmt = Inicio.con.createStatement();
				stmt.executeUpdate(sql);
				Inicio.con.commit();
			}
			if( puntosActu > 800){
				sql = "UPDATE public.perro_campeon\r\n"
						+ "	SET titulo='GCHP'\r\n"
						+ "	WHERE akc='" + textFieldAKC.getText() + "' ;";
				stmt = Inicio.con.createStatement();
				stmt.executeUpdate(sql);
				Inicio.con.commit();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
