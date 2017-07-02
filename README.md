# JDBC MySQL Schema and Query builder

[![Build Status](https://travis-ci.org/MontealegreLuis/dbal-jdbc.svg?branch=master)](https://travis-ci.org/MontealegreLuis/dbal-jdbc)
[![codebeat badge](https://codebeat.co/badges/80b448b0-5d55-43b8-9dea-e26fd96d7173)](https://codebeat.co/projects/github-com-montealegreluis-dbal-jdbc-master)

This library uses JDBC to help you create MySQL databases, migrations
and seeders programmatically. 

## Database management

```java
Database database = new Database(connection);
database.drop(name);
database.create(name);
database.use(name);
```

## Migrations

```java
Table movies = schema.table("movies").ifNotExists();
movies.increments("id");
movies.string("title", 300).makeRequired();
movies.integer("rating").defaultTo("0");
movies.string("thumbnail");

Table categories = schema.table("categories").ifNotExists();
categories.increments("id");
categories.string("name").makeRequired();

Table moviesCategories = schema.table("movies_categories").ifNotExists();
IntColumn movieId = (IntColumn) moviesCategories
    .integer("movie_id")
    .unsigned()
    .makeRequired()
;
IntColumn categoryId = (IntColumn) moviesCategories
    .integer("category_id")
    .unsigned()
    .makeRequired()
;
moviesCategories.foreign(movieId).references("id").on("movies");
moviesCategories.foreign(categoryId).references("id").on("categories");
moviesCategories.primary(movieId, categoryId);
```

## Query builder

```java
class MoviesTable extends Table<Movie> {
    class MoviesMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(List<Object> values) {
            return new Movie(
                (long) values.get(0),
                values.get(1).toString(),
                (int) values.get(2),
                values.get(3)
            );
        }
    }
    
    MoviesTable(Connection connection) {
        super(connection);
    }

    Movie findBy(int movieId) {
        return this.select("*").where("id = ?").execute(movieId).fetch();
    }

    void update(String title, int rating, long id) {
        this
            .createUpdate("title", "rating")
            .where("id = ?")
            .execute(title, rating, id)
        ;
    }

    Movie insert(
        String title,
        int rating,
        String thumbnail,
        List<Category> categories
    ) {
        return this
            .createInsert("title", "rating", "thumbnail")
            .execute(title, rating, thumbnail)
            .fetch()
        ;
    }

    @Override
    protected String table() {
        return "movies";
    }

    @Override
    protected RowMapper<Movie> mapper() {
        return new MoviesMapper();
    }
}
```

# Seeders

```java
MoviesTable table = new MoviesTable(connection); 
table.insert(
    movie.title(),
    movie.rating(),
    movie.thumbnail(),
    movie.categories()
);
```
