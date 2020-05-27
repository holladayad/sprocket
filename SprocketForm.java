/*
Author: Jeremy D. Ezell
Date: 5/21/2019
Purpose: JavaFX form to demonstrate advanced FX controls to create
         and manipulate CeramicSprockets
*/

package CIS484.sprocket;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.cell.*;

import java.sql.*;
import oracle.jdbc.pool.*;
import java.util.*;

public class SprocketForm extends Application {
    // Storing data in memory:
    ArrayList<Sprocket> sprocketData = new ArrayList<>();
    
    // TabPane and its Tabs
    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Create:");
    Tab tab2 = new Tab("Review:");
    Tab tab3 = new Tab("Graph:");
    
    // GridPanes for organizing each Tab
    GridPane overallPane = new GridPane();
    GridPane createPane = new GridPane();
    GridPane reviewPane = new GridPane();
    
    // Controls
    Label lblDesc = new Label("Label:");
    Label lblWeight = new Label("Weight:");
    Label lblColor = new Label("Color:");
    TextField txtDesc = new TextField();
    TextField txtWeight = new TextField();
    TextField txtColor = new TextField();
    Button btnCreate = new Button("Create Sprocket ->");
    
    TableView<Sprocket> sprocketTable;
    ObservableList<Sprocket> tableData;
    
    PieChart pieChart;
    ObservableList<PieChart.Data> pieChartData;
    
    MenuBar mnuBar = new MenuBar();
    
        
    // Our Database Connection method needs these 
    // objects. We declare them here and point them
    // to instance objects below.
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    
    @Override
    public void start(Stage primaryStage) {
        
        tab1.setClosable(false);
        tab2.setClosable(false);
        
        createPane.add(lblDesc, 0, 0);
        createPane.add(txtDesc, 1, 0);
        createPane.add(lblWeight, 0, 1);
        createPane.add(txtWeight, 1, 1);
        createPane.add(lblColor, 0, 2);
        createPane.add(txtColor, 1, 2);
        createPane.add(btnCreate, 1, 3);
        
        createPane.setAlignment(Pos.CENTER);
        reviewPane.setAlignment(Pos.CENTER);
        
        tab1.setContent(createPane);
        tab2.setContent(reviewPane);
        
        tbPane.getTabs().add(tab1);
        tbPane.getTabs().add(tab2);
        tbPane.getTabs().add(tab3);
        
        overallPane.setAlignment(Pos.CENTER);
        overallPane.add(mnuBar, 0, 0);
        overallPane.add(tbPane, 0, 1);
        
        Scene primaryScene = new Scene(overallPane,400,450);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Sprocket Factory Form");
        primaryStage.show();
        
        btnCreate.setOnAction(e -> {
            
            sprocketData.add(new CeramicSprocket(
                    txtDesc.getText(),
                    Double.valueOf(txtWeight.getText()),
                    txtColor.getText()));
            
            System.out.println(sprocketData.get(sprocketData.size() - 1).toString());
            
            tableData.clear();
            for (Sprocket s: sprocketData)
            {
                tableData.add(s);
            }
            
            txtDesc.clear();
            txtWeight.clear();
            txtColor.clear();
            
            pieChartData.clear();
            String label;
            for (int i=0;i<sprocketData.size();i++)
            {
                label = sprocketData.get(i).getLabel();
                pieChartData.add(new PieChart.Data(label, sprocketData.get(i).getWeight()));
            }
            
            pieChart.setData(pieChartData);
            tab3.setContent(pieChart);
            
            // Insert into the DB:
            // Commented out
//            Sprocket tempRef = sprocketData.get(sprocketData.size()-1);
//            String sqlQuery = "";
//            sqlQuery += "INSERT INTO JAVAUSER.SPROCKET (ID,LABEL,WEIGHT,SPROCKETCOLOR) VALUES (";
//            sqlQuery += "'" + tempRef.getIDNum() + "',";
//            sqlQuery += "'" + tempRef.getLabel() + "',";
//            sqlQuery += tempRef.getWeight() + ",";
//            sqlQuery += "'" + ((CeramicSprocket)tempRef).getCeramicColor() + "')";
//            
//            sendDBCommand(sqlQuery);
            
        });
        
        tbPane.setMinWidth(primaryScene.getWidth());
        tbPane.setMinHeight(primaryScene.getHeight());
        
        sprocketTable = new TableView<>();
        tableData = FXCollections.observableArrayList(sprocketData);
        sprocketTable.setItems(tableData);
        
        TableColumn tblcIDNum = new TableColumn("ID");
        TableColumn tblcLabel = new TableColumn("Label");
        TableColumn tblcWeight = new TableColumn("Weight");
        TableColumn tblcColor = new TableColumn("Color");
        
        sprocketTable.setMinWidth(primaryScene.getWidth());
        tblcLabel.setMinWidth(150);
        tblcIDNum.setMinWidth(100);
        
        tblcIDNum.setCellValueFactory(new PropertyValueFactory<Sprocket, String>("IDNum"));
        tblcLabel.setCellValueFactory(new PropertyValueFactory<Sprocket, String>("label"));
        tblcWeight.setCellValueFactory(new PropertyValueFactory<Sprocket, Double>("weight"));
        tblcColor.setCellValueFactory(new PropertyValueFactory<Sprocket, String>("ceramicColor"));
        
        sprocketTable.getColumns().addAll(tblcIDNum, tblcLabel, tblcWeight, tblcColor);
        reviewPane.add(sprocketTable, 0, 0);
        
        pieChart = new PieChart();
        pieChartData = FXCollections.observableArrayList();
        tbPane.getTabs().add(tab3);
        
        Menu menuCalcTotal = new Menu("Tools");
        Menu menuDescribe = new Menu("View");
        
        menuCalcTotal.getItems().add(new MenuItem("Total Weight All Sprockets"));
        menuDescribe.getItems().add(new MenuItem("Status - All Sprockets"));
        
        mnuBar.getMenus().addAll(menuCalcTotal, menuDescribe);
        
        menuCalcTotal.getItems().get(0).setOnAction(e -> {
            double theSum = 0;
            for (Sprocket s: sprocketData)
            {
                theSum += s.getWeight();
            }
            
            Alert userPrompt = new Alert(Alert.AlertType.INFORMATION, 
                    "Weight of all Sprockets: " + theSum + "oz", 
                    ButtonType.OK);
            userPrompt.show();
            
        }); // End of Menu Lambda
        
        menuDescribe.getItems().get(0).setOnAction(e -> {
            
            Alert userPrompt = new Alert(Alert.AlertType.INFORMATION,"There are " + sprocketData.size() 
                    + " Sprockets in Inventory!", ButtonType.OK);
            userPrompt.show();
            
        });
        
        sprocketTable.minHeightProperty().bind(primaryScene.widthProperty());
        
       
        
        primaryScene.widthProperty().addListener(ov -> {
            sprocketTable.setMinWidth(primaryScene.getWidth() - 20);
            tbPane.setMinWidth(primaryScene.getWidth()-20);
            mnuBar.setMinWidth(primaryScene.getWidth()-20);
            
        });
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void sendDBCommand(String sqlQuery)
    {
        // Set up your connection strings
        // IF YOU ARE IN CIS330 NOW: use YOUR Oracle Username/Password
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser"; // Change to YOUR Oracle username
        String userPASS = "javapass"; // Change to YOUR Oracle password
        OracleDataSource ds;
        
        // Clear Box Testing - Print each query to check SQL syntax
        //  sent to this method.
        // You can comment this line out when your program is finished
        System.out.println(sqlQuery);
        
        // Lets try to connect
        try
        {
            // instantiate a new data source object
            ds = new OracleDataSource();
            // Where is the database located? Web? Local?
            ds.setURL(URL);
            // Send the user/pass and get an open connection.
            dbConn = ds.getConnection(userID,userPASS);
            // When we get results
            //  -TYPE_SCROLL_SENSITIVE means if the database data changes we
            //   will see our resultset update in real time.
            //  -CONCUR_READ_ONLY means that we cannot accidentally change the
            //   data in our database by using the .update____() methods of
            //   the ResultSet class - TableView controls are impacted by
            //   this setting as well.
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // We send the query to the DB. A ResultSet object is instantiated
            //  and we are returned a reference to it, that we point to from
            // dbResults.
            // Because we declared dbResults at the datafield level
            // we can see the results from anywhere in our Class.
            dbResults = commStmt.executeQuery(sqlQuery); // Sends the Query to the DB
            // The results are stored in a ResultSet structure object
            // pointed to by the reference variable dbResults
            // Because we declared this variable globally above, we can use
            // the results in any method in the class.
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
    }
    
}
