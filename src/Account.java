import java.util.ArrayList;
import java.util.TreeSet;

public class Account {
    public Information info;
    public ArrayList<Character> characters;
    public int number_of_games;

    // clasa interna
    public static class Information {
        private final Credentials credentials;
        private final TreeSet<String> favourite_games;
        private final String name;
        private final String country;

        // folosesc sablonul de proiectare Builder Pattern
        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favourite_games = builder.favourite_games;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public static class InformationBuilder {
            private Credentials credentials;
            private TreeSet<String> favourite_games;
            private String name;
            private String country;

            public InformationBuilder credentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public InformationBuilder favourite_games(TreeSet<String> favourite_games) {
                this.favourite_games = favourite_games;
                return this;
            }

            public InformationBuilder name(String name) {
                this.name = name;
                return this;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                Information info = new Information(this);
                return info;
            }
        }
    }
}
