package fmc.entropy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class App 
{
    public static void main( String[] args )
    {
        String file = "CV AAL_en.pdf";
        String result = "result1.txt";
        getBytes(file, result);
    }

    private static void getBytes(String file, String result){
        try {
            byte[] bFile = Files.readAllBytes(Paths.get(file));

            double[] probability = new double[255];
            for (int i=0; i<probability.length; i++){
                byte aux = (byte) i;
                int cont = 0;
                for (int j=0; j<bFile.length; j++){
                    if(Byte.compare(aux, bFile[j]) == 0){
                        cont++;
                    }
                }
                probability[i] = cont / bFile.length;
            }

            double[] information = new double[255];
            double entropy = 0;
            for (int i=0; i<information.length; i++){
                information[i] = (log2((double) probability[i]) * (-1));
                entropy += information[i];
            }

            Path path = Paths.get(result);
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
            for (int i = 0; i<information.length; i++){
                writer.write(String.format("byte[" + i + "]: %f%n", information[i]));
            }
            writer.write(String.format("Entropy: %f%n", entropy));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static double log2(double num){
        return (Math.log(num) / Math.log(2));
    }
}

