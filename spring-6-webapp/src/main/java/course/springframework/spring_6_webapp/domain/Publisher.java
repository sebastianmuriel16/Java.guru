    package course.springframework.spring_6_webapp.domain;

    import jakarta.persistence.*;


    import java.util.HashSet;
    import java.util.Objects;
    import java.util.Set;

    @Entity
    public class Publisher {

        @Id
        @GeneratedValue(strategy =  GenerationType.AUTO )
        private Long id;
        private String publisherName;
        private String address;
        private String city;
        private String state;
        private String zip;

        @OneToMany(mappedBy = "publisher")
        private Set<Book> books = new HashSet<>();

        public Set<Book> getBooks() {
            return books;
        }

        public void setBooks(Set<Book> books) {
            this.books = books;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPublisherName() {
            return publisherName;
        }

        public void setPublisherName(String publisherName) {
            this.publisherName = publisherName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Publisher publisher)) return false;
            return Objects.equals(id, publisher.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
