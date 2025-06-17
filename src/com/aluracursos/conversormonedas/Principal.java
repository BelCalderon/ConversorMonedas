package com.aluracursos.conversormonedas;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Principal {

    public static void main(String[] args) {
        // Inicializa las dependencias
        ConsumoAPI apiConsumer = new ConsumoAPI();
        Conversor conversor = new Conversor(apiConsumer); // Aquí usamos tu nueva clase Conversor
        Scanner scanner = new Scanner(System.in); // Para leer la entrada del usuario

        System.out.println("--- Aplicación de Conversión de Monedas ---");

        try {
            // 1. Mostrar las monedas disponibles
            System.out.println("\nObteniendo lista de monedas disponibles...");
            // Usamos USD como base temporal para obtener todas las monedas soportadas por la API.
            Monedas initialResponse = conversor.getLatestRates("USD");
            Set<String> availableCurrencies = new TreeSet<>(initialResponse.conversionRates().keySet());

            System.out.println("\nMonedas disponibles:");
            int count = 0;
            for (String currency : availableCurrencies) {
                System.out.printf("%-6s", currency); // Formatea para mostrar 6 caracteres y alinear.
                count++;
                if (count % 10 == 0) { // Muestra 10 monedas por línea.
                    System.out.println();
                }
            }
            System.out.println("\n");

            String sourceCurrencyCode;
            String targetCurrencyCode;
            double amount;

            // 2. Pedir al usuario la moneda de origen (asegurándose de que sea válida).
            while (true) {
                System.out.print("Ingrese el código de la moneda de origen (ej. USD, EUR): ");
                sourceCurrencyCode = scanner.nextLine().toUpperCase();
                if (availableCurrencies.contains(sourceCurrencyCode)) {
                    break;
                } else {
                    System.out.println("Código de moneda no válido. Por favor, ingrese uno de la lista.");
                }
            }

            // 3. Pedir al usuario la moneda de destino (asegurándose de que sea válida).
            while (true) {
                System.out.print("Ingrese el código de la moneda de destino (ej. JPY, GBP): ");
                targetCurrencyCode = scanner.nextLine().toUpperCase();
                if (availableCurrencies.contains(targetCurrencyCode)) {
                    break;
                } else {
                    System.out.println("Código de moneda no válido. Por favor, ingrese uno de la lista.");
                }
            }

            // 4. Pedir el monto a convertir (asegurándose de que sea un número positivo).
            while (true) {
                System.out.print("Ingrese el monto a convertir: ");
                try {
                    amount = scanner.nextDouble();
                    if (amount <= 0) {
                        System.out.println("El monto debe ser un número positivo.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número para el monto.");
                    scanner.next(); // Consume la entrada incorrecta para evitar un bucle infinito.
                }
            }

            // Limpiar el buffer del scanner después de nextDouble() o nextInt().
            scanner.nextLine();

            System.out.printf("\nPreparando para convertir %.2f %s a %s...\n", amount, sourceCurrencyCode, targetCurrencyCode);

            // 5. Obtener las tasas de cambio para la moneda de origen y realizar la conversión.
            Monedas sourceRates = conversor.getLatestRates(sourceCurrencyCode);
            double convertedAmount = conversor.convert(amount, sourceRates, targetCurrencyCode);

            System.out.printf("\n%.2f %s equivale a %.2f %s\n",
                    amount, sourceCurrencyCode, convertedAmount, targetCurrencyCode);
            System.out.println("Tasas actualizadas a: " + sourceRates.timeLastUpdateUtc());


        } catch (ApiCallException e) {
            // Captura errores específicos de la API o el parseo.
            System.err.println("\n¡Error en la aplicación!: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Causa subyacente: " + e.getCause().getMessage());
            }
            System.err.println("Por favor, verifica tu conexión a internet y el código de las monedas.");
        } catch (Exception e) {
            // Captura cualquier otro error inesperado.
            System.err.println("\n¡Ha ocurrido un error inesperado!: " + e.getMessage());
            e.printStackTrace(); // Imprime el rastro completo del error para depuración.
        } finally {
            scanner.close(); // Siempre cierra el scanner para liberar recursos.
            System.out.println("\nGracias por usar la aplicación de conversión de monedas.");
        }
    }
}


