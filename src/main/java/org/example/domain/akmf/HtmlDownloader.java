package org.example.domain.akmf;


import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Service
public class HtmlDownloader {

    public String downloadHtml() {
        try {

            trustAllCertificates();

            // URL strony do pobrania
            String websiteURL = "https://www.akmf.pl/oferty-pracy";

            // Nawiązanie połączenia
            URL url = new URL(websiteURL);
            URLConnection connection = url.openConnection();

            // Dodanie nagłówka user-agent, aby uniknąć potencjalnego blokowania
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

            // Odczytanie odpowiedzi
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Budowanie zawartości HTML
            StringBuilder htmlContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
            reader.close();

            return htmlContent.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Błąd podczas pobierania HTML: " + e.getMessage();
        }
    }

    // Metoda do ignorowania problemów z certyfikatem SSL
    private void trustAllCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Nie rób nic - akceptuj wszystkie certyfikaty klienta
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Nie rób nic - akceptuj wszystkie certyfikaty serwera
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Ignorowanie sprawdzania nazwy hosta
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}