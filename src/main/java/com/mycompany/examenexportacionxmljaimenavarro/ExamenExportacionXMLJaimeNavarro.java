/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.examenexportacionxmljaimenavarro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Jaime Navarro Martínez
 */
public class ExamenExportacionXMLJaimeNavarro {

    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException {

        try {
            //Leemos el fichero
            FileReader lector = new FileReader("estado_sistemas.txt");

            //Le pasamos el buffer para leerlo mejor
            BufferedReader buffer = new BufferedReader(lector);

            //Cargamos la factoría para trabajar con documentos XML
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

            //Creamos el constructor del documento
            DocumentBuilder constructor = factoria.newDocumentBuilder();

            //Creamos el xml
            Document documento = constructor.newDocument();

            //Creamos la etiqueta raiz "objetivos"
            Element objetivos = documento.createElement("objetivos");

            documento.appendChild(objetivos);

            String linea;

            //Mediante un bucle while leemos todas las lineas del fichero
            while ((linea = buffer.readLine()) != null) {
                //Separamos cada linea por comas
                String[] partes = linea.split(",");

                //Guardamos los tres datos
                String instalacion = partes[0];
                String componente = partes[1];
                String estado = partes[2];

                //Como solo necesitamos mostrar los que tienen estado "Critico" usamos un if
                if (estado.equals("Critico")) {
                    //Creamos la etiqueta "sistema"
                    Element sistema = documento.createElement("sistema");

                    //Añadimos el atributo objetivo y le pasamos que coja el texto de instalacion
                    sistema.setAttribute("objetivo", instalacion);

                    //Añadimos al XML la etiqueta "componente" y hacemos que coja el texto de componente
                    Element componenteXML = documento.createElement("componente");
                    componenteXML.setTextContent(componente);

                    // Metemos componente dentro de sistema
                    sistema.appendChild(componenteXML);

                    // Metemos sistema dentro de objetivos
                    objetivos.appendChild(sistema);
                }

            }
            
            buffer.close();
            
            // Guardamos el documento XML en un archivo
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();

            Transformer transformer =
                    transformerFactory.newTransformer();

            DOMSource origen =
                    new DOMSource(documento);

            StreamResult destino =
                    new StreamResult(new File("objetivos_sabotaje.xml"));

            transformer.transform(origen, destino);

            System.out.println("XML generado correctamente.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: Transmision imperial no encontrada.");
        }
    }
}
