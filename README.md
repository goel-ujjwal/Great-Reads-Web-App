# Great Reads
## About
- A full stack web development attempt at creating a clone of [Goodreads](https://www.goodreads.com) web app
- Based on a NoSQL distributed database, the web app is built to be highly scalable and highly available
- Focus is more on backend development with following functionality support at current stage:
  - store and handle a catalogue of every book ever published in the world
  - allow users to browse through the catalogue
  - authenticated users can track their reading habits by:
    - marking a certain book with start-completion dates, reading status and book rating
    - managing a dashboard of all the books they are reading and have read
---
## Project Tech Stack
> Application Tier: [Spring Boot](https://spring.io)

> Database: Apache Cassandra ([DataStax Astra DB](https://www.datastax.com/products/datastax-astra) - hosted on Google Cloud)

> Data Layer: [Spring Data Cassandra](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html)

> Security: [Spring Security](https://docs.spring.io/spring-security/reference/index.html) (OAuth - GitHub)

> View Layer: [Thymeleaf](https://www.thymeleaf.org/documentation.html) (HTML-5, CSS-3, [Bootstrap](https://getbootstrap.com/docs/5.3/getting-started/introduction/))

> Data Source: [Open Library](https://openlibrary.org/developers)

> IDE: [Visual Studio Code](https://code.visualstudio.com/docs/java/java-project)
---
## Project Structure & Funtionality
> **Home Page**
  - *Unauthenticated User*: 
    - Login via Github
    - Search any book title
  - *Authenticated User*:
    - My Books Dashboard:
        - list of books associated with the user showing book details, reading status and book rating for each book
        - each book tile can be clicked to reach the Book's Page for tracking that book
    - Search any book title
    
> **Search Page**
- List of book tiles showing book details as per search criteria
  - each book tile can be clicked to reach the Book's Page
- Link to Home Page
- Search any other book title

> **Book's Page**
- Implements the idea of **1 page per book**
- Shows the book's cover image, title, authors, published date
- *Unauthenticated User*: Login option to track their reading habit
- *Authenticated User*: A form to track the book by entering following details (form is pre-populated if book is already associated with the user) for updation:
    - start & completion date
    - reading status (currently reading, finished, did not finish)
    - rating (1 to 5 stars)
  
