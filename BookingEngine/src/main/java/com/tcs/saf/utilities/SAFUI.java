package com.tcs.saf.utilities;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.Hashtable;


import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.border.TitledBorder;

import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.FlowLayout;
import javax.swing.JTabbedPane;

import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.jidesoft.swing.CheckBoxTree;


import java.awt.Button;

public class SAFUI extends JFrame {

	private JFrame frmSeleniumAutomationFramework;
	private javax.swing.JScrollPane jScrollPane1;
	public List<String> selectedItems = new ArrayList<String>();
	private JTextField textField;
	static ArrayList<String> packageNames;
	static Hashtable<String, String> fileTable = new Hashtable<String, String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAFUI window = new SAFUI();
					window.frmSeleniumAutomationFramework.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadCurrentTree() {

		File currentDir = new File(System.getProperty("user.dir")
				+ "\\src\\test\\java");
		displayDirectoryContents(currentDir);
	}

	/**
	 * Create the application.
	 */
	public SAFUI() {

		loadCurrentTree();
		initialize();

	}

	/**
	 * This method will generate the tree view of test cases
	 * 
	 * @param dir- src/test/java in the current execution directory in eclipse
	 */
	public static void displayDirectoryContents(File dir) {

		File[] files = dir.listFiles();
		//Arrays.sort(files);
		for (File file : files) 
		{
			
			if (file.isDirectory()) 
			{
				displayDirectoryContents(file);
			}
			else
			{

				if ((file.getName().contains("Test")))
				{
					
					String className = file.getName().replace(".java", "");
					
					String packageName = file
							.getPath()
							.replace(
									System.getProperty("user.dir")
									+ "\\src\\test\\java\\", " ")
									.replace('\\', '.')
									.replace("." + file.getName(), "");
						fileTable.put(className, packageName);

				}

			}
		}
	}

	/**
	 * This mehod will generate TestNG XML in the project folder
	 * 
	 * @return xml creation status
	 */
	private boolean createTestNGXML() {
		if (!textField.getText().trim().endsWith(".xml")) {
			textField.setText(textField.getText().trim() + ".xml");
		}
		try

		{
			String xmlName = textField.getText().trim();
			String current = new java.io.File(".").getCanonicalPath();
			File file = new File(current, xmlName);
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(xmlName));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.newLine();
			out.write("<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">");
			out.newLine();
			out.write("<suite name=\"SampleProject\" parallel=\"false\">"); // 544943

			out.newLine();
			out.write("\t<test verbose=\"2\" name=\"SAMPLE_TEST\" preserve-order=\"true\">"); // 544943

			out.newLine();
			out.write("\t\t<classes>");
			out.newLine();

			for (String selectedClass : selectedItems) {

				if (selectedClass.contains("Select All Tests")) {
					Set set = fileTable.entrySet();

					int i = 1;
					Iterator it = set.iterator();
					while (it.hasNext()) {

						Map.Entry entry = (Map.Entry) it.next();

						out.write("\t\t\t<class name=\""
								+ entry.getValue()
								.toString()
								.concat("." + entry.getKey().toString())
								.trim() + "\">");
						out.write("</class>");
						out.newLine();
					}
					break;
				}

				// when package names are selected
				else if (fileTable.contains(selectedClass)) {
					Set set = fileTable.entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						if (entry.getValue().equals(selectedClass)) {
							out.write("\t\t\t<class name=\""
									+ selectedClass.concat(
											"." + entry.getKey().toString())
											.trim() + "\">");
							out.write("</class>");
							out.newLine();
						}
					}
				}
				// when class names are selected
				else {
					String classFullName;
					String packageName = fileTable.get(selectedClass);

					classFullName = packageName.concat("." + selectedClass);
					out.write("\t\t\t<class name=\"" + classFullName.trim()
							+ "\">");
					out.write("</class>");
					out.newLine();
				}
			}
			out.write("\t\t</classes>");
			out.newLine();
			out.write("\t</test>");
			out.newLine();
			out.write("</suite>");
			out.newLine();
			out.newLine();
			out.close();

		} catch (IOException ex) {
			ex.printStackTrace();
			return (false);
		}
		return (true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSeleniumAutomationFramework = new JFrame();
		frmSeleniumAutomationFramework.getContentPane().setBackground(
				SystemColor.activeCaption);
		frmSeleniumAutomationFramework
		.setTitle("Selenium Automation Framework");
		frmSeleniumAutomationFramework.setFont(new Font("Times New Roman",
				Font.BOLD | Font.ITALIC, 18));
		frmSeleniumAutomationFramework.setBounds(100, 100, 472, 359);
		frmSeleniumAutomationFramework
		.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frmSeleniumAutomationFramework.getContentPane()
		.setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(50, 20, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmSeleniumAutomationFramework.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		Label label = new Label("Selenium Automation Framework");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 16));
		label.setBackground(new Color(51, 153, 204));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new CompoundBorder());
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 6;
		gbc_scrollPane.insets = new Insets(0, 20, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frmSeleniumAutomationFramework.getContentPane().add(scrollPane,
				gbc_scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(1, 0));
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setViewportView(panel_1);
		panel_1.setBackground(Color.GRAY);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE, 0.0 };
		panel_1.setLayout(gbl_panel_1);

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
				"Select All Tests");

		Set<String> keys = fileTable.keySet();
		ArrayList<String> packageList = new ArrayList<String>();
		for (String key : keys) {
			if (!packageList.contains(fileTable.get(key))) {
				packageList.add(fileTable.get(key));
			}

		}

		for (int i = 0; i < packageList.size(); i++) {
			DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode(
					packageList.get(i));
			for (String key : keys) {
				if (fileTable.get(key).equals(packageList.get(i))) {
					DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(
							key);

					packageNode.add(classNode);
				}
			}
			rootNode.add(packageNode);
		}

		final CheckBoxTree checkTree = new CheckBoxTree(rootNode);

		checkTree.setMaximumSize(new Dimension(95, 57));
		checkTree.setAlignmentY(Component.TOP_ALIGNMENT);
		checkTree.setAlignmentX(Component.LEFT_ALIGNMENT);
		checkTree.setVisibleRowCount(30);
		checkTree.setBackground(UIManager.getColor("Tree.background"));

		checkTree.getCheckBoxTreeSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						TreePath[] paths = e.getPaths();
						for (int i = 0; i < paths.length; i++) {
							TreePath path = e.getPath();
							DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
									.getLastPathComponent();
							if (e.isAddedPath(i)) {
								if (!selectedItems.contains(selectedNode
										.toString())) {
									selectedItems.add(selectedNode.toString());
								}
							} else {
								if (selectedNode.toString().equals(
										"Select All Tests")) {
									for (Enumeration en = selectedNode
											.children(); en.hasMoreElements();) {
										selectedItems.remove(en.nextElement()
												.toString());
									}
								}
								selectedItems.remove(selectedNode.toString());
							}
						}
					}
				});
		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.gridwidth = 0;
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.gridheight = 5;
		gbc_tree.gridx = 0;
		gbc_tree.gridy = 0;
		panel_1.add(checkTree, gbc_tree);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(SystemColor.inactiveCaption);
		tabbedPane.setToolTipText("");
		tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 2;
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.insets = new Insets(0, 20, 0, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 7;
		frmSeleniumAutomationFramework.getContentPane().add(tabbedPane,
				gbc_tabbedPane);

		Panel panel_2 = new Panel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("XML Generator", null, panel_2, null);

		JLabel lblNewLabel = new JLabel("XML Name: ");
		lblNewLabel.setVerticalTextPosition(SwingConstants.TOP);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel);

		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);

		JButton btnGenerateXml = new JButton("Generate XML");
		btnGenerateXml.addMouseListener(new MouseAdapter() {
			boolean xmlCreationStatus = false;

			@Override
			public void mouseClicked(MouseEvent e) {

				if (textField.getText().trim() == null
						|| textField.getText().trim().equals("")) { 
					JOptionPane.showMessageDialog(jScrollPane1,
							"Name field should not be empty !!!");

				} else if (new File(System.getProperty("user.dir") + "\\"
						+ textField.getText().trim()).exists()) {
					int ans = JOptionPane
							.showConfirmDialog(
									jScrollPane1,
									"A file named "
											+ textField.getText().trim()
											+ " already exists. \n Do you want to overwrite?");
					if (ans == 0) {
						xmlCreationStatus = createTestNGXML();
					}
				} else {
					xmlCreationStatus = createTestNGXML();
				}
				if (xmlCreationStatus == true) {
					JOptionPane.showMessageDialog(jScrollPane1,
							textField.getText()
							+ " has been created successfully");
				} else {
					JOptionPane.showMessageDialog(jScrollPane1,
							"Error while creating the file");
				}
			}

		});
		panel_2.add(btnGenerateXml);

		Panel panel_3 = new Panel();

		tabbedPane.addTab("Test Suite Execution", null, panel_3, null);

		Button button = new Button("Execute Test");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Boolean status = executeTest(textField.getText());
					if (status)
						JOptionPane.showMessageDialog(jScrollPane1,
								"Successfully executed " + textField.getText());
					else
						JOptionPane.showMessageDialog(jScrollPane1,
								"Error while executing " + textField.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(jScrollPane1,
							"Error while executing " + textField.getText());
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_3.add(button);

	}

	/**
	 * Method to execute test case. It will create a batch file to run mvn command with argument as testng xml name.
	 * Once after execution it will delete the batch file
	 * 
	 * @param xmlName
	 * @return
	 */
	private boolean executeTest(String xmlName) {
		String currentPath;
		File file = null;
		try {
			currentPath = new java.io.File(".").getCanonicalPath();

			file = new File(currentPath, "execution.bat");
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"execution.bat"));
			String newXMLName = xmlName.replace(".xml", "");
			writer.write("mvn test -Dtest.config.file=" + newXMLName);
			writer.close();
			Process p = Runtime.getRuntime().exec("execution.bat");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String answer = sb.toString();
			System.out.println(answer+"::");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			file.delete();
		}
		return true;
	}

}
