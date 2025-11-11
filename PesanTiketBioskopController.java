package Controller;

import Model.Mahasiswa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author AERO
 */
public class MahasiswaController implements Initializable {

    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtNim;
    @FXML
    private TableView<Mahasiswa> tvMahasiswa;
    @FXML
    private TableColumn<Mahasiswa, String> colNim;
    @FXML
    private TableColumn<Mahasiswa, String> colNama;

    private ObservableList<Mahasiswa> dataMahasiswa;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataMahasiswa = FXCollections.observableArrayList();
        // Tambahkan data dummy awal
        dataMahasiswa.add(new Mahasiswa("123001", "Liptia Venica"));
        dataMahasiswa.add(new Mahasiswa("123002", "Azila Zahra"));
        
        // Setup TableView Columns (Mencocokkan nama Property di Model)
        // Note: Gunakan "nim" dan "nama" karena kita membuat Getter untuk Property-nya di Mahasiswa.java
        colNim.setCellValueFactory(new PropertyValueFactory<>("nim"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
    
        //Bind data ke TableView
        tvMahasiswa.setItems(dataMahasiswa);
        
        //Tambahkan Listener untuk menampilkan detail saat baris dipilih
        tvMahasiswa.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showMahasiswaDetails(newValue));
    }    

    /**
     * Listener: Menampilkan data Mahasiswa yang dipilih ke kolom input.
     */
    private void showMahasiswaDetails(Mahasiswa mhs) {
        if (mhs != null) {
            txtNim.setText(mhs.getNim());
            txtNama.setText(mhs.getNama());
        } else {
            txtNim.setText("");
            txtNama.setText("");
        }
    }
    
    @FXML
    private void handleAddMahasiswa(ActionEvent event) {
        String nim = txtNim.getText();
        String nama = txtNama.getText();

        if (nim.isEmpty() || nama.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "NIM dan Nama tidak boleh kosong.");
            return;
        }

        Mahasiswa baru = new Mahasiswa(nim, nama);
        dataMahasiswa.add(baru);
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data Mahasiswa berhasil ditambahkan.");
    }

    @FXML
    private void handleEditMahasiswa(ActionEvent event) {
        Mahasiswa selectedMhs = tvMahasiswa.getSelectionModel().getSelectedItem();

        if (selectedMhs != null) {
            // Update object model menggunakan setter biasa
            selectedMhs.setNim(txtNim.getText()); 
            selectedMhs.setNama(txtNama.getText()); 

            tvMahasiswa.refresh();
            
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data Mahasiswa berhasil diubah.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih Mahasiswa yang ingin diubah.");
        }
    }

    @FXML
    private void handleDeleteMahasiswa(ActionEvent event) {
        Mahasiswa selectedMhs = tvMahasiswa.getSelectionModel().getSelectedItem();

        if (selectedMhs != null) {
            dataMahasiswa.remove(selectedMhs);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data Mahasiswa berhasil dihapus.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih Mahasiswa yang ingin dihapus.");
        }
    }
    
    // Utilitas
    private void clearFields() {
        txtNim.clear();
        txtNama.clear();
        tvMahasiswa.getSelectionModel().clearSelection();
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}
