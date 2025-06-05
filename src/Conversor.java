import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Conversor {

    static final String API_KEY = "3af4e0417407a186e6e2a70f";
    static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    static final String HISTORIAL_FILE = "historial.txt";

    static final String[] FROM_MONEDAS = {"USD", "ARS", "USD", "BRL", "USD", "COP"};
    static final String[] TO_MONEDAS   = {"ARS", "USD", "BRL", "USD", "COP", "USD"};

    static final Map<String, String> NOMBRES_MONEDAS = Map.of(
        "USD", "dólares estadounidenses",
        "ARS", "pesos argentinos",
        "BRL", "reales brasileños",
        "COP", "pesos colombianos"
    );

    static List<String> historial = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();

            int opcion = -1;
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida. Por favor, ingrese un número válido.");
                scanner.nextLine();
                continue;
            }

            if (opcion == 8) {
                System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                break;
            }

            if (opcion == 7) {
                mostrarHistorial();
                continue;
            }

            if (opcion < 1 || opcion > 6) {
                System.out.println("⚠️ Opción inválida. Intente de nuevo con un número válido.");
                continue;
            }

            String from = FROM_MONEDAS[opcion - 1];
            String to = TO_MONEDAS[opcion - 1];

            System.out.printf("Ingrese el monto en %s: ", from);
            double monto = 0;
            try {
                monto = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida. Debe ingresar un número decimal.");
                scanner.nextLine();
                continue;
            }

            double tasa = obtenerTasaDesdeAPI(from, to);
            if (tasa == -1) continue;

            double resultado = convertirMoneda(monto, tasa);
            registrarConversion(from, to, monto, resultado);

            while (true) {
                System.out.println("\n¿Desea realizar otra conversión?");
                System.out.println("1) Volver al menú principal");
                System.out.println("2) Salir");

                int subopcion = -1;
                try {
                    subopcion = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("⚠️ Entrada inválida. Por favor, ingrese un número válido.");
                    scanner.nextLine();
                    continue;
                }

                if (subopcion == 1) {
                    break;
                } else if (subopcion == 2) {
                    System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                    System.exit(0);
                } else {
                    System.out.println("Opción inválida. Intente de nuevo con un número válido.");
                }
            }
        }

        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("*********************************************");
        System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
        System.out.println();
        System.out.println("1) Dólar => Peso argentino");
        System.out.println("2) Peso argentino => Dólar");
        System.out.println("3) Dólar => Real brasileño");
        System.out.println("4) Real brasileño => Dólar");
        System.out.println("5) Dólar => Peso colombiano");
        System.out.println("6) Peso colombiano => Dólar");
        System.out.println("7) Ver historial");
        System.out.println("8) Salir");
        System.out.print("Elija una opción válida: ");
    }

    static double obtenerTasaDesdeAPI(String from, String to) {
        try {
            String url = API_URL + from;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            if (!rates.has(to)) {
                System.out.println("Error: tasa no disponible.");
                return -1;
            }

            return rates.get(to).getAsDouble();
        } catch (Exception e) {
            System.out.println("Error al obtener tasa: " + e.getMessage());
            return -1;
        }
    }

    static double convertirMoneda(double monto, double tasa) {
        return monto * tasa;
    }

    static void registrarConversion(String from, String to, double monto, double resultado) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy - HH:mm 'hs'")
                .withLocale(new Locale("es", "ES"));
        String tiempo = LocalDateTime.now().format(formatter);

        String nombreFrom = NOMBRES_MONEDAS.getOrDefault(from, from);
        String nombreTo = NOMBRES_MONEDAS.getOrDefault(to, to);

        String registro = String.format("[%s] %.2f %s => %.2f %s", tiempo, monto, nombreFrom, resultado, nombreTo);
        historial.add(registro);

        try {
            Files.write(Paths.get(HISTORIAL_FILE), (registro + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }

        System.out.println("✔ Conversión realizada:");
        System.out.println(registro);
    }

    static void mostrarHistorial() {
        System.out.println("\n=== Historial de conversiones ===");
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            for (String r : historial) {
                System.out.println(r);
            }
        }
    }
}
