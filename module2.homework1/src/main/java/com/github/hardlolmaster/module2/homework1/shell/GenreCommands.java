package com.github.hardlolmaster.module2.homework1.shell;

import com.github.hardlolmaster.module2.homework1.dao.GenreDao;
import com.github.hardlolmaster.module2.homework1.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class GenreCommands {
    private static Genre genre;
    private final GenreDao genreDao;

    @Autowired
    public GenreCommands(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public static Genre getGenre() {
        return genre;
    }

    @ShellMethod(value = "Insert genre", key = "insert-genre")
    public void insertGenre(@ShellOption long id,
                            @ShellOption String name) {
        genreDao.insert(new Genre(id, name));
        storeGenreById(id);
    }

    @ShellMethod(value = "Get all genres", key = "get-all-genre")
    public void getAllGenres() {
        System.out.println(genreDao.findAll());
    }

    @ShellMethod(value = "Get genre", key = "get-genre")
    public void getGenreById(@ShellOption long id) {
        System.out.println(genreDao.findById(id));
    }

    @ShellMethod(value = "Store genre", key = "store-genre")
    public void storeGenreById(@ShellOption long id) {
        genre = genreDao.findById(id);
    }
}
