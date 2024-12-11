package org.example;

public interface SampleJobOffer {

    default String bodyWith4OffersJson() {
        return """
                [
                    {
                        "title": "Crazy developer",
                        "company": "X",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "offer 1"
                    },
                    {
                        "title": "Java Developer",
                        "company": "Tesla",
                        "salary": "16 000 - 18 000 PLN",
                        "offerUrl": "offer 2"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "Sollers Consulting",
                        "salary": "7 500 - 11 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
                    },
                    {
                        "title": "Junior Full Stack Developer",
                        "company": "Vertabelo S.A.",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-full-stack-developer-vertabelo-remote-k7m9xpnm"
                    }
                ]
                """.trim();
    }
    default String bodyWith2OffersJson() {
        return """
                [
                    {
                        "title": "Crazy developer",
                        "company": "X",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "offer 1"
                    },
                    {
                        "title": "Java Developer",
                        "company": "Tesla",
                        "salary": "16 000 - 18 000 PLN",
                        "offerUrl": "offer 2"
                    }
                ]
                """.trim();
    }



    default String bodyWith0OffersJson() {
        return "";
    }
}
