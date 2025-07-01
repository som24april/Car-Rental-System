import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
public class BikeGUI {
    Main ob=new Main();
    BikeRentalSystem bk;
    DefaultTableModel returnModel = new DefaultTableModel(new String[]{"Bike Id", "Brand", "Model", "Price/Day (₹)"}, 0);

    public BikeGUI(BikeRentalSystem bk){
        this.bk=bk;
        mainwindow();
    }

      public void mainwindow(){
          JFrame mainFrame = new JFrame("Bike Rental System");
          mainFrame.setSize(300, 200);
          mainFrame.setLayout(new FlowLayout(1,40,30));
          mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          JButton rentBtn = new JButton("Rent a Bike");
          JButton returnBtn = new JButton("Return a Bike");

          rentBtn.setFont(new Font("Arial", Font.BOLD, 16));
          returnBtn.setFont(new Font("Arial", Font.BOLD, 16));
          rentBtn.setFocusable(false);
          returnBtn.setFocusable(false);

          rentBtn.addActionListener(e -> RentWindow());
          returnBtn.addActionListener(e -> ReturnWindow());

          mainFrame.add(rentBtn);
          mainFrame.add(returnBtn);
          mainFrame.setLocationRelativeTo(null);
          mainFrame.setVisible(true);
      }
      public void RentWindow(){
          JFrame frame = new JFrame("Rent a Bike");
          frame.setSize(500,500);
          frame.getContentPane().setBackground(new Color(255,255,255));
          JLabel label = new JLabel("Available Bikes");
          label.setBounds(0,0,500,50);
          label.setFont(new Font("Arial", Font.BOLD, 16));
          label.setHorizontalAlignment(SwingConstants.CENTER);
          frame.add(label);

          //Table............................
          DefaultTableModel model = new DefaultTableModel(new String[]{"Bike Id","Brand","Model", "Price/Day (₹)"}, 0);
          JTable table = new JTable(model);
          table.setFillsViewportHeight(true);
          table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          table.getColumnModel().getColumn(0).setPreferredWidth(60);  // Bike ID
          table.getColumnModel().getColumn(1).setPreferredWidth(120); // Brand
          table.getColumnModel().getColumn(2).setPreferredWidth(120); // Model
          table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Price
          for (Bike bike : bk.bikes) {
              if (bike.isAvailable) {
                  model.addRow(new Object[]{bike.bikeId, bike.brand,bike.model,bike.basePricePerDay});
              }
          }
          JScrollPane scrollPane = new JScrollPane(table);
          scrollPane.setBounds(15,50,450,280);
          frame.add(scrollPane);


          JLabel selectedbike = new JLabel("Selected Bike");
          selectedbike.setBounds(15,330,450,40);
          selectedbike.setFont(new Font("Arial",Font.BOLD,16));
          selectedbike.setHorizontalAlignment(SwingConstants.CENTER);
          frame.add(selectedbike);

          JTextArea selectedInfo = new JTextArea();
          selectedInfo.setBounds(15,370,450,20);
          frame.add(selectedInfo);
          // Listen to table selection
          final Bike[] selectedBike={null};
          table.getSelectionModel().addListSelectionListener(e -> {
              int row = table.getSelectedRow();
              if (row != -1) {
                  String id = model.getValueAt(row, 0).toString();
                  for (Bike bike : bk.bikes) {
                      if (bike.bikeId.equals(id)) {
                          selectedBike[0] = bike;
                          selectedInfo.setText("ID: " + bike.bikeId + ", Brand: " + bike.brand +
                                  ", Model: " + bike.model + ", Price/Day: ₹" + bike.basePricePerDay);
                          break;
                      }
                  }
              }
          });


          JLabel dayslabel = new JLabel("Enter Number of Days:");
          dayslabel.setBounds(15,0,150,20);
          dayslabel.setHorizontalAlignment(SwingConstants.LEFT);

          JTextField days = new JTextField();
          days.setBounds(180,0,50,20);

          JButton showtotal = new JButton("Show Total Price");
          showtotal.setBounds(300,0,150,20);
          showtotal.setFocusable(false);

          //Binding all three in the JPanel bottom
          JPanel bottom = new JPanel();
          bottom.setBackground(Color.WHITE);
          bottom.setLayout(null);
          bottom.setBounds(0,400,500,100);
          frame.add(bottom);
          bottom.add(dayslabel);
          bottom.add(days);
          bottom.add(showtotal);
          frame.add(bottom);
          bottom.setVisible(false);
          table.getSelectionModel().addListSelectionListener(e -> {
              if(table.getSelectedRow()!=-1)bottom.setVisible(true);
          });

          //Action Listener for Show Total Price
          JTextArea totalprice = new JTextArea();
          totalprice.setBounds(15,25,200,20);
          bottom.add(totalprice);
          totalprice.setVisible(false);


          JButton rentbutton=new JButton("Rent");
          rentbutton.setBounds(250,25,100,20);
          bottom.add(rentbutton);
          rentbutton.setVisible(false);


          showtotal.addActionListener(e->{
             try{
                 int d= Integer.parseInt(days.getText());
              if(d<1||days.getText().isEmpty())throw new NumberFormatException();
              else{
                  rentbutton.setVisible(true);
                  totalprice.setVisible(true);
                  double price = selectedBike[0].basePricePerDay*d;
                  totalprice.setText("Total Price: ₹"+price);
              }} catch (NumberFormatException Ex){
                 JOptionPane.showMessageDialog(frame,"Enter valid number of days");
              }
          });

          //Action Listener for Rent button
          rentbutton.addActionListener(e -> {
              Bike rented = selectedBike[0];
              rented.isAvailable = false;

              // Add to Return Table
               returnModel.addRow(new Object[]{rented.bikeId, rented.brand, rented.model, rented.basePricePerDay});

              // Remove from Rent Table
              model.removeRow(table.getSelectedRow());

              JOptionPane.showMessageDialog(frame, "Bike Rented Successfully");

              selectedInfo.setText("");
              totalprice.setText("");
              bottom.setVisible(false);
              days.setText("");
          });

          frame.setLayout(null);
          frame.setVisible(true);
      }
      public void ReturnWindow(){

              JFrame frame = new JFrame("Return a Bike");
              frame.setSize(500, 500);
              frame.setLayout(null);
              frame.getContentPane().setBackground(Color.WHITE);

              JLabel label = new JLabel("Return Bikes");
              label.setBounds(0, 0, 500, 50);
              label.setFont(new Font("Arial", Font.BOLD, 16));
              label.setHorizontalAlignment(SwingConstants.CENTER);
              frame.add(label);

              JTable returnTable = new JTable(returnModel);
              returnTable.setFillsViewportHeight(true);
              returnTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
              JScrollPane scrollPane = new JScrollPane(returnTable);
              scrollPane.setBounds(15, 50, 450, 280);
              frame.add(scrollPane);

              JLabel selectedbike = new JLabel("Selected Bike to Return");
              selectedbike.setBounds(15, 330, 450, 40);
              selectedbike.setFont(new Font("Arial", Font.BOLD, 16));
              selectedbike.setHorizontalAlignment(SwingConstants.CENTER);
              frame.add(selectedbike);

              JTextArea selectedInfo = new JTextArea();
              selectedInfo.setBounds(15, 370, 450, 20);
              frame.add(selectedInfo);

              JButton returnBtn = new JButton("Return Bike");
              returnBtn.setBounds(175, 400, 150, 25);
              returnBtn.setFocusable(false);
              frame.add(returnBtn);
              returnBtn.setVisible(false);

              final Bike[] selectedBike = {null};

              returnTable.getSelectionModel().addListSelectionListener(e -> {
                  int row = returnTable.getSelectedRow();
                  if (row != -1) {
                      String id = returnModel.getValueAt(row, 0).toString();
                      for (Bike bike : bk.bikes) {
                          if (bike.bikeId.equals(id)) {
                              selectedBike[0] = bike;
                              selectedInfo.setText("ID: " + bike.bikeId + ", Brand: " + bike.brand +
                                      ", Model: " + bike.model + ", Price/Day: ₹" + bike.basePricePerDay);
                              returnBtn.setVisible(true);
                              break;
                          }
                      }
                  }
              });

              returnBtn.addActionListener(e -> {
                  Bike returning = selectedBike[0];
                  returning.isAvailable = true;

                  JOptionPane.showMessageDialog(frame, "Bike Returned Successfully");

                  returnModel.removeRow(returnTable.getSelectedRow());
                  selectedInfo.setText("");
                  returnBtn.setVisible(false);
              });

              frame.setLocationRelativeTo(null);
              frame.setVisible(true);


      }


}
