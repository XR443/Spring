package com.github.hardlolmaster.module2.homework3.shell;

import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerGenre;
import com.github.hardlolmaster.module2.homework3.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.transaction.Transactional;

@ShellComponent
@Transactional
public class GenreCommands {
    private final IPersistenceManagerGenre managerGenre;

    @Autowired
    public GenreCommands(IPersistenceManagerGenre managerGenre) {
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
        System.out.println(managerGenre.findById(id));
    }
}
