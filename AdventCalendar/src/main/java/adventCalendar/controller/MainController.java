package adventCalendar.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
public class MainController {

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private Button generateButton;

    @FXML
    private Label labelPath;

    @FXML
    private Hyperlink linkPath;

    @FXML
    private TextArea textArea;

    final String PATH = "C:\\Users\\Janusz\\Desktop\\Adwent\\";

    public void initialize() {
        dateTo.setOnAction(event -> {
                checkDates();
        });

        dateFrom.setOnAction(event -> {
                checkDates();
        });

        generateButton.setOnAction(event -> {
            if (dateTo.getValue() != null && dateFrom.getValue() != null && !Objects.equals(textArea.getText(), "")) {
                new File(PATH).mkdirs();
                List<LocalDate> dates = getDates();
                String[] names = getNames();
                createFiles(names, dates);
                writeFiles(names, dates);
                enablePath();
            } else {
                textArea.setText("Uzupełnij wszystkie pola!");
            }
        });

        linkPath.setOnAction(event -> openFolder());
    }

    private List<LocalDate> getDates() {
        List<LocalDate> dates;
        dates = dateFrom.getValue().datesUntil(dateTo.getValue().plusDays(1)).collect(Collectors.toList());
        Collections.shuffle(dates);
        return dates;
    }

    private String[] getNames() {
        return textArea.getText().split(" ");
    }

    private void createFiles(String[] names, List<LocalDate> dates) {
        for (String name : names) {
            File file = new File(PATH + name + ".txt");
            boolean fileExists = file.exists();
            if (!fileExists) {
                try {
                    fileExists = file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(PATH + name + ".txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void writeFiles(String[] names, List<LocalDate> dates) {
        for (int j = 0; j < dates.size(); j++) {
            try (
                    var fileWriter = new FileWriter(PATH + names[j % names.length] + ".txt", true);
                    var writer = new BufferedWriter(fileWriter);
            ) {
                writer.write(String.valueOf(dates.get(j)));
                writer.newLine();
            } catch (IOException ignored) {
            }
        }

    }

    private void openFolder() {
        try {
            Desktop.getDesktop().open(new File(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDates() {
        if (dateTo.getValue() != null && dateFrom.getValue() != null) {
            if (dateTo.getValue().compareTo(dateFrom.getValue()) < 1) {
                textArea.setText("Data DO nie może być mniejsza niż data OD!");
            } else if (textArea.getText().equals("Data DO nie może być mniejsza niż data OD!")) {
                textArea.clear();
            }
        }
    }

    private void enablePath() {
        labelPath.setVisible(true);
        linkPath.setVisible(true);
        linkPath.setText(PATH);
    }
}
