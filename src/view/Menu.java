package view;

import java.util.*;

import dao.*;
import domain.*;
import service.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class Menu extends Application {

    private static NotebookService notebookService;
    private static SessionFactory factory;
    private static TableView tableArea;
    private BorderPane root;

    @Override
    public void init() {
        try {
            super.init();
            Locale.setDefault(Locale.ENGLISH);
            Configuration config = new Configuration().configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
            ssrb.applySettings(config.getProperties());
            StandardServiceRegistry standardServiceRegistry = ssrb.build();
            factory = config.buildSessionFactory(standardServiceRegistry);

            CPUDao cpuDao = new CPUDaoImpl(factory);
            MemoryDao memoryDao = new MemoryDaoImpl(factory);
            VendorDao vendorDao = new VendorDaoImpl(factory);
            SalesDao salesDao = new SalesDaoImpl(factory);
            StoreDao storeDao = new StoreDaoImpl(factory);
            NotebookDao notebookDao = new NotebookDaoImpl(factory);

            notebookService = new NotebookServiceImpl(cpuDao, memoryDao, vendorDao,
                    salesDao, storeDao, notebookDao);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();
        Scene scene = new Scene(root, 600, 200);
        primaryStage.setTitle("Notebook Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        HBox buttonRow = new HBox();
        buttonRow.setStyle("-fx-background-color:#add8e6");
        buttonRow.setPadding(new Insets(5,5,5,5));
        buttonRow.setSpacing(5);
        root.setTop(buttonRow);

        Button add = new Button();
        Image addImage = new Image("Actions-list-add-icon.png");
        add.setTooltip(new Tooltip("Create processor, memory, notebook or vendor"));
        add.setGraphic(new ImageView(addImage));
        add.setOnAction(event -> addWindow());

        Button warehouse = new Button();
        Image warehouseImage = new Image("box-1-icon.png");
        warehouse.setTooltip(new Tooltip("Add notebooks to warehouse"));
        warehouse.setGraphic(new ImageView(warehouseImage));
        warehouse.setOnAction(event -> storeWindow());

        Button sale = new Button();
        Image saleImage = new Image("tag-sale-icon.png");
        sale.setTooltip(new Tooltip("Sale notebooks"));
        sale.setGraphic(new ImageView(saleImage));
        sale.setOnAction(event -> saleWindow());

        Button edit = new Button();
        Image editImage = new Image("pencil-icon.png");
        edit.setTooltip(new Tooltip("Edit processor, memory, notebook or vendor"));
        edit.setGraphic(new ImageView(editImage));
        edit.setOnAction(event -> editWindow());

        Button delete = new Button();
        Image deleteImage = new Image("Button-Delete-icon.png");
        delete.setTooltip(new Tooltip("Remove notebooks from warehouse"));
        delete.setGraphic(new ImageView(deleteImage));
        delete.setOnAction(event -> removeWindow());

        Button report = new Button();
        Image reportImage = new Image("document-yellow-icon.png");
        report.setTooltip(new Tooltip("Diferent reports"));
        report.setGraphic(new ImageView(reportImage));
        report.setOnAction(event -> reportWindow());

        buttonRow.getChildren().addAll(add, warehouse, sale, edit, delete, report);
    }

    private void addWindow() {
        Stage addWindowStage = new Stage();
        addWindowStage.setTitle("Add Element");
        addWindowStage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));

        Scene addWindowScene = new Scene(vbox, 200, 330);

        Button processor = new Button("CPU");
        processor.setPrefSize(200, 64);
        processor.setStyle("-fx-font: bold 14pt Georgia");
        processor.setAlignment(Pos.CENTER_LEFT);
        processor.setGraphicTextGap(40);
        processor.setContentDisplay(ContentDisplay.LEFT);
        Image processorImage = new Image("cpuz.png");
        processor.setGraphic(new ImageView(processorImage));
        processor.setOnAction(event -> {
            addProcessorWindow();
            addWindowStage.close();
        });

        Button memory = new Button("Memory");
        memory.setPrefSize(200, 64);
        memory.setStyle("-fx-font: bold 14pt Georgia");
        memory.setAlignment(Pos.CENTER_LEFT);
        memory.setGraphicTextGap(20);
        Image memoryImage = new Image("ddr.png");
        memory.setGraphic(new ImageView(memoryImage));
        memory.setOnAction(event -> {
            addMemoryWindow();
            addWindowStage.close();
        });

        Button vendor = new Button("Vendor");
        vendor.setPrefSize(200, 64);
        vendor.setStyle("-fx-font: bold 14pt Georgia");
        vendor.setAlignment(Pos.CENTER_LEFT);
        vendor.setGraphicTextGap(25);
        Image vendorImage = new Image("Handiman.png");
        vendor.setGraphic(new ImageView(vendorImage));
        vendor.setOnAction(event -> {
            addVendorWindow();
            addWindowStage.close();
        });

        Button notebook = new Button("Notebook");
        notebook.setPrefSize(200, 64);
        notebook.setStyle("-fx-font: bold 14pt Georgia");
        notebook.setAlignment(Pos.CENTER_LEFT);
        notebook.setGraphicTextGap(15);
        Image notebookImage = new Image("laptop.png");
        notebook.setGraphic(new ImageView(notebookImage));
        notebook.setOnAction(event -> {
            addNotebookWindow();
            addWindowStage.close();
        });

        vbox.getChildren().addAll(processor, memory, vendor, notebook);

        addWindowStage.setScene(addWindowScene);
        addWindowStage.setResizable(false);
        addWindowStage.show();
    }

    private void addProcessorWindow() {
        Stage addProcessorWindowStage = new Stage();
        addProcessorWindowStage.setTitle("Add Processor");
        addProcessorWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene addProcessorWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(25);
        HBox row2 = new HBox();
        row2.setSpacing(10);
        HBox row3 = new HBox();
        row3.setSpacing(30);
        root.getChildren().addAll(row1, row2, row3);

        Label ven = new Label("Vendor:");
        ChoiceBox vendor = new ChoiceBox(getAllVendors());
        vendor.setPrefWidth(150);

        Label freq = new Label("Frequency:");
        TextField frequency = new TextField();

        Label mod = new Label("Model:");
        TextField model = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            try {
                String vendorStr = (String)vendor.getValue();
                Vendor vend = notebookService.findVendorByName(vendorStr);
                Double freqD = Double.parseDouble(frequency.getText());
                String modelStr = model.getText();

                if (vendorStr.isEmpty()) {errorWindow(addProcessorWindowStage); return;}
                if (modelStr.isEmpty()) {errorWindow(addProcessorWindowStage); return;}

                notebookService.addCPU(new CPU(vend, freqD, modelStr));
                addProcessorWindowStage.close();
            } catch (NumberFormatException e) {
                errorWindow(addProcessorWindowStage);
            }
        });

        row1.getChildren().addAll(ven, vendor);
        row2.getChildren().addAll(freq, frequency);
        row3.getChildren().addAll(mod, model);
        root.getChildren().add(addButton);

        addProcessorWindowStage.setScene(addProcessorWindowScene);
        addProcessorWindowStage.setResizable(false);
        addProcessorWindowStage.show();
    }

    private ObservableList getAllVendors() {
        List<Vendor> list = notebookService.findAllVendors();
        List<String> names = new LinkedList<>();
        for (Vendor ven : list) {
            names.add(ven.getName());
        }
        ObservableList<String> vendors = FXCollections.observableList(names);
        return vendors;
    }

    private void addMemoryWindow() {
        Stage addMemoryWindowStage = new Stage();
        addMemoryWindowStage.setTitle("Add Memory");
        addMemoryWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene addMemoryWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(15);
        HBox row2 = new HBox();
        row2.setSpacing(10);
        root.getChildren().addAll(row1, row2);

        Label ven = new Label("Vendor:");
        ChoiceBox vendor = new ChoiceBox(getAllVendors());
        vendor.setPrefWidth(150);

        Label cap = new Label("Capacity:");
        TextField capacity = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            try {
                String vendorStr = (String)vendor.getValue();
                Double capD = Double.parseDouble(capacity.getText());
                Vendor vend = notebookService.findVendorByName(vendorStr);

                // if(vendorStr.isEmpty()) {errorWindow(addMemoryWindowStage); return;}
                notebookService.addMemory(new Memory(vend, capD));
                addMemoryWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(addMemoryWindowStage);
            }
        });

        row1.getChildren().addAll(ven, vendor);
        row2.getChildren().addAll(cap, capacity);
        root.getChildren().add(addButton);

        addMemoryWindowStage.setScene(addMemoryWindowScene);
        addMemoryWindowStage.setResizable(false);
        addMemoryWindowStage.show();
    }

    private void addVendorWindow() {
        Stage addVendorWindowStage = new Stage();
        addVendorWindowStage.setTitle("Add Vendor");
        addVendorWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene addVendorWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        root.getChildren().addAll(row1);

        Label nam = new Label("Name:");
        TextField name = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String nameStr = name.getText();

            if (nameStr.isEmpty()) {errorWindow(addVendorWindowStage); return;}

            notebookService.addVendor(new Vendor(nameStr));
            addVendorWindowStage.close();
        });

        row1.getChildren().addAll(nam, name);
        root.getChildren().add(addButton);

        addVendorWindowStage.setScene(addVendorWindowScene);
        addVendorWindowStage.setResizable(false);
        addVendorWindowStage.show();
    }

    private void addNotebookWindow() {
        Stage addNotebookWindowStage = new Stage();
        addNotebookWindowStage.setTitle("Add Notebook");
        addNotebookWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene addProcessorWindowScene = new Scene(root, 260, 200);

        HBox row1 = new HBox();
        row1.setSpacing(35);
        HBox row2 = new HBox();
        row2.setSpacing(40);
        HBox row3 = new HBox();
        row3.setSpacing(7);
        HBox row4 = new HBox();
        row4.setSpacing(52);
        HBox row5 = new HBox();
        row5.setSpacing(30);

        root.getChildren().addAll(row1, row2, row3, row4, row5);

        Label ven = new Label("Vendor:");
        ChoiceBox vendor = new ChoiceBox(getAllVendors());
        vendor.setPrefWidth(180);

        Label mod = new Label("Model:");
        TextField model = new TextField();
        model.setPrefWidth(180);

        Label manDate = new Label("Manufacture \ndate:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(180);

        Label cpu = new Label("CPU:");
        ChoiceBox cpuT = new ChoiceBox(getAllCPUs());
        cpuT.setPrefWidth(180);

        Label mem = new Label("Memory:");
        ChoiceBox memory = new ChoiceBox(getAllMemories());
        memory.setPrefWidth(180);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            try {
                String vendorStr = (String)vendor.getValue();
                Vendor vendor1 = notebookService.findVendorByName(vendorStr);
                String modelStr = model.getText();
                Date date = java.sql.Date.valueOf(datePicker.getValue());
                String cpuText = (String)cpuT.getValue();
                CPU cpu1 = notebookService.findCPUByModel(cpuText);
                Double capacity = (Double)memory.getValue();
                Memory memory1 = notebookService.findMemoryByCapacity(capacity);

               /* if (vendorStr.isEmpty()) {errorWindow(addNotebookWindowStage); return;}
                if (modelStr.isEmpty()) {errorWindow(addNotebookWindowStage); return;}
                if (cpuText.isEmpty()) {errorWindow(addNotebookWindowStage); return;}*/
                notebookService.addNotebook(new Notebook(vendor1, modelStr, date, cpu1, memory1));
                addNotebookWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(addNotebookWindowStage);
            }
        });
        row1.getChildren().addAll(ven, vendor);
        row2.getChildren().addAll(mod, model);
        row3.getChildren().addAll(manDate, datePicker);
        row4.getChildren().addAll(cpu, cpuT);
        row5.getChildren().addAll(mem, memory);
        root.getChildren().add(addButton);

        addNotebookWindowStage.setScene(addProcessorWindowScene);
        addNotebookWindowStage.setResizable(false);
        addNotebookWindowStage.show();
    }

    private void editWindow() {
        Stage editWindowStage = new Stage();
        editWindowStage.setTitle("Edit Element");
        editWindowStage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));

        Scene addWindowScene = new Scene(vbox, 200, 330);

        Button processor = new Button("CPU");
        processor.setPrefSize(200, 64);
        processor.setStyle("-fx-font: bold 14pt Georgia");
        processor.setAlignment(Pos.CENTER_LEFT);
        processor.setGraphicTextGap(40);
        processor.setContentDisplay(ContentDisplay.LEFT);
        Image processorImage = new Image("cpuz.png");
        processor.setGraphic(new ImageView(processorImage));
        processor.setOnAction(event -> {
            getCPUId();
            editWindowStage.close();
        });

        Button memory = new Button("Memory");
        memory.setPrefSize(200, 64);
        memory.setStyle("-fx-font: bold 14pt Georgia");
        memory.setAlignment(Pos.CENTER_LEFT);
        memory.setGraphicTextGap(20);
        Image memoryImage = new Image("ddr.png");
        memory.setGraphic(new ImageView(memoryImage));
        memory.setOnAction(event -> {
            editMemoryId();
            editWindowStage.close();
        });

        Button vendor = new Button("Vendor");
        vendor.setPrefSize(200, 64);
        vendor.setStyle("-fx-font: bold 14pt Georgia");
        vendor.setAlignment(Pos.CENTER_LEFT);
        vendor.setGraphicTextGap(25);
        Image vendorImage = new Image("Handiman.png");
        vendor.setGraphic(new ImageView(vendorImage));
        vendor.setOnAction(event -> {
            editVendorId();
            editWindowStage.close();
        });

        Button notebook = new Button("Notebook");
        notebook.setPrefSize(200, 64);
        notebook.setStyle("-fx-font: bold 14pt Georgia");
        notebook.setAlignment(Pos.CENTER_LEFT);
        notebook.setGraphicTextGap(15);
        Image notebookImage = new Image("laptop.png");
        notebook.setGraphic(new ImageView(notebookImage));
        notebook.setOnAction(event -> {
            editNotebookId();
            editWindowStage.close();
        });

        vbox.getChildren().addAll(processor, memory, vendor, notebook);

        editWindowStage.setScene(addWindowScene);
        editWindowStage.setResizable(false);
        editWindowStage.show();
    }

    private void editNotebookId() {
        Stage portionStage = new Stage();
        portionStage.setTitle("Notebook");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter Notebook ID:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                Long number = Long.parseLong(portionText.getText());

                Notebook notebook = notebookService.findNotebookById(number);

                if (notebook == null) {return;}

                editNotebook(notebook);
                portionStage.close();
            } catch (NumberFormatException e) {
                errorWindow(portionStage);
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void editNotebook(Notebook notebook) {
        Stage editNotebookWindowStage = new Stage();
        editNotebookWindowStage.setTitle("Edit Notebook");
        editNotebookWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene editNotebookWindowScene = new Scene(root, 260, 200);

        HBox row1 = new HBox();
        row1.setSpacing(35);
        HBox row2 = new HBox();
        row2.setSpacing(40);
        HBox row3 = new HBox();
        row3.setSpacing(7);
        HBox row4 = new HBox();
        row4.setSpacing(52);
        HBox row5 = new HBox();
        row5.setSpacing(30);

        root.getChildren().addAll(row1, row2, row3, row4, row5);

        Label ven = new Label("Vendor:");
        ChoiceBox vendor = new ChoiceBox(getAllVendors());
        vendor.setPrefWidth(180);
        vendor.setValue(notebook.getVendor().getName());

        Label mod = new Label("Model:");
        TextField model = new TextField();
        model.setPrefWidth(180);
        model.setText(notebook.getModel());

        Label manDate = new Label("Manufacture \ndate:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(180);
        java.sql.Date dateSQL = new java.sql.Date(notebook.getManufactureDate().getTime());
        datePicker.setValue(dateSQL.toLocalDate());

        Label cpu = new Label("CPU:");
        ChoiceBox cpuT = new ChoiceBox(getAllCPUs());
        cpuT.setPrefWidth(180);
        cpuT.setValue(notebook.getCpu().getModel());

        Label mem = new Label("Memory:");
        ChoiceBox memory = new ChoiceBox(getAllMemories());
        memory.setPrefWidth(180);
        memory.setValue(notebook.getMemory().getCapacity());

        Button addButton = new Button("Edit");
        addButton.setOnAction(event -> {
            try {
                String vendorStr = vendor.getValue().toString();
                Vendor vendor1 = notebookService.findVendorByName(vendorStr);
                notebook.setVendor(vendor1);

                String modelStr = model.getText();
                notebook.setModel(modelStr);

                Date date = java.sql.Date.valueOf(datePicker.getValue());
                notebook.setManufactureDate(date);

                String cpuStr = cpuT.getValue().toString();
                CPU cpu1 = notebookService.findCPUByModel(cpuStr);
                notebook.setCpu(cpu1);

                Double memoryD = (Double)memory.getValue();
                Memory memory1 = notebookService.findMemoryByCapacity(memoryD);
                notebook.setMemory(memory1);

                notebookService.updateNotebook(notebook);

                editNotebookWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(editNotebookWindowStage);
            }
        });

        row1.getChildren().addAll(ven, vendor);
        row2.getChildren().addAll(mod, model);
        row3.getChildren().addAll(manDate, datePicker);
        row4.getChildren().addAll(cpu, cpuT);
        row5.getChildren().addAll(mem, memory);
        root.getChildren().add(addButton);

        editNotebookWindowStage.setScene(editNotebookWindowScene);
        editNotebookWindowStage.setResizable(false);
        editNotebookWindowStage.show();
    }


    private void editVendorId() {
        Stage portionStage = new Stage();
        portionStage.setTitle("Vendor");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter Vendor ID:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                Long number = Long.parseLong(portionText.getText());

                Vendor vendor = notebookService.findStoreByID(number);

                if (vendor == null) {return;}

                editVendor(vendor);
                portionStage.close();
            } catch (NumberFormatException e) {
                errorWindow(portionStage);
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void editVendor(Vendor vendor) {
        Stage editVendorWindowStage = new Stage();
        editVendorWindowStage.setTitle("Edit Vendor");
        editVendorWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene editVendorWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        root.getChildren().addAll(row1);

        Label nam = new Label("Name:");
        TextField name = new TextField();
        name.setText(vendor.getName());

        Button addButton = new Button("Edit");
        addButton.setOnAction(event -> {
            String nameStr = name.getText();

            if (nameStr.isEmpty()) errorWindow(editVendorWindowStage);

            vendor.setName(nameStr);
            notebookService.updateVendor(vendor);

            editVendorWindowStage.close();
        });

        row1.getChildren().addAll(nam, name);
        root.getChildren().add(addButton);

        editVendorWindowStage.setScene(editVendorWindowScene);
        editVendorWindowStage.setResizable(false);
        editVendorWindowStage.show();
    }

    private void editMemoryId() {
        Stage portionStage = new Stage();
        portionStage.setTitle("Memory");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter Memory ID:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                Long number = Long.parseLong(portionText.getText());
                Memory memory = notebookService.findMemoryById(number);

                if (memory == null) {return;}

                editMemory(memory);
                portionStage.close();
            } catch (NumberFormatException e) {
                errorWindow(portionStage);
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void editMemory(Memory memory) {
        Stage editMemoryWindowStage = new Stage();
        editMemoryWindowStage.setTitle("Edit Memory");
        editMemoryWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene editMemoryWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(15);
        HBox row2 = new HBox();
        row2.setSpacing(10);
        root.getChildren().addAll(row1, row2);

        Label ven = new Label("Vendor:");
        ChoiceBox vendor = new ChoiceBox(getAllVendors());
        vendor.setValue(memory.getVendor().getName());

        Label cap = new Label("Capacity:");
        TextField capacity = new TextField();
        capacity.setText(memory.getCapacity().toString());

        Button addButton = new Button("Edit");
        addButton.setOnAction(event -> {
            try {
                String vendorStr = (String)vendor.getValue();
                Vendor vendor1 = notebookService.findVendorByName(vendorStr);
                Double capD = Double.parseDouble(capacity.getText());
                memory.setCapacity(capD);
                memory.setVendor(vendor1);
                notebookService.updateMemory(memory);
                editMemoryWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(editMemoryWindowStage);
            }
        });

        row1.getChildren().addAll(ven, vendor);
        row2.getChildren().addAll(cap, capacity);
        root.getChildren().add(addButton);

        editMemoryWindowStage.setScene(editMemoryWindowScene);
        editMemoryWindowStage.setResizable(false);
        editMemoryWindowStage.show();
    }

    private void getCPUId() {
        Stage portionStage = new Stage();
        portionStage.setTitle("CPU");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter CPU ID:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                Long number = Long.parseLong(portionText.getText());
                CPU cpu = notebookService.findCPUByID(number);

                if (cpu == null) return;

                editCPU(cpu);
                portionStage.close();
            } catch (NumberFormatException e) {
                errorWindow(portionStage);
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void editCPU(CPU cpu) {
        Stage editProcessorWindowStage = new Stage();
        editProcessorWindowStage.setTitle("Edit Processor");
        editProcessorWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene editProcessorWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(25);
        HBox row2 = new HBox();
        row2.setSpacing(10);
        HBox row3 = new HBox();
        row3.setSpacing(30);
        root.getChildren().addAll(row1, row2, row3);

        Label ven = new Label("Vendor:");
        ChoiceBox vendor = new ChoiceBox(getAllVendors());
        vendor.setValue(cpu.getVendor().getName());
        vendor.setPrefWidth(150);

        Label freq = new Label("Frequency:");
        TextField frequency = new TextField();
        frequency.setText(cpu.getFrequency().toString());

        Label mod = new Label("Model:");
        TextField model = new TextField();
        model.setText(cpu.getModel());

        Button addButton = new Button("Edit");
        addButton.setOnAction(event -> {
            try {
                String vendorStr = (String)vendor.getValue();
                Vendor vendor1 = notebookService.findVendorByName(vendorStr);
                Double freqD = Double.parseDouble(frequency.getText());
                String modelStr = model.getText();
                cpu.setFrequency(freqD);
                cpu.setModel(modelStr);
                cpu.setVendor(vendor1);
                notebookService.updateCPU(cpu);
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(editProcessorWindowStage);
            }
            editProcessorWindowStage.close();
        });

        row1.getChildren().addAll(ven, vendor);
        row2.getChildren().addAll(freq, frequency);
        row3.getChildren().addAll(mod, model);
        root.getChildren().add(addButton);

        editProcessorWindowStage.setScene(editProcessorWindowScene);
        editProcessorWindowStage.setResizable(false);
        editProcessorWindowStage.show();
    }

    private void reportWindow() {
        Stage reportWindowStage = new Stage();
        reportWindowStage.setTitle("Reports");
        reportWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));

        Scene reportWindowScene = new Scene(vbox);

        ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("�������� ��� �������� �� ������ (���������)");
        button1.setToggleGroup(group);
        RadioButton button2 = new RadioButton("�������� ��� �������� ������� ������ \n���������� ����������");
        button2.setToggleGroup(group);
        RadioButton button3 = new RadioButton("�������� ��� �������� �� ���������� \n����� ������������� ����������");
        button3.setToggleGroup(group);
        RadioButton button4 = new RadioButton("�������� ��� �������� �� ������");
        button4.setToggleGroup(group);
        RadioButton button5 = new RadioButton("�������� ���� ���������, ���������� �� \n������ �� ������� �������������");
        button5.setToggleGroup(group);
        RadioButton button6 = new RadioButton("�������� ����� ������ ��������� �� ������� ���");
        button6.setToggleGroup(group);

        HBox add = new HBox();
        add.setAlignment(Pos.CENTER);
        Button reportButton = new Button("Report");
        add.getChildren().add(reportButton);
        reportButton.setOnAction(event -> {
            if (button1.isSelected()) {
                getPortion();
            } else if (button2.isSelected()) {
                getAmount();
            } else if (button3.isSelected()) {
                getVendorsName();
            } else if (button4.isSelected()) {
                showAll();
            } else if (button5.isSelected()) {
                showRemainingUsingVendors();
            } else {
                showSalesUsingDate();
            }
            reportWindowStage.close();
        });

        vbox.getChildren().addAll(button1, button2, button3, button4, button5, button6, add);

        reportWindowStage.setScene(reportWindowScene);
        reportWindowStage.setResizable(false);
        reportWindowStage.show();
    }

    private void getVendorsName() {
        Stage portionStage = new Stage();
        portionStage.setTitle("Vendor");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter vendor's name:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String vendor = portionText.getText();
                    showAllUsingVendor(vendor);
                } catch (NumberFormatException e) {
                    // TODO Auto-generated method stub
                }
                portionStage.close();
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void getAmount() {
        Stage portionStage = new Stage();
        portionStage.setTitle("Amount");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter amount:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                int number = Integer.parseInt(portionText.getText());
                showAllMoreThanNumber(number);
                portionStage.close();
            } catch (NumberFormatException e) {
                errorWindow(portionStage);
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void getPortion() {
        Stage portionStage = new Stage();
        portionStage.setTitle("Portion");
        portionStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.CENTER);

        Scene portionScene = new Scene(vbox);

        Label portionLabel = new Label("Enter portion:");
        TextField portionText = new TextField();

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                int portion = Integer.parseInt(portionText.getText());
                showAllPortion(portion);
                portionStage.close();
            } catch (NumberFormatException e) {
                errorWindow(portionStage);
            }
        });

        vbox.getChildren().addAll(portionLabel, portionText, okButton);

        portionStage.setScene(portionScene);
        portionStage.setResizable(false);
        portionStage.show();
    }

    private void showAllMoreThanNumber(int number) {
        tableArea = new TableView();
        TableColumn idCol = new TableColumn("id");
        TableColumn vendorCol = new TableColumn("vendor");
        TableColumn modelCol = new TableColumn("model");
        TableColumn dateCol = new TableColumn("date");
        TableColumn cpuCol = new TableColumn("cpu");
        TableColumn memoryCol = new TableColumn("memory");

        tableArea.getColumns().addAll(idCol, vendorCol, modelCol, dateCol, cpuCol, memoryCol);

        ObservableList<Notebook> data = FXCollections.observableArrayList(notebookService.getNotebooksGtAmount(number));

        idCol.setCellValueFactory(new PropertyValueFactory<Notebook,Long>("id"));

        vendorCol.setCellValueFactory(new PropertyValueFactory<Notebook,Vendor>("vendor"));

        modelCol.setCellValueFactory(new PropertyValueFactory<Notebook,String>("model"));

        dateCol.setCellValueFactory(new PropertyValueFactory<Notebook,Date>("date"));
        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Notebook,Date>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Notebook, Date> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getManufactureDate());
                    }
                }
        );

        cpuCol.setCellValueFactory(new PropertyValueFactory<Notebook,CPU>("cpu"));

        memoryCol.setCellValueFactory(new PropertyValueFactory<Notebook,Memory>("memory"));

        tableArea.setItems(data);

        root.setCenter(tableArea);
    }

    private void showAll() {
        tableArea = new TableView();
        TableColumn idCol = new TableColumn("id");
        TableColumn vendorCol = new TableColumn("vendor");
        TableColumn modelCol = new TableColumn("model");
        TableColumn dateCol = new TableColumn("date");
        TableColumn cpuCol = new TableColumn("cpu");
        TableColumn memoryCol = new TableColumn("memory");

        tableArea.getColumns().addAll(idCol, vendorCol, modelCol, dateCol, cpuCol, memoryCol);

        ObservableList<Notebook> data = FXCollections.observableArrayList(notebookService.findAllNotebooks());

        idCol.setCellValueFactory(new PropertyValueFactory<Notebook,Long>("id"));

        vendorCol.setCellValueFactory(new PropertyValueFactory<Notebook,Vendor>("vendor"));

        modelCol.setCellValueFactory(new PropertyValueFactory<Notebook,String>("model"));

        dateCol.setCellValueFactory(new PropertyValueFactory<Notebook,Date>("date"));
        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Notebook,Date>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Notebook, Date> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getManufactureDate());
                    }
                }
        );

        cpuCol.setCellValueFactory(new PropertyValueFactory<Notebook,CPU>("cpu"));

        memoryCol.setCellValueFactory(new PropertyValueFactory<Notebook,Memory>("memory"));

        tableArea.setItems(data);

        root.setCenter(tableArea);
    }

    private void showAllUsingVendor(String vendor) {

    }

    private void showAllPortion(int portion) {
        // TODO Auto-generated method stub
    }

    private void showSalesUsingDate() {
        // TODO Auto-generated method stub

    }

    private void showRemainingUsingVendors() {
        // TODO Auto-generated method stub

    }

    private void storeWindow() {
        Stage storeWindowStage = new Stage();
        storeWindowStage.setTitle("Store");
        storeWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene storeWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(5);
        HBox row2 = new HBox();
        row2.setSpacing(16);
        HBox row3 = new HBox();
        row3.setSpacing(32);
        root.getChildren().addAll(row1, row2, row3);

        Label notebook = new Label("Notebook:");
        ChoiceBox notebookText = new ChoiceBox(getAllNotebooks());
        notebookText.setPrefWidth(150);

        Label amount = new Label("Amount:");
        TextField amountText = new TextField();

        Label price = new Label("Price:");
        TextField priceText = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            try {
                String model = (String)notebookText.getValue();
                Notebook notebook1 = notebookService.findNotebookByModel(model);
                Integer amountInt = Integer.parseInt(amountText.getText());
                Double priceInt = Double.parseDouble(priceText.getText());
                notebookService.receive(notebook1.getId(), amountInt, priceInt);

                storeWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(storeWindowStage);
            }
        });
        row1.getChildren().addAll(notebook, notebookText);
        row2.getChildren().addAll(amount, amountText);
        row3.getChildren().addAll(price, priceText);
        root.getChildren().add(addButton);

        storeWindowStage.setScene(storeWindowScene);
        storeWindowStage.setResizable(false);
        storeWindowStage.show();
    }

    private void saleWindow() {
        Stage saleWindowStage = new Stage();
        saleWindowStage.setTitle("Sales");
        saleWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene saleWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(5);
        HBox row2 = new HBox();
        row2.setSpacing(5);

        root.getChildren().addAll(row1, row2);

        Label storeId = new Label("Store ID:");
        ChoiceBox storeText = new ChoiceBox(getAllStores());
        storeText.setPrefWidth(170);

        Label amount = new Label("Amount:");
        TextField amountText = new TextField();
        amountText.setPrefWidth(170);

        Button addButton = new Button("Sale");
        addButton.setOnAction(event -> {
            try {
                Integer amountInt = Integer.parseInt(amountText.getText());
                Long store = (Long) storeText.getValue();
                Long operation = notebookService.sale(store, amountInt);

                if (operation == -1L) {errorWindow(saleWindowStage); return;}

                saleWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(saleWindowStage);
            }
        });
        row1.getChildren().addAll(storeId, storeText);
        row2.getChildren().addAll(amount, amountText);

        root.getChildren().add(addButton);

        saleWindowStage.setScene(saleWindowScene);
        saleWindowStage.setResizable(false);
        saleWindowStage.show();
    }

    private void removeWindow() {
        Stage removeWindowStage = new Stage();
        removeWindowStage.setTitle("Remove");
        removeWindowStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));

        Scene saleWindowScene = new Scene(root);

        HBox row1 = new HBox();
        row1.setSpacing(5);
        HBox row2 = new HBox();
        row2.setSpacing(5);

        root.getChildren().addAll(row1, row2);

        Label storeId = new Label("Store ID:");
        ChoiceBox storeText = new ChoiceBox(getAllStores());
        storeText.setPrefWidth(170);


        Label amount = new Label("Amount:");
        TextField amountText = new TextField();
        amountText.setPrefWidth(170);

        Button addButton = new Button("Remove");
        addButton.setOnAction(event -> {
            try {
                Integer amountInt = Integer.parseInt(amountText.getText());
                Long storeID = (Long) storeText.getValue();
                Store store = notebookService.getStoreByID(storeID);
                boolean operation = notebookService.removeFromStore(store, amountInt);
                if (!operation) {errorWindow(removeWindowStage); return;}
                removeWindowStage.close();
            } catch (NumberFormatException | NullPointerException e) {
                errorWindow(removeWindowStage);
            }
        });

        row1.getChildren().addAll(storeId, storeText);
        row2.getChildren().addAll(amount, amountText);

        root.getChildren().add(addButton);

        removeWindowStage.setScene(saleWindowScene);
        removeWindowStage.setResizable(false);
        removeWindowStage.show();
    }

    private ObservableList getAllStores() {
        List<Store> list = notebookService.findAllStores();
        List<Long> ids = new LinkedList<>();
        for (Store store : list) {
            ids.add(store.getId());
        }
        ObservableList<Long> memories = FXCollections.observableList(ids);
        return memories;
    }

    private ObservableList getAllMemories() {
        List<Memory> list = notebookService.findAllMemories();
        List<Double> names = new LinkedList<>();
        for (Memory memory : list) {
            names.add(memory.getCapacity());
        }
        ObservableList<Double> memories = FXCollections.observableList(names);
        return memories;
    }

    private ObservableList<String> getAllNotebooks() {
        List<Notebook> list = notebookService.findAllNotebooks();
        List<String> names = new LinkedList<>();
        for (Notebook notebook : list) {
            names.add(notebook.getModel());
        }
        ObservableList<String> notebooks = FXCollections.observableList(names);
        return notebooks;
    }

    private ObservableList getAllCPUs() {
        List<CPU> list = notebookService.findAllCPUs();
        List<String> names = new LinkedList<>();
        for (CPU cpu : list) {
            names.add(cpu.getModel());
        }
        ObservableList<String> cpus = FXCollections.observableList(names);
        return cpus;
    }

    private static void errorWindow(Stage stage) {
        Stage errorStage = new Stage();
        errorStage.initModality(Modality.APPLICATION_MODAL);

        VBox error = new VBox();
        error.setAlignment(Pos.CENTER);
        error.setPadding(new Insets(5));

        Text errorText = new Text("Error data input!");
        errorText.setStyle("-fx-font:bold 14pt Times;"
                + "-fx-fill: #ff0000");

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(10);

        Button tryAgain = new Button("Try again");
        tryAgain.setOnAction(event -> errorStage.close());

        Button abort = new Button("Abort");
        abort.setOnAction(event -> {
            stage.close();
            errorStage.close();
        });

        buttonRow.getChildren().addAll(tryAgain, abort);

        error.getChildren().addAll(errorText, buttonRow);
        errorStage.setScene(new Scene(error, 150, 100));
        errorStage.setResizable(false);
        errorStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        factory.close();
    }

    public void main() {
        launch(Menu.class);
    }
}
