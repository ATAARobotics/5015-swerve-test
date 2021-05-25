package frc.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DataLogger {

    private FileOutputStream file;
    private BufferedWriter fileWriter;

    private String fileName;

    public DataLogger(String fileName) {
        this.fileName = fileName;

        setupFile();
    }

    public void writeLine(String data) {
        try {
            fileWriter.write(data + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupFile() {
        try {
            file = new FileOutputStream(new File("/home/lvuser/" + fileName + ".log"), false);
            fileWriter = new BufferedWriter(new OutputStreamWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
