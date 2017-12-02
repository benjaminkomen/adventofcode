package com.benjamin.repositories;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class InputRepository {

    @NotNull
    public String getInput(int day) {
        try {
            return getFileContents(day + ".txt");
        } catch (NoSuchFileException e) {
            throw new RuntimeException("We could not locate the file.");
        }
    }

    @NotNull
    private String getFileContents(@NotNull String fileName) throws NoSuchFileException {
        final StringBuilder result = new StringBuilder("");
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        final File inputFile = resource != null ? new File(resource.getFile()) : null;

        if (inputFile == null || !inputFile.exists()) {
            throw new NoSuchFileException("There is no file with the provided name.");
        }

        try (Scanner scanner = new Scanner(inputFile)) {

            while (scanner.hasNextLine()) {

                if (!"".equals(result.toString())) {
                    result.append("\n");
                }

                String line = scanner.nextLine();
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
