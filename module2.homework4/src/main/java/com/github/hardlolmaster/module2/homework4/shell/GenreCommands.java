package com.github.hardlolmaster.module2.homework4.shell;

import com.github.hardlolmaster.module2.homework4.dao.GenreRepository;
import com.github.hardlolmaster.module2.homework4.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigInteger;

@ShellComponent
public class GenreCommands {
    private final GenreRepository managerGenre;

    @Autowired
    public GenreCommands(GenreRepository managerGenre) {
        this.managerGenre = managerGenre;
    }

    @ShellMethod(value = "Insert genre", key = "insert-genre")
    public void insertGenre(@ShellOption String name) {
        Genre object = new Genre();
        object.setName(name);
        managerGenre.save(object);
        System.out.println(object);
    }

    @ShellMethod(value = "Get all genres", key = "get-all-genre")
    public void getAllGenres() {
        System.out.println(managerGenre.findAll());
    }

    @ShellMethod(value = "Get genre", key = "get-genre")
    public void getGenreById(@ShellOption long id) {
        System.out.println(managerGenre.findById(BigInteger.valueOf(id)));
    }
}
