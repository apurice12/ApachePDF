package com.ApachePDFBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReadFormFields {
    public static void main(String[] args) {
        String filePath = "FormFields.pdf";
        File file = new File(filePath);

        String outputPath = "editableFormFieldsOutput.txt"; // Output file for editable fields

        try (PDDocument document = PDDocument.load(file);
             FileWriter writer = new FileWriter(outputPath)) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                List<PDField> fields = acroForm.getFields();

                for (PDField field : fields) {
                    // Check if the field is not read-only (i.e., is editable)
                    if (!field.isReadOnly()) {
                        printField(field, "", writer); // Write only editable fields
                    }
                }
            } else {
                writer.write("This document does not contain any form fields.\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printField(PDField field, String indent, FileWriter writer) throws IOException {
        writer.write(indent + "Field name: " + field.getFullyQualifiedName() + "\n");
        writer.write(indent + "Field value: " + field.getValueAsString() + "\n\n");
    }
}
