package com.ApachePDFBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ValueToFormField {
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
                    // Exclude checkboxes
                    if (field.getFieldType() != null && !field.getFieldType().equals("Btn")) {
                        // Check if the field is not read-only (i.e., is editable)
                        if (!field.isReadOnly()) {
                            // Set a default value for the field. Adjust "DefaultValue" as needed.
                            field.setValue("DefaultValue");
                            printField(field, "DefaultValue", writer); // Write only editable fields
                        }
                    }
                }
                // Save the document with updated fields under a new file name
                document.save("FormFields_Updated2.pdf");
            } else {
                writer.write("This document does not contain any form fields.\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printField(PDField field, String defaultValue, FileWriter writer) throws IOException {
        writer.write("Field name: " + field.getFullyQualifiedName() + "\n");
        writer.write("New Default Value: " + defaultValue + "\n\n"); // Write the default value set
    }
}
